package com.example.demo.websocket.service;

import com.example.demo.websocket.dto.ContactNotification;
import com.example.demo.websocket.dto.WebSocketMessage;

/**
 * WebSocket Service Interface
 * Defines contract for WebSocket operations
 */
public interface WebSocketService {
    
    /**
     * Send message to all connected clients
     * @param message WebSocket message to send
     */
    void sendMessageToAll(WebSocketMessage message);
    
    /**
     * Send message to specific user
     * @param username Target username
     * @param message WebSocket message to send
     */
    void sendMessageToUser(String username, WebSocketMessage message);
    
    /**
     * Send contact notification to all clients
     * @param notification Contact notification
     */
    void sendContactNotification(ContactNotification notification);
    
    /**
     * Send system message to all clients
     * @param content Message content
     * @param type Message type
     */
    void sendSystemMessage(String content, String type);
    
    /**
     * Get number of connected clients
     * @return Number of active connections
     */
    int getConnectedClientsCount();
}
