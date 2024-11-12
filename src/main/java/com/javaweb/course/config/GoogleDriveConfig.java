package com.javaweb.course.config;

import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Configuration
public class GoogleDriveConfig {

    private final ResourceLoader resourceLoader;

    public GoogleDriveConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public Drive googleDriveService() throws IOException {
        // Load Service Account JSON from static folder
        Resource resource = resourceLoader.getResource("classpath:static/service_account.json");
        InputStream serviceAccountStream = resource.getInputStream();

        // Load credentials and set scope
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(serviceAccountStream)
                .createScoped(Collections.singletonList(DriveScopes.DRIVE));

        // Return Google Drive client
        return new Drive.Builder(
                new com.google.api.client.http.javanet.NetHttpTransport(),
                new GsonFactory(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName("Spring Boot Google Drive Integration").build();
    }
}
