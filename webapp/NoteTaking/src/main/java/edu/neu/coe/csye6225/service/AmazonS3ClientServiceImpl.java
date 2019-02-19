package edu.neu.coe.csye6225.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.model.*;

import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.mapper.AttachmentMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@Component
public class AmazonS3ClientServiceImpl implements AmazonS3ClientService{
    private String awsS3AudioBucket;
    private AmazonS3 amazonS3;
    private static final Logger logger = LoggerFactory.getLogger(AmazonS3ClientServiceImpl.class);
    @Autowired
    private AttachmentMapper attachmentMapper;

//    @Autowired
//    public AmazonS3ClientServiceImpl(Region awsRegion,  AWSCredentialsProvider awsCredentialsProvider, String awsS3AudioBucket)
//    {
//        this.amazonS3 = AmazonS3ClientBuilder.standard()
//                .withCredentials(awsCredentialsProvider)
//                .withRegion(awsRegion.getName()).build();
//        this.awsS3AudioBucket = awsS3AudioBucket;
//    }

    @Autowired
    public AmazonS3ClientServiceImpl(Region awsRegion, String awsS3AudioBucket){
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
        String key = credentials.getAWSSecretKey();
        String aKey = credentials.getAWSAccessKeyId();
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .build();
        this.awsS3AudioBucket = awsS3AudioBucket;
    }

    @Async
    public boolean createAttachmentToS3Bucket(Note note, MultipartFile multipartFile, boolean enablePublicReadAccess)
    {
        long fileSize = multipartFile.getSize();
        String fileName = multipartFile.getOriginalFilename();
        String noteId = note.getNoteId();

        try {
            //creating the file in the server (temporarily)
            File file = new File(fileName);
            String fileType = fileName.substring(fileName.lastIndexOf("."),fileName.length());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

            PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket, fileName, file);

            PutObjectResult result = this.amazonS3.putObject(putObjectRequest);
            URL url = amazonS3.getUrl(awsS3AudioBucket,fileName);
            String eTag = result.getETag();
            if(StringUtils.isNotBlank(eTag))
                logger.info("Send Attachment to Amazon S3 succeeded, Etag is "+eTag);
            Attachment attachment = new Attachment(noteId,url.toString(),fileSize,fileType,fileName,eTag);
            if (attachmentMapper.insertAttachment(attachment)<=0){
                logger.error("meta data failed to insert into database!");
                return false;
            }
            logger.info("Attachment information has been successfully saved to database");
            //removing the file created in the server
            file.delete();
            return true;
        } catch (IOException | AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
            return false;
        }
    }

    @Async
    public boolean deleteAttachmentFromS3Bucket(Note note, String attachmentId)
    {
        Attachment attachment = attachmentMapper.getAttachmentById(attachmentId);
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, attachment.getFileName()));
            logger.info("delete attachment from S3 succeeded");
            if(attachmentMapper.deleteAttachment(attachment)<=0){
                logger.error("delete attachment from database failed");
                return false;
            }
            logger.info("delete attachment from database succeeded");
            return true;
        } catch (AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + attachment.getFileName() + "] ");
            return false;
        }
    }
}
