package edu.neu.coe.csye6225.service.impl;

import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.mapper.AttachmentMapper;
import edu.neu.coe.csye6225.mapper.NoteMapper;
import edu.neu.coe.csye6225.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private NoteMapper noteMapper;

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
     *
     */
    @Override
    public Boolean updateAttachment(String oldId, Attachment newAttachment) {
        try{
            newAttachment.setAttachmentId(oldId);
            deleteAttachment(oldId);
            attachmentMapper.insertAttachment(newAttachment);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean attBelongToUser(String attId, String userId) {
        if(getAttachmentById(attId) == null)
            return false;
        return noteMapper.getNoteById(getAttachmentById(attId).getNoteId()).getUserId().equals(userId);
    }

    public void createNew(){
        attachmentMapper.createNewTable();
    }
}
