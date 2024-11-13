package com.javaweb.course.controller.web.googledrive;


import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.javaweb.course.entity.Course;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.service.GoogleDriveService;
import com.javaweb.course.service.impl.googledrive.GoogleDriveServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user/api/google-drive")
public class GoogleDriveController {

    @Autowired
    private GoogleDriveService googleDriveService;

    @GetMapping("/web-view-link")
    public ResponseEntity<?> getWebViewLink(@RequestParam("fileId") String fileId) {
        return googleDriveService.getWebViewLink(fileId);
    }

    @GetMapping("/add-permission")
    public ResponseEntity<?> addPermission(@RequestParam String fileId,
                                @RequestParam String email,
                                @RequestParam String role) {
        return googleDriveService.addPermission(fileId,email,role);
    }

    @PostMapping("/create-folder-course")
    public ResponseEntity<?> createFolderCourse(@RequestParam String folderName, @RequestParam(required = false) String parentFolderId) {
        return googleDriveService.createNewFolder(folderName, parentFolderId);
    }

    @PostMapping("/create-folder-category-course")
    public ResponseEntity<?> createFolderCategoryCourse(@RequestParam String folderName, @RequestParam(required = false) String parentFolderId) {
        return googleDriveService.createNewFolder(folderName, parentFolderId);
    }

    @GetMapping("/get-token")
    public String getAccessToken() {
        try {
            return googleDriveService.getAccessToken();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get access token", e);
        }
    }
}
