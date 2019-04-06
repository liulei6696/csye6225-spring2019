package edu.neu.coe.csye6225.mapper;


import com.amazonaws.services.dynamodbv2.xspec.N;
import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteMapper {
    boolean deleteNote(Note note);
    int updateNote(Note note);
    int insertNote(Note note);
    Note getNoteById(@Param("noteId") String noteId);
    List<Note>getAllNotes(String username);
    void createNewTable();
    List<Note> getNoteByIdSQL(@Param("noteId") String noteId);
}
