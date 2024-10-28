package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Course;
import com.javaweb.course.entity.Lesson;
import com.javaweb.course.entity.type.State;
import com.javaweb.course.model.dto.LessonDto;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.service.MinIOService;
import io.minio.GetObjectArgs;
import io.minio.PutObjectArgs;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.minio.MinioClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;
import java.util.Objects;

@Service
@Log4j2
public class FileVideoService implements MinIOService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    @Value("${minio.put-object-part-size}")
    private Long putObjectPartSize;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadFile(LessonDto lessonDto, MultipartFile file) {
        Course course = courseRepository.findById(lessonDto.getCourseId()).orElse(null);
        if (!Objects.isNull(course)) {
            throw new RuntimeException(String.format("course has id: %s not found", lessonDto.getCourseId()));
        }
        String videoUrl = course.getCourseName() + "/" + lessonDto.getLessonName() + '.' + file.getOriginalFilename();

        Lesson lesson = new Lesson();
        lesson.setCourseId(lessonDto.getCourseId());
        lesson.setDescription(lessonDto.getDescription());
        lesson.setLessonName(lessonDto.getLessonName());
        lesson.setDuration(lessonDto.getDuration());
        lesson.setState(State.ACTIVE);

        courseRepository.save(course);

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(videoUrl)
                            .stream(file.getInputStream(), file.getSize(), putObjectPartSize)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("push video to minIO failed");
        }
    }

    @Override
    public InputStream getVideo(String videoName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(videoName)
                            .build()
            );
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

}
