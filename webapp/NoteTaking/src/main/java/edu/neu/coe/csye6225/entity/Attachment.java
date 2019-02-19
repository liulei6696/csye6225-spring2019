package edu.neu.coe.csye6225.entity;

import java.util.UUID;

public class Attachment {
    private String attachmentId;
    private String noteId;
    private String url;
    private Long fileSize;
    private String fileType;
    private String fileName;
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
        setAttachmentId();
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId() {
        this.attachmentId = UUID.randomUUID().toString();
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
