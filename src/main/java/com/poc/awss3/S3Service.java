package com.poc.awss3;

import com.amazonaws.services.s3.AmazonS3EncryptionV2;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;


public class S3Service {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        // Reading property file.
        S3Service obj = new S3Service();
        String accessKey = "AKIA2C62N7ITIYD3HOGU";
        String secretKey = "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";
        String region = "ap-south-1";
        String encryptionAuthKey="my-encryption-key";
        String endPointUrl="https://s3.amazonaws.com/";

        final S3ConnectionFactory connFactory =
                getS3ConnectionFactory(accessKey, secretKey, encryptionAuthKey, endPointUrl, region);

        final AmazonS3EncryptionV2 connection = connFactory.getConnection();

        final S3Repository s3Repository = new S3Repository(connection);
        try {
            File file = new File("/Users/anujmehra/git/aws-s3/src/main/resources/application.properties");
            s3Repository.putObject("data.1.icsdpn","tmp/application.conf", file);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        final List<S3ObjectSummary> objects = s3Repository.getObjectList("data.1.icsdpn");
        /*objects.forEach(e => System.out.println(e.));*/
    }

    public static final S3ConnectionFactory getS3ConnectionFactory(String accessKey, String secretKey, String encryptionAuthKey,
                                                             String endPointUrl, String region){
        return new S3ConnectionFactory(accessKey, secretKey, encryptionAuthKey, endPointUrl, region);
    }

}
