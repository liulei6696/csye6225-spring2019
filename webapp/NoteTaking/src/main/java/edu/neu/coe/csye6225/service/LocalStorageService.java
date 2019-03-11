package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.Note;
import org.springframework.web.multipart.MultipartFile;

@Deprecated
public interface LocalStorageService extends AttachmentService {
    boolean createAttachmentToLocalDirectory(Note note, String attachmentId, MultipartFile multipartFile);

    boolean deleteAttachmentFromLocalDirectory(Note note, String attachmentId);
}
