package com.javaweb.course.controller.web;

import com.javaweb.course.entity.CourseBenefit;
import com.javaweb.course.model.respone.CourseBenefitResponse;
import com.javaweb.course.service.CourseBenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user/course-benefit")
public class CourseBenefitController {

    @Autowired
    private CourseBenefitService courseBenefitService;

    @GetMapping
    public ResponseEntity<CourseBenefitResponse> findByCourseId(@RequestParam("courseId") Integer courseId) {
        List<CourseBenefit> courseBenefits = courseBenefitService.findByCourseId(courseId);

        CourseBenefitResponse courseBenefitResponse = new CourseBenefitResponse();
        courseBenefitResponse.setBenefits(
                courseBenefits.stream()
                        .map(CourseBenefit::getBenefit)
                        .collect(Collectors.toList())
        );

        return ResponseEntity.ok(courseBenefitResponse);
    }
}
