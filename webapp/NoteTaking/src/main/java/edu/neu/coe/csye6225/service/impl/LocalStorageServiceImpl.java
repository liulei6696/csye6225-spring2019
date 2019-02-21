package edu.neu.coe.csye6225.service.impl;

import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.mapper.AttachmentMapper;
import edu.neu.coe.csye6225.service.LocalStorageService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Deprecated
public class LocalStorageServiceImpl extends AttachmentServiceImpl implements LocalStorageService {
    @Autowired
    private AttachmentMapper attachmentMapper;
    private static final Logger logger = LoggerFactory.getLogger(LocalStorageServiceImpl.class);
    @Override
    public boolean createAttachmentToLocalDirectory(Note note, String attachmentId, MultipartFile multipartFile) {
        long fileSize = multipartFile.getSize();
        String fileName = multipartFile.getOriginalFilename();
        String noteId = note.getNoteId();
        String fileType = fileName.substring(fileName.lastIndexOf("."),fileName.length());
        String path = "/Users/ziyanzhu/csye6225/LocalStorage/";
        String url = path+fileName;
        String eTag = "Local storage, no eTag";
        try {
            byte[]sourceBytes = multipartFile.getBytes();
            if(sourceBytes==null)
                return false;
            FileUtils.writeByteArrayToFile(new File(url),sourceBytes,false);

                logger.info("Send Attachment to local storage succeeded");

            if(StringUtils.isNotEmpty(attachmentId)){
                //do update method in mysql
                Attachment attachment = attachmentMapper.getAttachmentById(attachmentId);
                if(attachment==null){
                    logger.error("this attachment does not exists");
                    return false;
                }
                attachment.setUrl(url);
                attachment.setFileType(fileType);
                attachment.setFileSize(fileSize);
                attachment.setFileName(fileName);
                if(attachmentMapper.updateAttachment(attachment)<=0){
                    logger.error("update attachment information failed");
                    return false;
                }
            }
            else {
                //do create method in mysql
                Attachment attachment = new Attachment(noteId,url.toString(),fileSize,fileType,fileName,eTag);
                if (attachmentMapper.insertAttachment(attachment)<=0){
                    logger.error("meta data failed to insert into database!");
                    return false;
                }
            }
            logger.info("Attachment information has been successfully saved to database");
            return true;
        } catch (IOException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
            return false;
        }
    }

    @Override
    public boolean deleteAttachmentFromLocalDirectory(Note note, String attachmentId) {
        Attachment attachment = attachmentMapper.getAttachmentById(attachmentId);
        if(attachment==null){
            logger.error("this attachment does not exists");
            return false;
        }
        try {
            FileUtils.forceDelete(new File(attachment.getUrl()));
            logger.info("delete attachment from S3 succeeded");
            if(attachmentMapper.deleteAttachment(attachment)<=0){
                logger.error("delete attachment from database failed");
                return false;
            }
            logger.info("delete attachment from database succeeded");
            return true;
        } catch (IOException|NullPointerException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while removing [" + attachment.getFileName() + "] ");
            return false;
        }
    }
}
