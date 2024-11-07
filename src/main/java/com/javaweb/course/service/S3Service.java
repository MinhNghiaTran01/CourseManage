package com.javaweb.course.service;

public interface S3Service {

    public String createPutPresignedUrl(String objectKey);

    public String createGetPresignedUrl(String objectKey);
}
