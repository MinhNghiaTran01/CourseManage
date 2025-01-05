package com.javaweb.course.service.impl;

import com.javaweb.course.entity.LessonCategory;
import com.javaweb.course.enums.State;
import com.javaweb.course.model.dto.LessonCategoryDto;
import com.javaweb.course.model.respone.LessonCategoryResponse;
import com.javaweb.course.repository.LessonCategoryRepository;
import com.javaweb.course.repository.LessonRepository;
import com.javaweb.course.service.GoogleDriveService;
import com.javaweb.course.service.LessonCategoryService;
import com.javaweb.course.untils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class LessonCategoryServiceImpl implements LessonCategoryService {

    @Autowired
    private LessonCategoryRepository lessonCategoryRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private GoogleDriveService googleDriveService;

    @Override
    public void save(LessonCategoryDto lessonCategoryDto) {
        LessonCategory lessonCategory = new LessonCategory();
        lessonCategory.setName(lessonCategoryDto.getName());
        lessonCategory.setCourseId(lessonCategoryDto.getCourseId());
        lessonCategory.setState(State.ACTIVE);
        lessonCategory.setFolderId(lessonCategoryDto.getFolderId());
        lessonCategory.setCreatedAt(Helper.getNowMillisAtUtc());
        lessonCategory.setUpdatedAt(Helper.getNowMillisAtUtc());

        lessonCategoryRepository.save(lessonCategory);
    }

    @Override
    public void update(Integer id, LessonCategoryDto lessonCategoryDto) {
        LessonCategory lessonCategory = lessonCategoryRepository.findById(id).orElse(null);
        if (lessonCategory != null) {
            lessonCategory.setState(lessonCategoryDto.getState());
            lessonCategory.setUpdatedAt(Helper.getNowMillisAtUtc());
            lessonCategoryRepository.save(lessonCategory);
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        LessonCategory lessonCategory = lessonCategoryRepository.findById(id).orElse(null);
        lessonCategoryRepository.deleteById(id);
        lessonRepository.deleteAllByLessonCategoryId(id);
        googleDriveService.deleteFolderById(lessonCategory.getFolderId());
    }

    @Override
    public List<LessonCategoryResponse> findAll() {
        List<LessonCategory> lessonCategories = lessonCategoryRepository.findAll();

        return lessonCategories.stream().map(LessonCategoryResponse::new).toList();
    }

    @Override
    public LessonCategoryResponse findById(Integer id) {
        LessonCategory lessonCategory = lessonCategoryRepository.findById(id).orElse(null);
        if (Objects.isNull(lessonCategory)) {
            throw new RuntimeException(String.format("lessonCategory id: %s is null", id));
        }
        return new LessonCategoryResponse(lessonCategory);
    }

    @Override
    public List<LessonCategoryResponse> findByCourseId(Integer courseId) {
        List<LessonCategory> lessonCategories = lessonCategoryRepository.findAllByCourseId(courseId);

        return lessonCategories.stream().map(LessonCategoryResponse::new).toList();
    }

    @Override
    public void updateNew(LessonCategoryDto lessonCategoryDto) {
        LessonCategory lessonCategory = lessonCategoryRepository.findById(lessonCategoryDto.getId()).orElse(null);
        lessonCategory.setState(lessonCategoryDto.getState());
        lessonCategory.setUpdatedAt(Helper.getNowMillisAtUtc());
        lessonCategoryRepository.save(lessonCategory);
    }
}
