package edu.neu.coe.csye6225.entity;

import java.util.UUID;

public class Attachment {
    private String attachmentId;
    private String noteId;
    private String url;
    private String owner;
    private String lastModifiedTime;
    private String eTag;
    private String storageClass;
    private String serverSideEncryption;
    private Double fileSize;
    private String fileKey;

    public Attachment() {
    }

    public Attachment(String noteId, String url, String owner, String lastModifiedTime, String eTag, String storageClass, String serverSideEncryption, Double fileSize, String fileKey) {
        setAttachmentId();
        this.noteId = noteId;
        this.url = url;
        this.owner = owner;
        this.lastModifiedTime = lastModifiedTime;
        this.eTag = eTag;
        this.storageClass = storageClass;
        this.serverSideEncryption = serverSideEncryption;
        this.fileSize = fileSize;
        this.fileKey = fileKey;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }

    public String getServerSideEncryption() {
        return serverSideEncryption;
    }

    public void setServerSideEncryption(String serverSideEncryption) {
        this.serverSideEncryption = serverSideEncryption;
    }

    public Double getFIleSize() {
        return fileSize;
    }

    public void setFIleSize(Double fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attachmentId='" + attachmentId + '\'' +
                ", noteId='" + noteId + '\'' +
                ", url='" + url + '\'' +
                ", owner='" + owner + '\'' +
                ", lastModifiedTime='" + lastModifiedTime + '\'' +
                ", eTag='" + eTag + '\'' +
                ", storageClass='" + storageClass + '\'' +
                ", serverSideEncryption='" + serverSideEncryption + '\'' +
                ", fileSize=" + fileSize +
                ", fileKey='" + fileKey + '\'' +
                '}';
    }
}
