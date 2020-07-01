package com.wequan.bu.im.db;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

/**
 * @author zhen
 */
// Create the Customer table
@DynamoDbBean
public class Message {

    private Long userId;
    private Long msgId;
    private String dataContent;


    @DynamoDbPartitionKey
    @DynamoDbAttribute("user_id")
    public Long getUserId() {
        return this.userId;
    }

    ;

    public void setUserId(Long userId) {

        this.userId = userId;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("msg_id")
    public Long getMsgId() {
        return this.msgId;

    }

    public void setMsgId(Long msgId) {

        this.msgId = msgId;
    }

    @DynamoDbAttribute("data_content")
    public String getDataContent() {
        return this.dataContent;
    }

    public void setDataContent(String dataContent) {

        this.dataContent = dataContent;
    }
}