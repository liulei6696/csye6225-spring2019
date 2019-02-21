package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.entity.Note;
import org.springframework.web.multipart.MultipartFile;

@Deprecated
public interface AmazonS3ClientService {
    Attachment createAttachmentToS3Bucket(Note note, String attachmentId, MultipartFile multipartFile, boolean enablePublicReadAccess);

    boolean deleteAttachmentFromS3Bucket(Note note, String attachmentId);
}
