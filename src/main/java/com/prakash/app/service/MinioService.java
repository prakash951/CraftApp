package com.prakash.app.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MinioService {

  @Value("${minio.url}")
  String endpoint;

  @Value("${minio.accessKey}")
  String accessKey;

  @Value("${minio.secretKey}")
  String secretKey;

  public boolean create(String bucketName) {
    try {
      // Create a minioClient with the MinIO server playground, its access key and
      // secret key.
      MinioClient minioClient =
          MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();

      // Make bucketName bucket if not exist.
      boolean found =
          minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
      if (!found) {
        // Make a new bucket called bucketName.
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
      } else {
        log.info("Bucket " + bucketName + " already exists.");
      }

      //			 Upload '/home/user/Photos/asiaphotos.zip' as object name
      //			 'asiaphotos-2015.zip' to bucket
      //			 'asiatrip'.
      //
      //	minioClient.uploadObject(UploadObjectArgs.builder().bucket("asiatrip").object("asiaphotos-2015.zip")
      //					.filename("/home/user/Photos/asiaphotos.zip").build());
      //			System.out.println("'/home/user/Photos/asiaphotos.zip' is successfully uploaded as "
      //					+ "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");
    } catch (MinioException
        | InvalidKeyException
        | NoSuchAlgorithmException
        | IllegalArgumentException
        | IOException e) {
      log.error("Error occurred: " + e);
      log.error("HTTP trace: " + e.getMessage());
      return false;
    }
    return true;
  }
}
