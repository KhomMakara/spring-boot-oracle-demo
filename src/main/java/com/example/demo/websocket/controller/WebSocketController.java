package com.example.demo.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.demo.websocket.dto.WebSocketMessage;
import com.example.demo.websocket.service.WebSocketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * WebSocket Controller
 * Handles incoming WebSocket messages and broadcasts responses
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketController {
    
    private final WebSocketService webSocketService;
    
    /**
     * Handle incoming messages from clients
     * @param message Incoming WebSocket message
     * @return Response message
     */
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public WebSocketMessage handleMessage(@Payload WebSocketMessage message) {
        log.info("Received message from client: {} - {}", message.getSender(), message.getContent());
        
        // Echo the message back with timestamp
        return WebSocketMessage.builder()
                .type("CHAT_RESPONSE")
                .content("Echo: " + message.getContent())
                .sender("SERVER")
                .timestamp(java.time.LocalDateTime.now())
                .build();
    }
    
    /**
     * Handle system commands
     * @param command System command
     * @return Response message
     */
    @MessageMapping("/system")
    @SendTo("/topic/system")
    public WebSocketMessage handleSystemCommand(@Payload String command) {
        log.info("Received system command: {}", command);
        
        String response = switch (command.toLowerCase()) {
            case "ping" -> "Pong! Server is alive";
            case "status" -> "Server is running normally";
            case "time" -> "Current time: " + java.time.LocalDateTime.now();
            default -> "Unknown command: " + command;
        };
        
        return WebSocketMessage.createMessage("SYSTEM_RESPONSE", response, "SERVER");
    }
}
