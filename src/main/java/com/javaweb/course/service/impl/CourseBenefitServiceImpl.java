package com.javaweb.course.service.impl;

import com.javaweb.course.entity.CourseBenefit;
import com.javaweb.course.model.respone.CourseBenefitResponse;
import com.javaweb.course.repository.CourseBenefitRepository;
import com.javaweb.course.service.CourseBenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseBenefitServiceImpl implements CourseBenefitService {

  @Autowired
  private CourseBenefitRepository courseBenefitRepository;

  @Override
  public void save(Object o) {

  }

  @Override
  public void update(Integer id, Object o) {

  }

  @Override
  public void delete(Integer id) {

  }

  @Override
  public List<CourseBenefitResponse> findAll() {
    return List.of();
  }

  @Override
  public CourseBenefitResponse findById(Integer id) {
    CourseBenefit courseBenefit = courseBenefitRepository.findById(id).orElse(null);

    if(courseBenefit == null) {
      throw new RuntimeException(String.format("courseBenefit has id %s is not found", id));
    }
    return new CourseBenefitResponse(courseBenefit);
  }
}
