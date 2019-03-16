package edu.neu.coe.csye6225.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.service.AttachmentService;
import edu.neu.coe.csye6225.service.FileSaveService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@Component
@Profile("prod")
public class FileSaveServiceS3Impl implements FileSaveService {

    private String awsS3AudioBucket;
    private AmazonS3 amazonS3;
    private final AttachmentService attachmentService;
    private static final Logger logger = LoggerFactory.getLogger(FileSaveServiceS3Impl.class);

    @Autowired
    public FileSaveServiceS3Impl (String awsS3AudioBucket, AttachmentService attachmentService) {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
//                .withCredentials(new InstanceProfileCredentialsProvider(false))
                .build();
        this.awsS3AudioBucket = awsS3AudioBucket;
        this.attachmentService = attachmentService;
    }

    @Override
    public Attachment saveFile(String noteId, MultipartFile multipartFile) {

        String fileName = multipartFile.getOriginalFilename();
        if (fileName == null) {
            logger.info("File with no name is not allowed");
            return null;
        }
        int index = fileName.lastIndexOf(".");
        String fileType = "";
        if(index != -1){ // if the file does not have suffix
            fileType = fileName.substring(index);
        }

        // if file already submitted or the filename is the same
        for (Attachment att : attachmentService.getAllAttachments(noteId)) {
            if (fileName.equals(att.getFileName()))
                return att;
        }

        try {
            // creating the file in the server (temporarily)
            File file = new File(fileName);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

            PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket, fileName, file);

            PutObjectResult putObjectResult = amazonS3.putObject(putObjectRequest);
            URL url = amazonS3.getUrl(awsS3AudioBucket, fileName);
            String etag = putObjectResult.getETag();
            if(StringUtils.isNotBlank(etag))
                logger.info("Send Attachment to Amazon S3 succeeded, Etag is "+etag);

            // removing the file created in the server
            file.delete();

            return new Attachment(noteId, url.toString(), multipartFile.getSize(), fileType, fileName, etag);

        } catch (IOException | AmazonS3Exception e) {
            logger.error("error [" + e.getMessage() + "] occurred while uploading [" + fileName + "] ");
            return null;
        }
    }

    @Override
    public boolean deleteFile(String attachmentId) {
        Attachment att = attachmentService.getAttachmentById(attachmentId);
        try{
            amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, att.getFileName()));
            logger.info("delete file [" + att.getFileName()+"] from S3");
            return true;
        } catch (AmazonServiceException e){
            logger.error("error [" + e.getMessage() + "]  occurred while removing [" + att.getFileName()+ "]");
            return false;
        }
    }
}
