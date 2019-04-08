package edu.neu.coe.csye6225.service;


import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.mapper.NoteMapper;
import edu.neu.coe.csye6225.mapper.UserMapper;
import static org.hamcrest.CoreMatchers.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NoteServiceImplTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NoteMapper noteMapper;

    private User user;
    private Note note;

    /**
     * before each method
     * create user and a note and insert them into database
     */
    @Before
    public void beforeClass() throws Exception{
        user = new User();
        user.setPassword("QAZwsx190!");
        user.setUsername("liulei@me.com");
        accountService.signUp(user);

        note = new Note(user.getUsername(), "Note In Test", "This is a test note created to test NoteService. ");
        noteMapper.insertNote(note);
    }

    /**
     * after each method
     * delete user and note from database
     */
    @After
    public void afterClass() throws Exception{
        userMapper.deleteUserByUsername(user.getUsername());
        noteMapper.deleteNote(note);
    }


    /**
     * create a user and rollback to avoid dirty data
     *
     * if using mysql, it should use InnoDB as storage engine
     * mysql> show variables like '%storage_engine%';
     * latest version already supports that
     */
    @Test
    @Transactional
    public void creatNoteTest() {
        Note newNote = noteService.createNote(user.getUsername());
        assertThat(noteMapper.getNoteById(newNote.getNoteId()), notNullValue());
    }


    /**
     * delete a existed note
     */
    @Test
    public void deleteNoteTest() {
        noteService.deleteNote(note.getNoteId());
        assertThat(noteMapper.getNoteById(note.getNoteId()), nullValue());
    }


    /**
     * update a note test
     */
    @Test
    public void updateNoteTest() {
        String content = "I've changed my mind, this is a diary! ";
        note.setContent(content);
        noteService.updateNote(note);

        assertThat(noteMapper.getNoteById(note.getNoteId()).getContent(), equalTo(content));
    }

    /**
     * get a note by its id test
     */
    @Test
    public void getNodeByIdTest() {
        // TODO: verify the date format of returned note object
        assertThat(noteService.getNoteById( note.getNoteId()).getNoteId(),
                equalTo(noteMapper.getNoteById(note.getNoteId()).getNoteId()));
    }

    /**
     * get all notes test
     */
    @Test
    public void getAllNotesTest() {
        assertThat(noteService.getAllNotes(user.getUsername()).size(), equalTo(1));
        assertThat(noteService.getAllNotes(user.getUsername()).get(0).getNoteId(),
                equalTo(note.getNoteId()));
    }
}
