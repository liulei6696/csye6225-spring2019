package edu.neu.coe.csye6225.service.impl;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import edu.neu.coe.csye6225.service.AmazonSNSClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AmazonSNSClientServiceImpl implements AmazonSNSClientService {
    private AmazonSNS amazonSNS;
    private AwsClientBuilder awsClientBuilder;
    @Value("${aws.sns.topic.arn}")
    private String topicArn;
    @Value("${domain.name}")
    private String domainName;

    @Autowired
    public AmazonSNSClientServiceImpl()
    {
        this.amazonSNS =  AmazonSNSClientBuilder.standard().withCredentials(new InstanceProfileCredentialsProvider()).build();
    }

/*    public void createSubscription(String email) {
        // Subscribe an email endpoint to an Amazon SNS topic.
        final SubscribeRequest subscribeRequest = new SubscribeRequest(topicArn, "email", email);
        this.amazonSNS.subscribe(subscribeRequest);

        // Print the request ID for the SubscribeRequest action.
        System.out.println("SubscribeRequest: " + this.amazonSNS.getCachedResponseMetadata(subscribeRequest));
        System.out.println("To confirm the subscription, check your email.");

    }*/

    public void publishMessagetoTopic(String email) {
        // Publish a message to an Amazon SNS topic.
        final String msg = "{\n" +
                "\t\"domain\": " + domainName + ",\n" +
                "\t\"email\": " + email + "\n" +
                "}";
        final PublishRequest publishRequest = new PublishRequest(topicArn, msg);
        final PublishResult publishResponse = this.amazonSNS.publish(publishRequest);

        // Print the MessageId of the message.
        System.out.println("MessageId: " + publishResponse.getMessageId());

    }
}
