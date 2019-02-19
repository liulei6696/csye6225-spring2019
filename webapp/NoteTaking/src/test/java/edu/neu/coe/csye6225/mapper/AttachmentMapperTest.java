package edu.neu.coe.csye6225.mapper;


import static org.junit.Assert.*;
import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.entity.Attachment;
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
public class AttachmentMapperTest {
   @Autowired
   private AttachmentMapper attachmentMapper;


    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    @Test
    @Transactional
    public void testInsertAttachment(){
        Note note = new Note(UUID.randomUUID().toString(),"a","b");
        Attachment attachment = new Attachment(note.getNoteId(),"aa",123l,"bb","cc","dd");
        attachmentMapper.insertAttachment(attachment);
        assertNotNull(attachmentMapper.getAttachmentById(attachment.getAttachmentId()));
    }

    @Test
    @Transactional
    public void testGetAllAttachments() {
//        String userId, String title, String content
        Note note = new Note(UUID.randomUUID().toString(),"noteTitle","noteContent");
//        String noteId, String url, Long fileSize, String fileType, String fileName, String eTag
        Attachment a1 = new Attachment(note.getNoteId(),"localhost:8080",5050l,"docx","filename","eee");
        Attachment a2 = new Attachment(note.getNoteId(),"localhost:8081",1010l,"jpg","name2","aaa");
        int size1 = attachmentMapper.getAllAttachment(note.getNoteId()).size();
        attachmentMapper.insertAttachment(a1);
        attachmentMapper.insertAttachment(a2);
        assertEquals(2+size1,attachmentMapper.getAllAttachment(note.getNoteId()).size());
    }


    @Test
    @Transactional
    public void testGetAttachmentByAttachmentId() {
//        String userId, String title, String content
        Note note = new Note(UUID.randomUUID().toString(),"noteTitle","noteContent");
//        String noteId, String url, Long fileSize, String fileType, String fileName, String eTag
        Attachment a1 = new Attachment(note.getNoteId(),"localhost:8080",5050l,"docx","filename","eee");
        Attachment a2 = new Attachment(note.getNoteId(),"localhost:8081",1010l,"jpg","name2","aaa");
        attachmentMapper.insertAttachment(a1);
        assertNotNull(attachmentMapper.getAttachmentById(a1.getAttachmentId()));
    }

    @Test
    @Transactional
    public void testUpdateAttachment(){
//        String userId, String title, String content
        Note note = new Note(UUID.randomUUID().toString(),"noteTitle","noteContent");
//        String noteId, String url, Long fileSize, String fileType, String fileName, String eTag
        Attachment a1 = new Attachment(note.getNoteId(),"localhost:8080",5050l,"docx","filename","eee");
        a1.setUrl("localhost:8082");
        a1.setFileSize(1110l);
        a1.setFileType("jpg");
        a1.setFileName("newFIleName");
        a1.seteTag("123");
        attachmentMapper.updateAttachment(a1);
        System.out.println(attachmentMapper.getAttachmentById(a1.getAttachmentId()));
//        assertNotEquals("filename",attachmentMapper.getAttachmentById(a1.getAttachmentId()).getFileName());
    }

    @Test
    @Transactional
    public void testDeleteAttachment(){
//        String userId, String title, String content
        Note note = new Note(UUID.randomUUID().toString(),"noteTitle","noteContent");
//        String noteId, String url, Long fileSize, String fileType, String fileName, String eTag
        Attachment a1 = new Attachment(note.getNoteId(),"localhost:8080",5050l,"docx","filename","eee");
        Attachment a2 = new Attachment(note.getNoteId(),"localhost:8081",1010l,"jpg","name2","aaa");
        attachmentMapper.insertAttachment(a1);
        assertNotNull(attachmentMapper.getAttachmentById(a1.getAttachmentId()));
        attachmentMapper.deleteAttachment(a1);
        assertNull(attachmentMapper.getAttachmentById(a1.getAttachmentId()));
    }

}
