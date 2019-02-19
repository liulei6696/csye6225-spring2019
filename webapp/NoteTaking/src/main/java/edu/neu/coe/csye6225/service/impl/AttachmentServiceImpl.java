package edu.neu.coe.csye6225.service.impl;

import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.mapper.AttachmentMapper;
import edu.neu.coe.csye6225.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Override
    public List<Attachment> getAllAttachments(String noteId) {
        return attachmentMapper.getAllAttachment(noteId);
    }
}
