package edu.neu.coe.csye6225.service.impl;

import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.mapper.AttachmentMapper;
import edu.neu.coe.csye6225.mapper.NoteMapper;
import edu.neu.coe.csye6225.service.AttachmentService;
import edu.neu.coe.csye6225.service.FileSaveService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private FileSaveService fileSaveService;

    @Override
    public List<Attachment> getAllAttachments(String noteId) {
        return attachmentMapper.getAllAttachment(noteId);
    }

    @Override
    public Attachment getAttachmentById(String attachmentId) {
        return attachmentMapper.getAttachmentById(attachmentId);
    }

    /**
     *
     * @param attachmentId attachment Id
     * @return null if delete failed, attachment if delete success
     */
    @Override
    public Attachment deleteAttachment(String attachmentId) {
        Attachment attachment = attachmentMapper.getAttachmentById(attachmentId);
        if(attachment == null) {
            return null;
        }
        if(attachmentMapper.deleteAttachment(attachment) > 0) {
            return attachment;
        } else {
            return null;
        }
    }

    @Override
    public Boolean addAttachment(String noteId, Attachment attachment) {
        if(getAttachmentById(attachment.getAttachmentId()) != null)
            return true;
        attachment.setNoteId(noteId);
        return (attachmentMapper.insertAttachment(attachment) > 0);
    }

    /**
     * delete old attachment and insert new attachment
     * @param oldAttachment old attachment ID
     * @param newAttachment new attachment ID
     * @return true if success, false if failed
     */
    @Override
    public Boolean updateAttachment(Attachment oldAttachment, Attachment newAttachment) {
        if (attachmentMapper.deleteAttachment(oldAttachment) > 0) {
            return (attachmentMapper.insertAttachment(newAttachment) > 0);

        }
        return false;
    }

    @Override
    public Boolean attBelongToUser(String attId, String userId) {
        return noteMapper.getNoteById(getAttachmentById(attId).getNoteId()).getUserId().equals(userId);
    }
}
