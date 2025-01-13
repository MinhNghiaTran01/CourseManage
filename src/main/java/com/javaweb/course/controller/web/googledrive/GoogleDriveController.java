package com.javaweb.course.controller.web.googledrive;


import com.javaweb.course.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


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
    public ResponseEntity<?> addPermission(@RequestParam String folderId,
                                           @RequestParam String role,@RequestParam String emailRegisterCourse) {
        return googleDriveService.addPermission(folderId, emailRegisterCourse, role);
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
