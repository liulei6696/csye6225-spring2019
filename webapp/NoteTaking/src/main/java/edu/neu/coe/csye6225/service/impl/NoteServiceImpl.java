package edu.neu.coe.csye6225.service.impl;

import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.mapper.NoteMapper;
import edu.neu.coe.csye6225.service.AccountService;
import edu.neu.coe.csye6225.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private AccountService accountService;

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
}