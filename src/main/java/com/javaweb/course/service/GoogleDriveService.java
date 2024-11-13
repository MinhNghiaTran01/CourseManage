package com.javaweb.course.service;

import org.springframework.http.ResponseEntity;

import java.io.IOException;


public interface GoogleDriveService {
    ResponseEntity<?> createNewFolder(String folderName, String parentFolderId);

    ResponseEntity<?> getWebViewLink(String fileId);

    ResponseEntity<?> addPermission(String fileId, String email, String role);

    String getAccessToken() throws IOException;
}
