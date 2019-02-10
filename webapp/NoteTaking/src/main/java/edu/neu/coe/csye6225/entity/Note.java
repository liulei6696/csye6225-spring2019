package edu.neu.coe.csye6225.entity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Note {

    String noteId;
    String userId;
    String title;
    String content;
    Date createTime;
    Date lastModifiedTime;



    public Note() {

    }
//
//    //update note
//    public Note(String noteId, String userId, String title, String content) {
//        this.noteId = noteId;
//        this.userId = userId;
//        this.title = title;
//        this.content = content;
//        this.createTime = getCreateTime();
//        setLastModifiedTime();
//    }

    //create new note, assign note id
    public Note(String userId, String title, String content) {
        setNoteId();
        this.userId = userId;
        this.title = title;
        this.content = content;
        setCreateTime();
        setLastModifiedTime();
    }


    public String getNoteId() {
        return noteId;
    }

    public void setNoteId() {
        this.noteId = UUID.randomUUID().toString();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime() {
        this.createTime = new Date();
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime() {
        this.lastModifiedTime = new Date();
    }


}
