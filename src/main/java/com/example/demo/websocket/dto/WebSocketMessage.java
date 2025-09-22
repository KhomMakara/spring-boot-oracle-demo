package com.example.demo.websocket.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WebSocket Message DTO
 * Represents messages sent through WebSocket connections
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {
    
    private String type;
    private String content;
    private String sender;
    private LocalDateTime timestamp;
    private Object data;
    
    public static WebSocketMessage createMessage(String type, String content, String sender) {
        return WebSocketMessage.builder()
                .type(type)
                .content(content)
                .sender(sender)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    public static WebSocketMessage createDataMessage(String type, Object data, String sender) {
        return WebSocketMessage.builder()
                .type(type)
                .data(data)
                .sender(sender)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
