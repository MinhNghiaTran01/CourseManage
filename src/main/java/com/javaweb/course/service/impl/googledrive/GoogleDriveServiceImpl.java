package com.javaweb.course.service.impl.googledrive;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.google.auth.oauth2.GoogleCredentials;
import com.javaweb.course.entity.Course;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleDriveServiceImpl implements GoogleDriveService {

    private static final String DEFAULT_PARENT_FOLDER_ID = "1Xm4naoVUgZivoaY0WggYh0Zz7hfMnvNm";

    @Autowired
    private Drive driveService;

    @Autowired
    private CourseRepository courseRepository;

    public ResponseEntity<?> createNewFolder(String folderName, String parentFolderId) {

        Course course = courseRepository.findByCode(folderName);
        if (course != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course already exists");
        }
        try {
            
            if (parentFolderId == null || parentFolderId.isEmpty()) {
                parentFolderId = DEFAULT_PARENT_FOLDER_ID;
            }
            
            
            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName(folderName);
            fileMetadata.setMimeType("application/vnd.google-apps.folder");

            
            if (parentFolderId != null && !parentFolderId.isEmpty()) {
                fileMetadata.setParents(List.of(parentFolderId));
            }

            
            com.google.api.services.drive.model.File folder = driveService.files().create(fileMetadata)
                    .setFields("id") 
                    .execute();
            return ResponseEntity.status(HttpStatus.CREATED).body(folder.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Couldn't create folder");
        }
    }

    @Override
    public ResponseEntity<?> getWebViewLink(String fileId) {
        try {
            
            String webViewLink = driveService.files()
                    .get(fileId)
                    .setFields("webViewLink") 
                    .execute()
                    .getWebViewLink();

            return webViewLink != null ? ResponseEntity.status(HttpStatus.OK).body(webViewLink) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't find web view link");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to get webViewLink: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> addPermission(String fileId, String email, String role) {
        try {
            Permission permission = new Permission();
            permission.setType("user");
            permission.setRole(role); 
            permission.setEmailAddress(email);

            
            driveService.permissions().create(fileId, permission)
                    .setSendNotificationEmail(true) 
                    .execute();
            return ResponseEntity.status(HttpStatus.CREATED).body("Permission added successfully to file ID: " + fileId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed to add permission: " + e.getMessage());
        }
    }

    @Override
    public String getAccessToken() throws IOException {
        String serviceAccountKeyPath = "classpath:static/service_account.json";
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(getClass().getClassLoader().getResourceAsStream("static/service_account.json"))
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/drive.file"));
        credentials.refreshIfExpired();
        return credentials.getAccessToken().getTokenValue();
    }
}
