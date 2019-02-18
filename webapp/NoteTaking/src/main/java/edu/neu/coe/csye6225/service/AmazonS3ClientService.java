package edu.neu.coe.csye6225.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3ClientService {
    void createAttachmentToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess);

    void deleteAttachmentFromS3Bucket(String fileName);
}
