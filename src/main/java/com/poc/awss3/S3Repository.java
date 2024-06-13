package com.poc.awss3;

import com.amazonaws.services.s3.AmazonS3EncryptionV2;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.SetObjectMetadataRequest;

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

    /*public void updateObjectMetadata(String bucketName, String key){
       try{
           ObjectMetadata metadata = client.getObjectMetadata(bucketName, key);
           // Set the expiration time to one month from now
           metadata.setExpirationTime(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000));

           // Update the metadata of the object
           SetObjectMetadataRequest request = new SetObjectMetadataRequest(bucketName, key, metadata);
           client.setObjectMetadata(request);
       }catch (AmazonServiceException e) {
           // Handle Amazon S3 service exceptions
           e.printStackTrace();
       } catch (SdkClientException e) {
           // Handle Amazon S3 client exceptions
           e.printStackTrace();
       }

    }*/
}
