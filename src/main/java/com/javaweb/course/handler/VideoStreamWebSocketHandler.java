//package com.javaweb.course.handler;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.BinaryMessage;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.BinaryWebSocketHandler;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.ProtocolException;
//import java.net.URL;
//import java.util.Collections;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.auth.oauth2.ServiceAccountCredentials;
//
//@Component
//public class VideoStreamWebSocketHandler extends BinaryWebSocketHandler {
//
//    private static final String GOOGLE_DRIVE_API_URL = "https://www.googleapis.com/drive/v3/files/";
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
//        // Parse fileId from frontend message
//        String fileId = message.getPayload();
//
//        // Authenticate using Service Account
//        InputStream serviceAccountStream = getClass().getClassLoader().getResourceAsStream("static/service_account.json");
//        GoogleCredentials credentials = null;
//        try {
//            credentials = ServiceAccountCredentials.fromStream(serviceAccountStream)
//                    .createScoped(Collections.singletonList("https://www.googleapis.com/auth/drive.readonly"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            credentials.refreshIfExpired();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        String accessToken = credentials.getAccessToken().getTokenValue();
//
//        // Connect to Google Drive file
//        String videoUrl = GOOGLE_DRIVE_API_URL + fileId + "?alt=media";
//        HttpURLConnection connection = null;
//        try {
//            connection = (HttpURLConnection) new URL(videoUrl).openConnection();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
//        try {
//            connection.setRequestMethod("GET");
//        } catch (ProtocolException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Stream video data as binary to WebSocket client
//        try (InputStream inputStream = connection.getInputStream()) {
//            byte[] buffer = new byte[65536];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                // Chuyển đổi buffer thành ByteBuffer và gửi qua WebSocket
//                session.sendMessage(new BinaryMessage(java.nio.ByteBuffer.wrap(buffer, 0, bytesRead)));
//                System.out.println("Sent bytes: " + bytesRead); // Log số bytes gửi
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                session.close();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//}
