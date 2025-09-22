package com.example.demo.websocket.service.impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.websocket.dto.ContactNotification;
import com.example.demo.websocket.dto.WebSocketMessage;
import com.example.demo.websocket.service.WebSocketService;

import lombok.extern.slf4j.Slf4j;

/**
 * WebSocket Service Implementation
 * Handles real-time messaging through WebSocket connections
 */
@Slf4j
@Service
public class WebSocketServiceImpl implements WebSocketService {
    
    private final SimpMessagingTemplate messagingTemplate;
    
    public WebSocketServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    
    @Override
    public void sendMessageToAll(WebSocketMessage message) {
        try {
            log.info("Sending message to all clients: {}", message.getType());
            messagingTemplate.convertAndSend("/topic/messages", message);
        } catch (Exception e) {
            log.error("Error sending message to all clients", e);
        }
    }
    
    @Override
    public void sendMessageToUser(String username, WebSocketMessage message) {
        try {
            log.info("Sending message to user {}: {}", username, message.getType());
            messagingTemplate.convertAndSendToUser(username, "/queue/messages", message);
        } catch (Exception e) {
            log.error("Error sending message to user: {}", username, e);
        }
    }
    
    @Override
    public void sendContactNotification(ContactNotification notification) {
        try {
            log.info("Sending contact notification: {} - {}", 
                    notification.getAction(), notification.getContactPerson());
            
            WebSocketMessage message = WebSocketMessage.createDataMessage(
                    "CONTACT_NOTIFICATION", 
                    notification, 
                    "SYSTEM"
            );
            
            messagingTemplate.convertAndSend("/topic/notifications", message);
        } catch (Exception e) {
            log.error("Error sending contact notification", e);
        }
    }
    
    @Override
    public void sendSystemMessage(String content, String type) {
        try {
            log.info("Sending system message: {} - {}", type, content);
            
            WebSocketMessage message = WebSocketMessage.createMessage(
                    type, 
                    content, 
                    "SYSTEM"
            );
            
            messagingTemplate.convertAndSend("/topic/system", message);
        } catch (Exception e) {
            log.error("Error sending system message", e);
        }
    }
    
    @Override
    public int getConnectedClientsCount() {
        // This is a simplified implementation
        // In a real application, you would track active sessions
        return 0;
    }
}
