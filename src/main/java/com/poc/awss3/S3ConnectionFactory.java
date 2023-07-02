package com.poc.awss3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class S3ConnectionFactory {

    private final String accessKey;
    private final String secretKey;
    private final String encryptionAuthKey;
    private final String endPointUrl;
    private final String region;

    public S3ConnectionFactory(String accessKey, String secretKey, String encryptionAuthKey,
                               String endPointUrl, String region){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.encryptionAuthKey = encryptionAuthKey;
        this.endPointUrl = endPointUrl;
        this.region = region;
    }


    public AmazonS3EncryptionV2 getConnection() throws NoSuchAlgorithmException {
        return new AmazonS3EncryptionClientV2Builder()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withEncryptionMaterialsProvider(new StaticEncryptionMaterialsProvider(new EncryptionMaterials(toSecretKey(encryptionAuthKey))))
                .withCryptoConfiguration(new CryptoConfigurationV2().withCryptoMode(CryptoMode.StrictAuthenticatedEncryption))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPointUrl, region))
                .build();

    }

    public void closeConnetion(AmazonS3EncryptionV2 s3Client){
        if(s3Client != null){
            s3Client.shutdown();
        }
    }

    private SecretKey toSecretKey(String plainKey) throws NoSuchAlgorithmException {
        byte[] encodedKey = Arrays.copyOf(MessageDigest.getInstance("SHA-1").digest(plainKey.getBytes(StandardCharsets.UTF_8)), 16);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

}
