package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.entity.Note;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3ClientService extends AttachmentService{
    Attachment createAttachmentToS3Bucket(Note note, String attachmentId, MultipartFile multipartFile, boolean enablePublicReadAccess);

    boolean deleteAttachmentFromS3Bucket(Note note, String attachmentId);
}
