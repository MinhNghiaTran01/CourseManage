package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Course;
import com.javaweb.course.entity.PaymentVnpay;
import com.javaweb.course.entity.RegistrationCourse;
import com.javaweb.course.entity.User;
import com.javaweb.course.model.dto.RegistrationCourseDTO;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.repository.PaymentVnpayRepository;
import com.javaweb.course.repository.RegistrationCourseRepository;
import com.javaweb.course.repository.UserRepository;
import com.javaweb.course.service.RegistrationCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationCourseServiceImpl implements RegistrationCourseService {

    @Autowired
    private PaymentVnpayRepository paymentVnpayRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RegistrationCourseRepository registrationCourseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean save(RegistrationCourseDTO registrationCourseDTO) {
        try {
            PaymentVnpay paymentVnpay = paymentVnpayRepository.findByTransactionId(registrationCourseDTO.getPaymentTransactionId());
            Course course = courseRepository.findById(registrationCourseDTO.getCourseId()).orElse(null);
            User user = userRepository.findById(registrationCourseDTO.getUserId()).orElse(null);
            if (paymentVnpay != null && course != null && user != null) {
                RegistrationCourse registrationCourse = new RegistrationCourse();
                registrationCourse.setCourse(course);
                registrationCourse.setPaymentVnpay(paymentVnpay);
                registrationCourse.setUser(user);
                registrationCourse.setRegistrationDate(registrationCourseDTO.getRegistrationDate());
                registrationCourseRepository.save(registrationCourse);
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<RegistrationCourse> findAll() {
        return registrationCourseRepository.findAll();
    }

    @Override
    public RegistrationCourse findById(Integer id) {
        return null;
    }

    @Override
    public RegistrationCourse findByCourseIdAndUserId(Integer courseId, Integer userId) {
        return registrationCourseRepository.findByCourseIdAndUserId(courseId,userId);
    }

    @Override
    public List<RegistrationCourse> findByUserId(Integer userId) {
        return registrationCourseRepository.findByUserId(userId);
    }
}
