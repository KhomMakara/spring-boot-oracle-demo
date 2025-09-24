package com.example.demo.telegram.service;

import com.example.demo.telegram.dto.ContactTelegramNotification;
import com.example.demo.telegram.dto.TelegramMessage;

/**
 * Telegram Service Interface
 * Defines contract for Telegram Bot operations
 */
public interface TelegramService {
    
    /**
     * Send a text message to Telegram
     * @param message Telegram message to send
     * @return true if message sent successfully, false otherwise
     */
    boolean sendMessage(TelegramMessage message);
    
    /**
     * Send a simple text message
     * @param text Message text
     * @return true if message sent successfully, false otherwise
     */
    boolean sendTextMessage(String text);
    
    /**
     * Send contact notification to Telegram
     * @param notification Contact notification
     * @return true if message sent successfully, false otherwise
     */
    boolean sendContactNotification(ContactTelegramNotification notification);
    
    /**
     * Send system message to Telegram
     * @param message System message
     * @return true if message sent successfully, false otherwise
     */
    boolean sendSystemMessage(String message);
    
    /**
     * Check if Telegram service is enabled and configured
     * @return true if service is available, false otherwise
     */
    boolean isServiceAvailable();
    
    /**
     * Test Telegram connection
     * @return true if connection successful, false otherwise
     */
    boolean testConnection();
}
