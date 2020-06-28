package com.wequan.bu.im.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * @author zhen
 */
@DynamoDBTable(tableName = "msg_sync")
public class Message {
    private Integer user_id;
    private Integer msg_id;
    private String data_content;

    public Message(Integer user_id, Integer msg_id, String data_content) {
        this.user_id = user_id;
        this.msg_id = msg_id;
        this.data_content = data_content;
    }

    @DynamoDBHashKey(attributeName = "user_id")
    public Integer getUserId() {
        return this.user_id;
    }

    @DynamoDBRangeKey(attributeName = "msg_id")
    public Integer getMsgId() {
        return this.msg_id;
    }

    @DynamoDBAttribute(attributeName = "data_content")
    public String getDataContent() {
        return this.data_content;
    }
}
