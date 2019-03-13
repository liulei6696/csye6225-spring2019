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

    @Override
    public Note createNote(User user) {
        Note note = new Note(user.getUsername(), "Title of the note", "Content of the note");
        if(noteMapper.insertNote(note) > 0) {
            System.out.println("Note created for user: " + user.getUsername());
            return note;
        } else return null;
    }

    @Override
    public boolean deleteNote(User user, String noteId) {
        Note note = noteMapper.getNoteById(noteId);
        if (note == null) return false;
        String noteUserId = note.getUserId();
        if (user.getUsername().equals(noteUserId)) {
            if(noteMapper.deleteNote(note)) {
                System.out.println("Note deleted!");
                return true;
            } else {
                System.out.println("Fail to delete!");
                return false;
            }

        }
        System.out.println("No authorization to this note! Fail to delete!");
        return false;

    }

    @Override
    public Note updateNote(User user,Note note) {
        if (note == null) {
            System.out.println("Please specify the note you want to update!");
            return null;
        }
        Note realNote = noteMapper.getNoteById(note.getNoteId());
        if (user.getUsername().equals(realNote.getUserId())) {
            note.setLastModifiedTime();
            if(noteMapper.updateNote(note) > 0) {
                System.out.println("Note updated!");
                return noteMapper.getNoteById(note.getNoteId());
            } else {
                System.out.println("Fail to update!");
                return null;
            }

        }
        System.out.println("No authorization to this note! Fail to delete!");
        return null;
    }

    @Override
    public Note getNoteById(User user,String noteId) {
        Note note = noteMapper.getNoteById(noteId);
        if (note == null) return note;
        String noteUserId = note.getUserId();
        if (user.getUsername().equals(noteUserId)) {
            note = noteMapper.getNoteById(noteId);
            System.out.println("Get note successfully!");
            return note;
        } else {
            System.out.println("No authorization to this note! Fail to get!");
            return null;
        }


    }


    @Override
    public List<Note> getAllNotes(User user) {
        List<Note> noteList = new ArrayList<>();
            noteList = noteMapper.getAllNotes(user.getUsername());
        return noteList;
    }

    @Override
    public Boolean noteBelongToUser(String noteId, String userId) {
        if(noteMapper.getNoteById(noteId) == null)
            return false;
        return noteMapper.getNoteById(noteId).getUserId().equals(userId);
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
}
