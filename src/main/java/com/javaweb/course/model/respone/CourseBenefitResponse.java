package com.javaweb.course.model.respone;

import com.javaweb.course.entity.CourseBenefit;
import com.javaweb.course.untils.JsonParser;
import lombok.Data;

import java.util.List;

@Data
public class CourseBenefitResponse {

  private Integer courseId;

  private List<String> benefits;

  public CourseBenefitResponse(CourseBenefit courseBenefit) {
    this.courseId = courseBenefit.getCourseId();
    this.benefits = JsonParser.getList(courseBenefit.getBenefits());
  }
}
