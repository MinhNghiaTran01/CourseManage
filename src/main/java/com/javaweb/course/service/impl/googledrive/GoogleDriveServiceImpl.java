package com.javaweb.course.service.impl.googledrive;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.auth.oauth2.GoogleCredentials;
import com.javaweb.course.entity.Course;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.service.GoogleDriveService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
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


            File folder = driveService.files().create(fileMetadata)
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
    public ResponseEntity<?> addPermission(String fileId, String emailRegisterCourse, String role) {
        try {
            Permission permission = new Permission();
            permission.setType("user");
            permission.setRole(role);
            permission.setEmailAddress(emailRegisterCourse);


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
    public ResponseEntity<?> updateFolderCourseName(String folderId, String newFolderCourseName) {
        try {
            if (folderId == null || folderId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid folderId");
            }

            File fileMetadata = new File();
            fileMetadata.setName(newFolderCourseName);

            File updatedFolder = driveService.files().update(folderId, fileMetadata)
                    .setFields("id, name")
                    .execute();
            log.debug("======updateFolderCourseName updatedFolder: " + updatedFolder);
            return ResponseEntity.status(HttpStatus.OK).body("Folder renamed to: " + updatedFolder.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Couldn't update folder name");
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

    @Override
    public ResponseEntity<?> deleteFolderById(String folderId) {
        try {
            if (folderId == null || folderId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid folderId");
            }

            driveService.files().delete(folderId).execute();

            return ResponseEntity.status(HttpStatus.OK).body("Folder deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Couldn't delete folder");
        }
    }

    @Override
    public boolean deleteFile(String fileId) {
        try {
            driveService.files().delete(fileId).execute();
            return true;
        } catch (IOException e) {
            log.debug("Error while deleting file: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateFolderLessonCategoryName(String folderId, String newFolderLessonCategoryName) {
        try {
            if (folderId == null || folderId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid folderId");
            }

            File fileMetadata = new File();
            fileMetadata.setName(newFolderLessonCategoryName);

            File updatedFolder = driveService.files().update(folderId, fileMetadata)
                    .setFields("id, name")
                    .execute();
            log.debug("======updateFolderLessonCategoryName updatedFolder: " + updatedFolder);
            return ResponseEntity.status(HttpStatus.OK).body("Folder renamed to: " + updatedFolder.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Couldn't update folder name");
        }
    }

    @Override
    public ResponseEntity<?> updateFileLessonName(String fileId, String newFileLessonName) {
        try {
            if (fileId == null || fileId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid folderId");
            }

            File fileMetadata = new File();
            fileMetadata.setName(newFileLessonName);

            File updateFile = driveService.files().update(fileId, fileMetadata)
                    .setFields("id, name")
                    .execute();
            log.debug("======updateFileLessonName updatedFile: " + updateFile);
            return ResponseEntity.status(HttpStatus.OK).body("Folder renamed to: " + updateFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Couldn't update folder name");
        }
    }
}
