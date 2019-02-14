package edu.neu.coe.csye6225.entity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Note {

    String noteId;
    String username;
    String title;
    String content;
    String createTime;
    String lastModifiedTime;



    public Note() {

    }

    //create new note, assign note id
    public Note(String userId, String title, String content) {
        setNoteId();
        this.username = userId;
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
        return username;
    }

    public void setUserId(String userId) {
        this.username = userId;
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

    public String getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId:\"" + noteId + '\"' +
                ", username:\"" + username + '\"' +
                ", title:\"" + title + '\"' +
                ", content:\"" + content + '\"' +
                ", createTime:\"" + createTime + '\"' +
                ", lastModifiedTime:\"" + lastModifiedTime + '\"' +
                '}';
    }

    public void setCreateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createDate = df.format(new Date());
        this.createTime = createDate;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateDate = df.format(new Date());
        this.lastModifiedTime = updateDate;
    }

//    public void setLastModifiedTime(String message) {
//        this.lastModifiedTime = message;
//    }


}
