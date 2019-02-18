package edu.neu.coe.csye6225.mapper;

import edu.neu.coe.csye6225.entity.Attachment;

import java.util.List;

public interface AttachmentMapper {
    int insertAttachment(Attachment attachment);
    int deleteAttachment(Attachment attachment);
    Attachment getAttachmentById(String attachmentId);
    List<Attachment>getAllAttachment();
}
