package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.entity.Note;

import java.util.List;

public interface AttachmentService {
    List<Attachment>getAllAttachments(String noteId);
    Attachment getAttachmentById(String attachmentId);

    Attachment deleteAttachment(String attachmentId);

    Boolean addAttachment (String noteId, Attachment attachment);
    Boolean updateAttachment (String oldId, Attachment newAttachment);
    Boolean attBelongToUser(String attId, String userId);
    void createNew();
}
