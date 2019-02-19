package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.Note;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3ClientService {
    boolean createAttachmentToS3Bucket(Note note, MultipartFile multipartFile, boolean enablePublicReadAccess);

    boolean deleteAttachmentFromS3Bucket(Note note, String attachmentId);
}
