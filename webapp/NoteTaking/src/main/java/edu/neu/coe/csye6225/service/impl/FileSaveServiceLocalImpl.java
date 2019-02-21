package edu.neu.coe.csye6225.service.impl;

import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.service.AttachmentService;
import edu.neu.coe.csye6225.service.FileSaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
@Profile("dev")
public class FileSaveServiceLocalImpl implements FileSaveService {

    private static final Logger logger = LoggerFactory.getLogger(FileSaveServiceLocalImpl.class);

    private String tempFolder;
    private final AttachmentService attachmentService;

    @Autowired
    public FileSaveServiceLocalImpl (AttachmentService attachmentService) {
        this.tempFolder = createTempFolder();
        this.attachmentService = attachmentService;
    }
    @Override
    public Attachment saveFile(String noteId, MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename();
        if (filename == null) {
            logger.info("File with no name is not allowed");
            return null;
        }
        String url = tempFolder + "/" + filename;
        String etag = "Local storage, no eTag";
        int index = filename.lastIndexOf(".");
        String fileType = "";
        if(index != -1){ // if the file does not have suffix
            fileType = filename.substring(index);
        }

        // if file already submitted or the filename is the same
        for (Attachment att : attachmentService.getAllAttachments(noteId)) {
            if (filename.equals(att.getFileName()))
                return att;
        }

        File dest = new File(url);


        try{
            multipartFile.transferTo(dest);
            logger.info("file [" + filename + "] uploaded under \"" + tempFolder + "\"");

            return new Attachment(noteId, url, multipartFile.getSize(), fileType, filename, etag);
        }catch (IOException e){
            logger.error("store file ["+ filename + "] in local failed");
            return null;
        }
    }

    @Override
    public boolean deleteFile(String attachmentId) {
        Attachment att = attachmentService.getAttachmentById(attachmentId);
        File file = new File(att.getUrl());
        if(!file.exists()) {
            logger.error("file ["+att.getFileName()+"] does not exist!");
            return false;
        } else {
            if(file.delete()) {
                logger.info("file [" + att.getFileName()+"] delete successful");
                return true;
            }
            logger.error("file [" + att.getFileName() + "] delete failed!");
            return false;
        }
    }


    private String createTempFolder () {
        File folder = new File("UploadedFiles");
        folder.mkdir();
        return folder.getAbsolutePath();
    }
}
