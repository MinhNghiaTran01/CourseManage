package com.javaweb.course.model.dto;

import com.javaweb.course.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDto {

    private Integer id;

    private String code;

    private String courseName;

    private String description;

    private Long price;

    private State state;

    private MultipartFile fileImage;

    private byte[] image;

    private List<String> benefits;

    private String folderId;
}
