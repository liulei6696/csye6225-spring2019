package edu.neu.coe.csye6225.mapper;

import static org.junit.Assert.*;
import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class NoteMapperTest {
    @Autowired
    private NoteMapper noteMapper;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    @Test
    @Transactional
    public void testGetAllNotes() {
        User user = new User(UUID.randomUUID().toString(),"bbb");
        Note note1 = new Note(user.getUsername(),"aa","bbb");
        Note note2 = new Note(user.getUsername(),"bb","ccc");
        int size1 = noteMapper.getAllNotes(user.getUsername()).size();
        noteMapper.insertNote(note1);
        noteMapper.insertNote(note2);
        assertEquals(2+size1,noteMapper.getAllNotes(user.getUsername()).size());
    }

    @Test
    @Transactional
    public void testGetNoteByNoteId() {
        User user = new User(UUID.randomUUID().toString(),"bbb");
        Note note = new Note(user.getUsername(),"aa","ccccc");
        noteMapper.insertNote(note);
        assertNotNull(noteMapper.getNoteById(note.getNoteId()));
    }

    @Test
    @Transactional
    public void testUpdateNote(){
        String uid = UUID.randomUUID().toString();
        Note note = new Note(uid,"title","content");
        noteMapper.insertNote(note);
        note.setContent("newContent");
        noteMapper.updateNote(note);
        assertNotEquals("content",noteMapper.getNoteById(note.getNoteId()).getContent());

    }


    @Test
    @Transactional
    public void testInsertNote(){
        User user = new User(UUID.randomUUID().toString(),"bbb");
        Note note1 = new Note(user.getUsername(),"aa","bbb");
        noteMapper.insertNote(note1);
        assertNotNull(noteMapper.getNoteById(note1.getNoteId()));
    }

    @Test
    @Transactional
    public void testDeleteNote(){
        User user = new User(UUID.randomUUID().toString(),"bbb");
        Note note1 = new Note(user.getUsername(),"aa","bbb");
        noteMapper.insertNote(note1);
        assertNotNull(noteMapper.getNoteById(note1.getNoteId()));
        noteMapper.deleteNote(note1);
        assertNull(noteMapper.getNoteById(note1.getNoteId()));
    }

}