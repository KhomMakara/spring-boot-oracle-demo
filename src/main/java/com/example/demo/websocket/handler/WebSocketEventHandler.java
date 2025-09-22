package com.example.demo.websocket.handler;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * WebSocket Event Handler
 * Handles WebSocket connection and disconnection events
 */
@Slf4j
@Component
public class WebSocketEventHandler {
    
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        
        log.info("WebSocket connection established - Session ID: {}", sessionId);
    }
    
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        
        log.info("WebSocket connection closed - Session ID: {}", sessionId);
    }
}
