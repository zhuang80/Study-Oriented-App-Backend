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
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;

@Service
public class StorageServiceImpl implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(TutorServiceImpl.class);

    private final Region region = Region.US_EAST_1;
    private final String END_POINT = "http://s3.amazonaws.com";
    private final String ACCESS_KEY = "AKIAIF4OVFNLW2ABXANQ";
    private final String SECRET_KEY = "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";
    private final String bucket = "wq-dev";
    private S3Client s3;

    @Autowired
    private void setS3() {
        s3 = getS3Client();
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
//        GetObjectRequest request = getGetObjectRequest(key);;
//        ResponseInputStream r = s3.getObject(request, ResponseTransformer.toInputStream());
//        InputStreamResource resource = new InputStreamResource(r);
//        try {
//            return resource.getInputStream();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
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

//    private ByteBuffer getRandomByteBuffer(int size) throws IOException {
//        byte[] b = new byte[size];
//        new Random().nextBytes(b);
//        return ByteBuffer.wrap(b);
//    }
}
