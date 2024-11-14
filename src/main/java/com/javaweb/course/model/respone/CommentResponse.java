package com.javaweb.course.model.respone;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponse {
    private String userName;
    private byte[] image;
    private String comment;
    private Long createdAt;
}
