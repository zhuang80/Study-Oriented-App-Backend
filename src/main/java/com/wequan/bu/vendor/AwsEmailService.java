package com.wequan.bu.vendor;

import com.wequan.bu.config.properties.AppProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Properties;

/**
 * @author ChrisChen
 */
@Component
public class AwsEmailService {

    private static final Logger log = LoggerFactory.getLogger(AwsEmailService.class);

    private static final String DEFAULT_EMAIL_SENDER = "bingo.tech.20@gmail.com";
    private static final long TOKEN_EXPIRATION_IN_MSC = 1800000;
    private static final String EMAIL_CONFIRM_BASE_URL = "http://ec2-107-21-15-243.compute-1.amazonaws.com:8080/api/user/e-confirm?token=";

    @Value("${aws.ses.access-key-id}")
    private String accessKeyId;
    @Value("${aws.ses.secret-access-key}")
    private String secretAccessKey;
    @Value("${aws.ses.enabled}")
    private boolean enabled;
    @Autowired
    private AppProperties appProperties;

    // The email body for recipients with non-HTML email clients
    private static String BODY_TEXT = "Please confirm your email via __EMAIL_CONFIRM_LINK__ to complete the registration.";
    // The HTML body of the email
    private static String BODY_HTML = "<html><head></head><body><h3>Please confirm your email via __EMAIL_CONFIRM_LINK__ to complete the registration.</h3></body></html>";

    public void sendRegisterEmail(String receiver, String userName) {
        Date now = new Date();
        String token = Jwts.builder()
                .setSubject(receiver + "||" + userName)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_EXPIRATION_IN_MSC))
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
        if (!enabled) {
            log.info("Email confirm URL = {}", EMAIL_CONFIRM_BASE_URL + token);
        } else {
            Session session = Session.getDefaultInstance(new Properties());
            try {
                // Build a new MimeMessage object
                MimeMessage message = new MimeMessage(session);
                message.setSubject("Please confirm your email address", "UTF-8");
                message.setFrom(new InternetAddress(DEFAULT_EMAIL_SENDER));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));

                MimeBodyPart wrap = new MimeBodyPart();
                // Create a multipart/alternative child container
                MimeMultipart msgBody = new MimeMultipart("alternative");
                // Define the text part
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setContent(BODY_TEXT.replaceAll("__EMAIL_CONFIRM_LINK__", EMAIL_CONFIRM_BASE_URL + token), "text/plain; charset=UTF-8");
                // Define the HTML part
                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(BODY_HTML.replaceAll("__EMAIL_CONFIRM_LINK__", EMAIL_CONFIRM_BASE_URL + token), "text/html; charset=UTF-8");
                msgBody.addBodyPart(textPart);
                msgBody.addBodyPart(htmlPart);
                wrap.setContent(msgBody);

                // Create a multipart/mixed parent container
                MimeMultipart msg = new MimeMultipart("mixed");
                msg.addBodyPart(wrap);
                message.setContent(msg);

                // Build SesClient
                Region region = Region.US_EAST_1;
                StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)
                );
                SesClient sesClient = SesClient.builder().credentialsProvider(staticCredentialsProvider).region(region).build();
                // Send by AWS SDK
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                message.writeTo(outputStream);
                ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());
                byte[] arr = new byte[buf.remaining()];
                buf.get(arr);
                SdkBytes data = SdkBytes.fromByteArray(arr);
                RawMessage rawMessage = RawMessage.builder().data(data).build();
                SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder().rawMessage(rawMessage).build();
                sesClient.sendRawEmail(rawEmailRequest);
                log.info("confirm email to {} send", receiver);
            } catch (Exception e) {
                log.error("exception in sending email", e);
            }
        }

    }

    public static void main(String[] args) {
        String receiver = "bingo.tech.20@gmail.com";
        String userName = "bingo.tech.20";
        Date now = new Date();
        String token = Jwts.builder()
                .setSubject(receiver + "||" + userName)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_EXPIRATION_IN_MSC))
                .signWith(SignatureAlgorithm.HS512, "926D96C90030DD58429D2751AC1BDBBC")
                .compact();
        System.out.println(token);
    }

}
