package com.javaweb.course.controller.web.websocket;

import com.javaweb.course.entity.Student;
import com.javaweb.course.entity.User;
import com.javaweb.course.model.dto.CommentDTO;
import com.javaweb.course.model.respone.CommentResponse;
import com.javaweb.course.service.CommentService;
import com.javaweb.course.service.StudentService;
import com.javaweb.course.service.UserService;
import com.javaweb.course.untils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class CommentsController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @MessageMapping("/learning/comments")
    public void comments(CommentDTO commentDTO) throws Exception {
        commentService.save(commentDTO);
        User user = userService.findById(commentDTO.getUserId());
        Student student = studentService.findById(commentDTO.getUserId());
        CommentResponse commentResponse = CommentResponse.builder()
                .userName(user.getUsername())
                .fullName(student.getFullName())
                .image(user.getImage())
                .comment(commentDTO.getComment())
                .createdAt(Helper.getNowMillisAtUtc())
                .build();
        String destination = String.format("/topic/learning/courseId=%d&lessonId=%d", commentDTO.getCourseId(), commentDTO.getLessonId());
        simpMessagingTemplate.convertAndSend(destination, commentResponse);
    }
}
