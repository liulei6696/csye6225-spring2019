package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.entity.User;
import net.sf.json.JSONObject;

import java.util.List;

public interface NoteService {
    Note createNote(User user);
    boolean deleteNote(User user, String noteId);
    Note updateNote(User user,Note note);
    Note getNoteById(User user,String noteId);
    List<Note> getAllNotes(User user);
    Boolean noteBelongToUser(String noteId, String userId);
    JSONObject getNoteDetailWithAttachment (String noteId);
}


