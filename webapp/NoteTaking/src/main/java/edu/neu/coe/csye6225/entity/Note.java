package edu.neu.coe.csye6225.entity;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.command.BaseModel;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Table(name="note")
public class Note extends BaseModel {

    @Column(name="noteID", type = MySqlTypeConstant.VARCHAR, length = 100)
    String noteId;
    @Column(name = "username", type = MySqlTypeConstant.VARCHAR, length=100)
    String username;
    @Column(name = "title", type = MySqlTypeConstant.VARCHAR, length = 100)
    String title;
    @Column(name = "content", type = MySqlTypeConstant.TEXT)
    String content;
    @Column(name="createTime", type = MySqlTypeConstant.VARCHAR, length=100)
    String createTime;
    @Column(name = "lastModifiedTime", type = MySqlTypeConstant.VARCHAR, length=100)
    String lastModifiedTime;



    public Note() {
        this.noteId = UUID.randomUUID().toString();
    }

    //create new note, assign note id
    public Note(String userId, String title, String content) {
        this.noteId = UUID.randomUUID().toString();
        this.username = userId;
        this.title = title;
        this.content = content;
        setCreateTime();
        setLastModifiedTime();
    }


    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String newID) {
        this.noteId = newID;
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
