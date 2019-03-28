package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public Attachment saveFile(String noteId, MultipartFile multipartFile);
    public boolean deleteFile(String attachmentId);
}
