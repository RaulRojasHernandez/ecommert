package com.tiid.tienda.Utilities;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Log
@Service
public class AmazonUtils {

    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Autowired
    public void setAmazonS3(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void putObject(String keyName, MultipartFile image) {

//        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.addUserMetadata("Content-Type", image.getContentType());
            objectMetadata.addUserMetadata("Content-Length", String.valueOf(image.getSize()));
//            File file = new File("C:/Users/angel/OneDrive/Documentos/imagen/User_icon_2.png");
            amazonS3.putObject(bucketName, keyName, image.getInputStream(), objectMetadata);
//            amazonS3.putObject("ec-mall-resources", keyName, file);
        } catch (Exception e) {
        }
    }

    public byte[] getObject(String keyName) {

//        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
        try {
            S3Object o = amazonS3.getObject(bucketName, keyName);
            S3ObjectInputStream s3is = o.getObjectContent();
            return IOUtils.toByteArray(s3is);
        } catch (AmazonServiceException e) {
        } catch (IOException e) {
           /* System.err.println(e.getMessage());
            System.exit(1);*/
        }
        return null;
    }

    public boolean delete(String keyName) {
        try {
            amazonS3.deleteObject(bucketName, keyName);
            return true;
        } catch (AmazonServiceException e) {
            return false;

        }
    }
}
