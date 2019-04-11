package edu.neu.coe.csye6225.service.impl;

import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.mapper.NoteMapper;
import edu.neu.coe.csye6225.service.AccountService;
import edu.neu.coe.csye6225.service.AttachmentService;
import edu.neu.coe.csye6225.service.NoteService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private NoteMapper noteMapper;
    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    @Override
    public Note createNote(String username) {
        Note note = new Note(username, "Default Title of the note", "Default Content of the note");
        if (noteMapper.insertNote(note) > 0) {
            logger.info("Note created, note ID : " + note.getNoteId());
            return note;
        } else {
            logger.error("Note create failed");
            return null;
        }
    }

    @Override
    public boolean deleteNote(String noteId) {
        Note note = noteMapper.getNoteById(noteId);
        if (note == null) {
            logger.warn("Deleting a non-existing note: " + noteId);
            return false;
        }
        if (noteMapper.deleteNote(note)) {
            logger.info("Note [ " + noteId + " ] deleted!");
            return true;
        } else {
            logger.error("Fail to delete note: " + noteId);
            return false;
        }
    }

    @Override
    public Note updateNote(Note note) {
        if (note == null) {
            logger.error("Update with null note");
            return null;
        }
//        Note realNote = noteMapper.getNoteById(note.getNoteId());
        note.setLastModifiedTime();
        if (noteMapper.updateNote(note) > 0) {
            logger.info("Note updated!");
            return noteMapper.getNoteById(note.getNoteId());
        } else {
            logger.error("Fail to update!");
            return null;
        }
    }

    @Override
    public Note getNoteById(String noteId) {
        Note note = noteMapper.getNoteById(noteId);
        if (note == null) {
            logger.warn("Note with ID: " + noteId + " not exist");
            return null;
        }
//        String noteUserId = note.getUserId();
        note = noteMapper.getNoteById(noteId);
        logger.info("Get note successfully!");
        return note;

    }


    @Override
    public List<Note> getAllNotes(String username) {
        return noteMapper.getAllNotes(username);
    }

    @Override
    public Boolean noteBelongToUser(String noteId, String userId) {
//        logger.info("Get note " + noteId + ", and user " + userId);
        if (noteMapper.getNoteById(noteId) == null)
            return false;
//        logger.info("from database " + noteMapper.getNoteById(noteId).getUserId());
        return noteMapper.getNoteById(noteId).getUserId().equals(userId);
    }

    @Override
    public Boolean fileAlreadyInNote(String noteId, String originalFileName) {
        int index = originalFileName.lastIndexOf(".");
        String fileType = "";
        String fileNameWithoutType = originalFileName;
        if(index != -1){
            fileType = originalFileName.substring(index);
            fileNameWithoutType = originalFileName.substring(0, index);
        }
        String afterName = fileNameWithoutType + "_" + noteId + fileType;
        List<Attachment> atts = attachmentService.getAllAttachments(noteId);
        if (atts != null) {
            for (Attachment att : atts) {
                if (att.getFileName().equals(afterName))
                    return true;
            }
        }
        return false;
    }

    @Override
    public JSONObject getNoteDetailWithAttachment(String noteId) {
        JSONObject re = new JSONObject();
        Note note = noteMapper.getNoteById(noteId);
        re.put("id", note.getNoteId());
        re.put("content", note.getContent());
        re.put("title", note.getTitle());
        re.put("created_on", note.getCreateTime());
        re.put("last_updated_on", note.getLastModifiedTime());

        // put attachments details
        List<Attachment> attList = attachmentService.getAllAttachments(noteId);
        JSONArray jsonArray = new JSONArray();
        for (Attachment att : attList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("attachmentId", att.getAttachmentId());
            jsonObject.put("url", att.getUrl());
            jsonArray.add(jsonObject);
        }
        re.put("attachments", jsonArray);

        return re;
    }

    public void createNew() {
        try {
            noteMapper.createNewTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Note> getNoteByIdSQL(String noteId) {
        List<Note> noteList = noteMapper.getNoteByIdSQL(noteId);
        logger.info("Get note successfully!");
        return noteList;
    }
}
