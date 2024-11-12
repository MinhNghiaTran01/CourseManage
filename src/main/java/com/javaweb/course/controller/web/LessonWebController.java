//package com.javaweb.course.controller.web;
//
//import com.javaweb.course.service.impl.FileVideoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.InputStream;
//
//@RestController
//@RequestMapping("v1/web/lesson")
//public class LessonWebController {
//
//    @Autowired
//    private FileVideoService fileVideoService;
//
//    @GetMapping("{lessonName}")
//    public void getVideo(@PathVariable(value = "lessonName") String lessonName, HttpServletResponse response) {
//        try (InputStream videoStream = fileVideoService.getVideo(lessonName)) {
//            response.setContentType("video/mp4"); // Set Content-Type phù hợp với định dạng video
//            response.setHeader("Content-Disposition", "inline; filename=\"" + lessonName + "\"");
//            org.apache.commons.io.IOUtils.copy(videoStream, response.getOutputStream());
//            response.flushBuffer();
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//    }
//
//}
