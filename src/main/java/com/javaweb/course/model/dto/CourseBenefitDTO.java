package com.javaweb.course.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
public class CourseBenefitDTO {
    private List<String> benefits;
    private String codeCourse;
}
