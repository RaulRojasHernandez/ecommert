package com.tiid.tienda.repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;


@Repository
public class StorageRepository {


    final private AmazonS3 amazonS3;

    @Autowired
    public StorageRepository(AmazonS3 s3Client) {
        this.amazonS3 = s3Client;
    }


    public boolean uploadFile(String bucketName, String fileName, File fileObj) {
        try {
            PutObjectResult response = amazonS3.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
            System.out.println(response.toString());
             return true;
        }catch (Exception e){
            return false;
        }
    }
}
