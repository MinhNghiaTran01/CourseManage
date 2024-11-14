package com.javaweb.course.controller.web;

import com.javaweb.course.entity.Comment;
import com.javaweb.course.entity.User;
import com.javaweb.course.model.respone.CommentResponse;
import com.javaweb.course.service.CommentService;
import com.javaweb.course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/lesson/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> findAll(@RequestParam Integer courseId, @RequestParam Integer lessonId) {
        List<Comment> comments = commentService.findByLessonIdAndCourseId(courseId, lessonId);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (Comment comment : comments) {
            User user = userService.findById(comment.getUserId());
            CommentResponse commentResponse = CommentResponse.builder()
                    .userName(user.getUsername())
                    .comment(comment.getComment())
                    .image(user.getImage())
                    .createdAt(comment.getCreatedAt())
                    .build();
            commentResponseList.add(commentResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(commentResponseList);
    }
}
