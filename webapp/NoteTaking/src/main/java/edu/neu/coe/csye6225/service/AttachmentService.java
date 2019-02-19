package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.entity.Note;

import java.util.List;

public interface AttachmentService {
    List<Attachment>getAllAttachments(String noteId);
}
