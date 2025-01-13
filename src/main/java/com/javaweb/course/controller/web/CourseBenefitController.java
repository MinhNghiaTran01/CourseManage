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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user/course-benefit")
public class CourseBenefitController {

    @Autowired
    private CourseBenefitService courseBenefitService;

    @GetMapping
    public ResponseEntity<CourseBenefitResponse> findByCourseId(@RequestParam("courseId") Integer courseId) {
        CourseBenefit courseBenefits = courseBenefitService.findByCourseId(courseId);
        String benefit = courseBenefits.getBenefit();

        List<String> benefitResponse = new ArrayList<>();
        if(benefit.length()>2) {
            benefit = benefit.substring(1, benefit.length() - 2);
            String [] benefits = benefit.split(",");
            for(String tmp : benefits) {
                tmp = tmp.trim().substring(1, tmp.length() - 2);
                benefitResponse.add(tmp);
            }
        }
        CourseBenefitResponse courseBenefitResponse = new CourseBenefitResponse();
        courseBenefitResponse.setBenefits(benefitResponse);
        return ResponseEntity.ok(courseBenefitResponse);
    }
}
