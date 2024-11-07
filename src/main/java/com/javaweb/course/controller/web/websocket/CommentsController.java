package com.javaweb.course.controller.web.websocket;

import com.javaweb.course.model.dto.CommentDTO;
import com.javaweb.course.model.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class CommentsController {

    @MessageMapping("/comments")
    @SendTo("/learnings/comments")
    public CommentDTO comments(MessageDTO messageDTO) throws Exception {
        return new CommentDTO();
    }

}
