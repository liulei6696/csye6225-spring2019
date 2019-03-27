package edu.neu.coe.csye6225.service;

public interface AmazonSNSClientService {
//    void createSubscription (String email);
    void publishMessagetoTopic(String email);
}
