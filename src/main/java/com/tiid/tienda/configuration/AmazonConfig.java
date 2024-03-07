package com.tiid.tienda.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig
{
    @Value("${aws.s3.accessKey}")
    private String accessKey;

    @Value("${aws.s3.secretKey}")
    private String secretKey;

    @Value("${aws.s3.url}")
    private String endpoint;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public static String BUCKET_NAME;

    @Value("${aws.s3.bucketName}")
    public void setNameStatic(String name)
    {
        AmazonConfig.BUCKET_NAME= name;
    }

    @Bean
    public AmazonS3 s3() {
        AmazonS3 s3;
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, "ap-south-1");
        s3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

        if (s3.doesBucketExistV2(bucketName)) {
            return s3;
        } else {
            try {
                s3.createBucket(new CreateBucketRequest(bucketName, String.valueOf(Regions.DEFAULT_REGION)));
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
        }

                return s3;
    }
}