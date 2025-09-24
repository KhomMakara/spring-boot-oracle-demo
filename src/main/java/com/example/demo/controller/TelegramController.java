package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.telegram.dto.ContactTelegramNotification;
import com.example.demo.telegram.service.TelegramService;

/**
 * Telegram Controller
 * Provides endpoints for testing and managing Telegram notifications
 */
@RestController
@RequestMapping("/api/telegram")
public class TelegramController {
    
    @Autowired
    private TelegramService telegramService;
    
    private static final Logger log = LoggerFactory.getLogger(TelegramController.class);
    
    /**
     * Test Telegram connection
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testConnection() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean isAvailable = telegramService.isServiceAvailable();
            boolean testResult = false;
            
            if (isAvailable) {
                testResult = telegramService.testConnection();
            }
            
            response.put("available", isAvailable);
            response.put("testResult", testResult);
            response.put("message", testResult ? "Telegram connection successful" : "Telegram connection failed");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error testing Telegram connection", e);
            response.put("available", false);
            response.put("testResult", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * Send a test message to Telegram
     */
    @PostMapping("/send-test")
    public ResponseEntity<Map<String, Object>> sendTestMessage(@RequestParam String message) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = telegramService.sendTextMessage(message);
            
            response.put("success", success);
            response.put("message", success ? "Message sent successfully" : "Failed to send message");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error sending test message", e);
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * Send a test contact notification
     */
    @PostMapping("/send-contact-test")
    public ResponseEntity<Map<String, Object>> sendTestContactNotification() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            ContactTelegramNotification testNotification = ContactTelegramNotification.createContactAdded(
                    "TEST-001",
                    "John Doe",
                    "123 Main Street, Phnom Penh",
                    "+855123456789",
                    "john.doe@example.com",
                    "Phnom Penh",
                    "Phnom Penh",
                    "Test User"
            );
            
            boolean success = telegramService.sendContactNotification(testNotification);
            
            response.put("success", success);
            response.put("message", success ? "Test contact notification sent successfully" : "Failed to send test notification");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error sending test contact notification", e);
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * Get Telegram service status
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean isAvailable = telegramService.isServiceAvailable();
            
            response.put("enabled", isAvailable);
            response.put("message", isAvailable ? "Telegram service is configured and ready" : "Telegram service is not configured");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error getting Telegram status", e);
            response.put("enabled", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * Get Telegram configuration details (for debugging)
     */
    @GetMapping("/config")
    public ResponseEntity<Map<String, Object>> getConfig() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            response.put("available", telegramService.isServiceAvailable());
            response.put("message", "Configuration details logged in server logs");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error getting Telegram config", e);
            response.put("available", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
