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

//    @GetMapping("/add-permission")
//    public ResponseEntity<?> addPermission(@RequestParam String fileId,
//                                @RequestParam String email,
//                                @RequestParam String role) {
//        return googleDriveService.addPermission(fileId,)
//        try {
//            addPermissionToDriveFile(fileId, email, role);
//            return "Permission added successfully to file ID: " + fileId;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Failed to add permission: " + e.getMessage();
//        }
//    }

//    private void addPermissionToDriveFile(String fileId, String email, String role) throws IOException {
//        Permission permission = new Permission();
//        permission.setType("user");
//        permission.setRole(role); // Role: reader, writer, or owner
//        permission.setEmailAddress(email);
//
//        // Thực hiện gán quyền với Drive API
//        driveService.permissions().create(fileId, permission)
//                .setSendNotificationEmail(true) // Notify the user by email
//                .execute();
//    }


    @PostMapping("/create-folder")
    public ResponseEntity<?> createFolder(@RequestParam String folderName, @RequestParam(required = false) String parentFolderId) {
        return googleDriveService.createNewFolder(folderName, parentFolderId);
    }
}
