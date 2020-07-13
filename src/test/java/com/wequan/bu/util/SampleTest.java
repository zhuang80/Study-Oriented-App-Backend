package com.wequan.bu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wequan.bu.WeQuanApplication;
import com.wequan.bu.controller.vo.Condition;
import com.wequan.bu.repository.model.*;
import com.wequan.bu.service.CommonDataService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.Thread;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeQuanApplication.class)
public class SampleTest {

    @Autowired
    private CommonDataService commonDataService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    public void testAwsS3() {

    }

    @Test
    public void testRedis() throws InterruptedException {
        int threadNum = 100;
        Thread[] threads = new Thread[threadNum];
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(() -> {
                try {
                    countDownLatch.await();
                    List<School> schoolData = commonDataService.getSchoolData();
                    System.out.println(schoolData.get(0).getName());
                    List<Subject> subjectData = commonDataService.getSubjectData();
                    System.out.println(subjectData.get(0).getName());
                    List<Tag> tagData = commonDataService.getTagData();
                    System.out.println(tagData.get(0).getName());
                    List<Topic> topicData = commonDataService.getTopicData();
                    System.out.println(topicData.get(0).getName());
                    List<Degree> degreeData = commonDataService.getDegreeData();
                    System.out.println(degreeData.get(0).getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads[i] = thread;
            thread.start();
            countDownLatch.countDown();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

}
