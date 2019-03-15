package edu.neu.coe.csye6225.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.command.BaseModel;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import java.util.UUID;

@Table(name="attachment")
public class Attachment extends BaseModel {
    @Column(name="attachmentId", type= MySqlTypeConstant.VARCHAR, length=100)
    private String attachmentId;
    @Column(name="noteId",type=MySqlTypeConstant.VARCHAR, length=100)
    private String noteId;
    @Column(name="url", type=MySqlTypeConstant.VARCHAR, length=100)
    private String url;
    @Column(name="fileSize", type=MySqlTypeConstant.INT)
    private Long fileSize;
    @Column(name="fileType", type = MySqlTypeConstant.VARCHAR, length=100)
    private String fileType;
    @Column(name="fileName", type = MySqlTypeConstant.VARCHAR, length=100)
    private String fileName;
    @Column(name="eTag", type = MySqlTypeConstant.VARCHAR, length=150)
    private String eTag;


    public Attachment() {
    }

    public Attachment(String noteId, String url, Long fileSize, String fileType, String fileName, String eTag) {
        this.noteId = noteId;
        this.url = url;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.fileName = fileName;
        this.eTag = eTag;
        this.attachmentId = UUID.randomUUID().toString();
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String id) {
        this.attachmentId = id;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attachmentId='" + attachmentId + '\'' +
                ", noteId='" + noteId + '\'' +
                ", url='" + url + '\'' +
                ", fileSize=" + fileSize +
                ", fileType='" + fileType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", eTag='" + eTag + '\'' +
                '}';
    }
}
