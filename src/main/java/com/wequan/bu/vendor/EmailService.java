package com.wequan.bu.vendor;

import com.wequan.bu.exception.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author ChrisChen
 */
@Component
public class EmailService {

    private static final String SEND_CLOUD_EMAIL_API = "http://api2.sendcloud.net/api/mail/";
    private static final String DEFAULT_EMAIL_SENDER = "noreply@wequan.com";
    private static final String DEFAULT_EMAIL_TEMPLATE_NAME = "wequan_email_verification";

    @Value("${sendcloud.email.api-user}")
    private String apiUser;
    @Value("${sendcloud.email.api-key}")
    private String apiKey;
    @Value("${sendcloud.email.domain}")
    private String domain;

    @Autowired
    private RestTemplate restTemplate;

    public String send(String receiver, String subject, String content) throws NotImplementedException {
        throw new NotImplementedException();
    }

    public String sendTemplate(String receiver, String nickname) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("apiUser", apiUser);
        params.add("apiKey", apiKey);
        params.add("from", DEFAULT_EMAIL_SENDER);
        params.add("fromName", "圈子");
        params.add("templateInvokeName", DEFAULT_EMAIL_TEMPLATE_NAME);
        params.add("xsmtpapi", "{\"to\": [\"" + receiver + "\"],\"sub\":{\"%user_name%\": [\"Ben\"],\"%money%\":[288]}}");
        //WebClient for asynchronous in future
        ResponseEntity<String> resp = restTemplate.postForEntity(SEND_CLOUD_EMAIL_API + "sendtemplate", params, String.class);
        return resp.getBody();
    }

}
