package com.example.demo.telegram.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.config.TelegramConfig;
import com.example.demo.telegram.dto.ContactTelegramNotification;
import com.example.demo.telegram.dto.TelegramMessage;
import com.example.demo.telegram.dto.TelegramResponse;
import com.example.demo.telegram.service.TelegramService;

import lombok.extern.slf4j.Slf4j;

/**
 * Telegram Service Implementation
 * Handles communication with Telegram Bot API
 */
@Slf4j
@Service
public class TelegramServiceImpl implements TelegramService {
    
    private final TelegramConfig telegramConfig;
    private final RestTemplate restTemplate;
    
    private static final String TELEGRAM_API_URL = "https://api.telegram.org/bot{token}/sendMessage";
    
    public TelegramServiceImpl(TelegramConfig telegramConfig, RestTemplate restTemplate) {
        this.telegramConfig = telegramConfig;
        this.restTemplate = restTemplate;
        
        log.info("TelegramServiceImpl initialized with:");
        log.info("Token: {}", telegramConfig.getToken() != null ? "***" + telegramConfig.getToken().substring(telegramConfig.getToken().length() - 4) : "NULL");
        log.info("Chat ID: {}", telegramConfig.getChatId());
        log.info("Enabled: {}", telegramConfig.isEnabled());
        log.info("Username: {}", telegramConfig.getUsername());
    }
    
    @Override
    public boolean sendMessage(TelegramMessage message) {
        if (!isServiceAvailable()) {
            log.warn("Telegram service is not available or not configured");
            return false;
        }
        
        try {
            String url = TELEGRAM_API_URL.replace("{token}", telegramConfig.getToken());
            
            log.info("Sending Telegram message to chat: {}", message.getChatId());
            log.info("Message content: {}", message.getText());
            log.info("Full message object: {}", message);
            
            // Validate chat ID
            if (message.getChatId() == null || message.getChatId().trim().isEmpty()) {
                log.error("Chat ID is null or empty!");
                return false;
            }
            
            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            // Create request entity
            HttpEntity<TelegramMessage> requestEntity = new HttpEntity<>(message, headers);
            
            // Send request
            TelegramResponse response = restTemplate.postForObject(url, requestEntity, TelegramResponse.class);
            
            if (response != null && response.isSuccess()) {
                log.info("Telegram message sent successfully");
                return true;
            } else {
                log.error("Failed to send Telegram message: {}", 
                         response != null ? response.getDescription() : "Unknown error");
                return false;
            }
            
        } catch (Exception e) {
            log.error("Error sending Telegram message", e);
            return false;
        }
    }
    
    @Override
    public boolean sendTextMessage(String text) {
        log.info("Creating message with chat ID: {}", telegramConfig.getChatId());
        TelegramMessage message = TelegramMessage.createTextMessage(
                telegramConfig.getChatId(), 
                text
        );
        log.info("Message created with chat ID: {}", message.getChatId());
        return sendMessage(message);
    }
    
    @Override
    public boolean sendContactNotification(ContactTelegramNotification notification) {
        if (!isServiceAvailable()) {
            log.warn("Telegram service is not available for contact notification");
            return false;
        }
        
        try {
            String formattedMessage = notification.toFormattedMessage();
            TelegramMessage message = TelegramMessage.createTextMessage(
                    telegramConfig.getChatId(), 
                    formattedMessage
            );
            
            boolean success = sendMessage(message);
            if (success) {
                log.info("Contact notification sent to Telegram: {} - {}", 
                        notification.getAction(), notification.getContactPerson());
            }
            return success;
            
        } catch (Exception e) {
            log.error("Error sending contact notification to Telegram", e);
            return false;
        }
    }
    
    @Override
    public boolean sendSystemMessage(String message) {
        String formattedMessage = String.format("""
            🤖 <b>System Notification</b>
            
            %s
            
            <i>Spring Oracle Application</i>
            """, message);
        
        return sendTextMessage(formattedMessage);
    }
    
    @Override
    public boolean isServiceAvailable() {
        return true;
    }
    
    @Override
    public boolean testConnection() {
        if (!isServiceAvailable()) {
            return false;
        }
        
        try {
            String testMessage = "🧪 <b>Connection Test</b>\n\nTelegram service is working correctly!";
            return sendTextMessage(testMessage);
        } catch (Exception e) {
            log.error("Telegram connection test failed", e);
            return false;
        }
    }
}
