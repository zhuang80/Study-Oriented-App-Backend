package com.wequan.bu.service.impl;

import com.wequan.bu.service.StorageService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

@Service
public class StorageServiceImpl implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(TutorServiceImpl.class);

    private final Region region = Region.US_EAST_1;
    private final String END_POINT = "http://s3.amazonaws.com";
    private final String ACCESS_KEY = "AKIAT3UCHJRBB7WS5BUB";
    private final String SECRET_KEY = "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";
    private final String bucket = "wq-dev";
    private S3Client s3;
    private S3Presigner presigner;

    @Autowired
    private void setS3() {
        s3 = getS3Client();
    }

    @Autowired
    private void setPresigner() {
        AwsSessionCredentials awsCreds = AwsSessionCredentials.create(ACCESS_KEY, SECRET_KEY, "");
        presigner = S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(region).build();
    }

    private boolean upload(PutObjectRequest request, RequestBody body) {
        try {
            s3.putObject(request, body);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean uploadBufferedImage(String key, BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
        } catch (Exception e) {
        }
        byte[] buffer = os.toByteArray();
        InputStream is = new ByteArrayInputStream(buffer);
        long len = buffer.length;
        PutObjectRequest request = getPutObjectRequest(key);
        RequestBody body = RequestBody.fromInputStream(is, len);
        return upload(request, body);
    }

    @Override
    public byte[] download(String key) {
        try {
            ResponseBytes<GetObjectResponse> s3Object = s3.getObject(
                    GetObjectRequest.builder().bucket(bucket).key(key).build(),
                    ResponseTransformer.toBytes());
            final byte[] bytes = s3Object.asByteArray();
            return bytes;
        } catch (SdkClientException ase) {
            logger.error("Caught an AmazonServiceException, which " + "means your request made it "
                    + "to Amazon S3, but was rejected with an error response" + " for some reason: " + key, ase);
            throw ase;
        } catch (SdkServiceException ace) {
            logger.error("Caught an AmazonClientException, which " + "means the client encountered "
                    + "an internal error while trying to " + "communicate with S3, "
                    + "such as not being able to access the network: " + key, ace);
            throw ace;
        }
    }

    @Override
    public URL getPresignedURL(String key) {
        try {
            PresignedPutObjectRequest presignedRequest =
                    presigner.presignPutObject(z -> z.signatureDuration(Duration.ofMinutes(10))
                            .putObjectRequest(por -> por.bucket(bucket).key(key)));

            return presignedRequest.url();
        } catch (S3Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    private S3Client getS3Client() {

        AwsSessionCredentials awsCreds = AwsSessionCredentials.create(ACCESS_KEY, SECRET_KEY, "");

        S3Client s3 = S3Client.builder().credentialsProvider(
                StaticCredentialsProvider.create(awsCreds))
                .endpointOverride(URI.create(END_POINT)).region(region).build();

        return s3;
    }

    private PutObjectRequest getPutObjectRequest(String key) {
        return PutObjectRequest.builder().bucket(bucket).key(key).build();
    }

    private GetObjectRequest getGetObjectRequest(String key) {
        return GetObjectRequest.builder().bucket(bucket).key(key).build();
    }

    private RequestBody getRequestBody(File file) {
        return RequestBody.fromFile(file);
    }

    private RequestBody getRequestBody(InputStream is, long len) {
        return RequestBody.fromInputStream(is, len);
    }


}
