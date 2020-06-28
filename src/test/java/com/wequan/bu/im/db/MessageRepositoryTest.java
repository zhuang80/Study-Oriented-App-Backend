package com.wequan.bu.im.db;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageRepositoryTest {
//    private DynamoDBMapper dynamoDBMapper;

//    @Autowired
//    private AmazonDynamoDB amazonDynamoDB;
//
//    @Autowired
//    private MessageRepository messageRepository;

    @Before
    public void setup() throws Exception {
//        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    @Test
    public void test() throws Exception {
//        messageRepository.save(new Message(2, 132, "hello"));
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(amazonAWSCredentialsProvider()).build();
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        long tik = System.currentTimeMillis();
        mapper.save(new Message(2, 333, "hello"));
        long tok = System.currentTimeMillis();
        System.out.println("elapse: " + (tok - tik));
        tik = System.currentTimeMillis();
        mapper.save(new Message(5, 15646, "hi"));
        tok = System.currentTimeMillis();
        System.out.println("elapse: " + (tok - tik));
    }

    private String amazonAWSAccessKey = "AKIAT3UCHJRBB7WS5BUB";
    private String amazonAWSSecretKey = "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";

    private AWSCredentialsProvider amazonAWSCredentialsProvider() {
        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
    }

    private AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

}