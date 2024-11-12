package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Comment;
import com.javaweb.course.model.dto.CommentDTO;
import com.javaweb.course.repository.CommentRepository;
import com.javaweb.course.service.CommentService;
import com.javaweb.course.untils.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<Comment> findByLessonIdAndCourseId(Integer courseId, Integer lessonId) {
        List<Comment> comments = commentRepository.findByLessonIdAndCourseId(lessonId, courseId);
        return comments;
    }

    @Async
    @Override
    public void save(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setCreatedAt(Helper.getNowMillisAtUtc());
        commentRepository.save(comment);
    }
}
