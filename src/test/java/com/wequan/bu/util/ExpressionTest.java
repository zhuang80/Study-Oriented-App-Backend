package com.wequan.bu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wequan.bu.controller.vo.Condition;
import org.junit.Assert;
import org.junit.Test;

public class ExpressionTest {

    @Test
    public void testExpressionCheck() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"expression\":{\"and\":[{\"field\":\"name\",\"value\":\"abc\",\"type\":\"string\",\"op\":\"like\"},{\"field\":\"topic\",\"value\":\"cs\",\"type\":\"string\",\"op\":\"like\"}]},\"sort\":[{\"field\":\"name\",\"value\":\"asc\"}],\"page\":{\"no\":1,\"size\":10}}";
        Condition condition = objectMapper.readValue(json, Condition.class);
        boolean check = condition.selfCheck();
        Assert.assertTrue(check);
        json = "{\"expression\":{\"field\":\"name\",\"value\":\"abc\",\"type\":\"string\",\"op\":\"like\"},\"sort\":[{\"field\":\"name\",\"value\":\"asc\"},{\"field\":\"create_time\",\"value\":\"desc\"}]}";
        condition = objectMapper.readValue(json, Condition.class);
        check = condition.selfCheck();
        Assert.assertTrue(check);
    }

}
