package com.javaweb.course.service;

import com.javaweb.course.model.dto.LessonDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinIOService {

    public void uploadFile(String objectName, LessonDto lessonDto, MultipartFile file);

    public InputStream getVideo(String videoName) ;
}
