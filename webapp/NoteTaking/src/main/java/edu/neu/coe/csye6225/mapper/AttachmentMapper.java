package edu.neu.coe.csye6225.mapper;

import edu.neu.coe.csye6225.entity.Attachment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentMapper{
    int insertAttachment(Attachment attachment);
    int deleteAttachment(Attachment attachment);
    Attachment getAttachmentById(String attachmentId);
    List<Attachment>getAllAttachment();
}
