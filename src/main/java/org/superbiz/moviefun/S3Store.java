package org.superbiz.moviefun;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

import java.io.IOException;
import java.util.Optional;

public class S3Store implements BlobStore {
    private final AmazonS3Client s3Client;
    private final String s3BucketName;

    public S3Store(AmazonS3Client s3Client, String s3BucketName) {
        this.s3Client = s3Client;
        this.s3BucketName = s3BucketName;
        if(!s3Client.doesBucketExist(s3BucketName))
        {
            s3Client.createBucket(s3BucketName);
        }

    }

    @Override
    public void put(Blob blob) throws IOException {
        ObjectMetadata metaData = new ObjectMetadata();
        metaData.addUserMetadata("content-type",blob.contentType);
        System.out.println("hit it! sergei: "+blob.name);
        this.s3Client.putObject(this.s3BucketName,blob.name,blob.inputStream,metaData);
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        S3Object s3Object = this.s3Client.getObject(this.s3BucketName,name);
        return Optional.of(new Blob(s3Object.getKey(),s3Object.getObjectContent(),s3Object.getObjectMetadata().getContentType()));
    }

    @Override
    public void deleteAll() {
        this.s3Client.deleteBucket(this.s3BucketName);
    }

}
