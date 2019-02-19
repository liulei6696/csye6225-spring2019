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

}
