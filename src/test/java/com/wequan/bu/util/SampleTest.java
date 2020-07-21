package com.wequan.bu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wequan.bu.WeQuanApplication;
import com.wequan.bu.controller.vo.Condition;
import com.wequan.bu.controller.vo.Token;
import com.wequan.bu.repository.model.*;
import com.wequan.bu.security.authentication.token.UserNamePasswordAuthenticationToken;
import com.wequan.bu.security.component.TokenProvider;
import com.wequan.bu.service.CommonDataService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.lang.Thread;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private TokenProvider tokenProvider;

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

    @Test
    public void testRefreshToken() {
        Authentication authentication = new UserNamePasswordAuthenticationToken(5, null);
        Token refreshToken = tokenProvider.createRefreshToken(authentication);
        System.out.println(refreshToken.getToken());
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("30094969A24217D99F46A4EBB3BA9FB80D471B48AF545DBBF472C029041836FF").parseClaimsJws(refreshToken.getToken());
        Date expiration = claimsJws.getBody().getExpiration();
        String subject = claimsJws.getBody().getSubject();
        System.out.println(expiration);
        System.out.println(subject);
    }

    @Test
    public void testTokenRefresh()  throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:8080/api/access_token/refresh?refreshToken={refreshToken}";
        int num = 20;
        Thread[] threads = new Thread[num];
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Map<String, String> params = new HashMap<>();
                    params.put("refreshToken", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3IiwiaWF0IjoxNTk1MzY0Mzk0LCJleHAiOjE1OTUzNjQ2MDR9.4SFEzmx943HyUUJOwbvYP17JtjuLC3d9v0ausvRH2Jw");
                    countDownLatch.await();
                    ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class, params);
                    JsonNode node = new ObjectMapper().readTree(resp.getBody());
                    System.out.println("access_token = " + node.get("data").get("access_token").asText());
                    System.out.println("refresh_token = " + node.get("data").get("refresh_token").asText());
                } catch (JsonProcessingException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads[i] = thread;
            thread.start();
            countDownLatch.countDown();
        }
        for (int i = 0; i < num; i++) {
            threads[i].join();
        }
    }

}
