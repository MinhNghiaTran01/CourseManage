package com.javaweb.course.controller.admin;

import com.javaweb.course.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/s3")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @GetMapping("/generate-presigned-url/upload")
    public String generatePutPresignedUrl(@RequestParam String keyName) {
        return s3Service.createPutPresignedUrl(keyName);
    }

    @GetMapping("/generate-presigned-url/download")
    public String generateGetPresignedUrl(@RequestParam String keyName) {
        return s3Service.createGetPresignedUrl(keyName);
    }

}
