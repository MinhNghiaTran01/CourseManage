package com.javaweb.course.service;

import org.springframework.http.ResponseEntity;

import java.io.IOException;


public interface GoogleDriveService {
    ResponseEntity<?> createNewFolder(String folderName, String parentFolderId);

    ResponseEntity<?> getWebViewLink(String fileId);

    ResponseEntity<?> addPermission(String fileId, String email, String role);

    ResponseEntity<?> updateFolderCourseName(String folderId, String newFolderCourseName);

    ResponseEntity<?> deleteFolderById(String folderId);

    String getAccessToken() throws IOException;

    boolean deleteFile(String fileId);

    ResponseEntity<?> updateFolderLessonCategoryName(String folderId, String newFolderLessonCategoryName);
}
