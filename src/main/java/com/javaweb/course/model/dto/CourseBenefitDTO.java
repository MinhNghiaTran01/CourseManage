package com.javaweb.course.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CourseBenefitDTO {
    private List<String> benefits;
    private String codeCourse;
}
