package com.javaweb.course.service.impl.googledrive;

import com.google.api.services.drive.Drive;
import com.javaweb.course.entity.Course;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
            // Sử dụng thư mục mặc định nếu không có parentFolderId
            if (parentFolderId == null || parentFolderId.isEmpty()) {
                parentFolderId = DEFAULT_PARENT_FOLDER_ID;
            }
            // Tạo thư mục
//            String folderId = createNewFolder(folderName, parentFolderId);
//            return ResponseEntity.status(HttpStatus.CREATED).body(folderId);
            // Metadata của thư mục mới
            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName(folderName);
            fileMetadata.setMimeType("application/vnd.google-apps.folder");

            // Nếu có parentFolderId, gán vào metadata
            if (parentFolderId != null && !parentFolderId.isEmpty()) {
                fileMetadata.setParents(List.of(parentFolderId));
            }

            // Gọi API để tạo thư mục
            com.google.api.services.drive.model.File folder = driveService.files().create(fileMetadata)
                    .setFields("id") // Lấy ID của thư mục được tạo
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
            // Lấy metadata file từ Google Drive API
            String webViewLink = driveService.files()
                    .get(fileId)
                    .setFields("webViewLink") // Chỉ lấy trường webViewLink
                    .execute()
                    .getWebViewLink();

            return webViewLink != null ? ResponseEntity.status(HttpStatus.OK).body(webViewLink) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't find web view link");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to get webViewLink: " + e.getMessage());
        }
    }
}
