package com.wequan.bu.im.db;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.*;
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
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class MessageRepositoryTest {
//    private DynamoDBMapper dynamoDBMapper;

//    @Autowired
//    private AmazonDynamoDB amazonDynamoDB;
//
//    @Autowired
//    private MessageRepository messageRepository;

    @Autowired
    private DynamoDBMapper mapper;

    @Before
    public void setup() throws Exception {
//        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    @Test
    public void test() throws Exception {

        // Create a DynamoDbClient object
        Region region = Region.US_EAST_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .credentialsProvider(amazonAWSCredentialsProvider())
                .region(region)
                .build();

        // Create a DynamoDbEnhancedClient and use the DynamoDbClient object
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();


        try {
            DynamoDbTable<Message> custTable = enhancedClient.table("msg_sync", TableSchema.fromBean(Message.class));


            // Populate the table
            Message m = new Message();
            m.setUserId(1L);
            m.setMsgId(1234123412341234L);
            m.setDataContent("hello");

            // Put the customer data into a DynamoDB table
            custTable.putItem(m);
        } catch (DynamoDbException exception) {
            exception.printStackTrace();
        }


//        messageRepository.save(new Message(2, 132, "hello"));
//        BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAT3UCHJRBB7WS5BUB", "KrteLjIDsyXqqImSHb3VwAojEx59C2iTZvkxwots");
//        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//                .withRegion(Regions.US_EAST_1)
////                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
//                .withCredentials(amazonAWSCredentialsProvider()).build();
////        client.shutdown();
////        return;
//        ListTablesResult r = client.listTables();
//        System.out.println(r.getTableNames());
//        DynamoDBMapper mapper = new DynamoDBMapper(client);
//        long tik = System.currentTimeMillis();
//        mapper.save(new Message(1L, 1278092367294890071L, "hello: 1"));
//        long tok = System.currentTimeMillis();
//        System.out.println("elapse: " + (tok - tik));
//        tik = System.currentTimeMillis();
//        mapper.save(new Message(5, 15646, "ttt"));
//        tok = System.currentTimeMillis();
//        System.out.println("elapse: " + (tok - tik));
    }

    @Test
    public void testtest() throws Exception {
//        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//                .withRegion(Regions.US_EAST_1)
//                .withCredentials(amazonAWSCredentialsProvider()).build();
//        DynamoDB dynamoDB = new DynamoDB(client);
//        Map<String, AttributeValue> attr = new HashMap();
//        attr.put("user_id", new AttributeValue().with);
//        attr.put()
//        PutItemRequest request = new PutItemRequest().withTableName("msg_sync")
//        client.putItem()
    }

    private String amazonAWSAccessKey = "AKIAT3UCHJRBB7WS5BUB";
    private String amazonAWSSecretKey = "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";

    private AwsCredentialsProvider amazonAWSCredentialsProvider() {
        return AwsCredentialsProviderChain.builder().addCredentialsProvider(this::amazonAWSCredentials).build();
    }

    private AwsCredentials amazonAWSCredentials() {
        return AwsBasicCredentials.create(amazonAWSAccessKey, amazonAWSSecretKey);

//        return new BasicAwsCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

}