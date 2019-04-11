package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.entity.User;
import net.sf.json.JSONObject;

import java.util.List;

public interface NoteService {
    Note createNote(String username);
    boolean deleteNote(String noteId);
    Note updateNote(Note note);
    Note getNoteById(String noteId);
    List<Note> getAllNotes(String username);
    Boolean noteBelongToUser(String noteId, String userId);
    JSONObject getNoteDetailWithAttachment (String noteId);
    void createNew();
    List<Note> getNoteByIdSQL(String noteId);
    Boolean fileAlreadyInNote(String noteId, String originalFileName);
}


