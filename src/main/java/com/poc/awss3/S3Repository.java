package com.poc.awss3;

import com.amazonaws.services.s3.AmazonS3EncryptionV2;
import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class S3Repository {

    private AmazonS3EncryptionV2 client;

    public S3Repository(AmazonS3EncryptionV2 client){
        this.client = client;
    }

    public ObjectMetadata getObject(String bucketName, String key, Path outputPath){
        return client.getObject(new GetObjectRequest(bucketName, key), outputPath.toFile());
    }

    public PutObjectResult putObject(String bucketName, String key, File fileToUpload){
        return client.putObject(new PutObjectRequest(bucketName, key, fileToUpload));
    }

    public List<S3ObjectSummary> getObjectList(String bucketName){
        return client.listObjects(bucketName).getObjectSummaries();
    }

}
