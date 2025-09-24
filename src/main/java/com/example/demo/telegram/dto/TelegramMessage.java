package com.example.demo.telegram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Telegram Message DTO
 * Represents a message to be sent via Telegram Bot API
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramMessage {
    
    @JsonProperty("chat_id")
    private String chatId;
    
    private String text;
    
    @JsonProperty("parse_mode")
    private String parseMode;
    
    @JsonProperty("disable_web_page_preview")
    private boolean disableWebPagePreview;
    
    @JsonProperty("disable_notification")
    private boolean disableNotification;
    
    public static TelegramMessage createTextMessage(String chatId, String text) {
        return TelegramMessage.builder()
                .chatId(chatId)
                .text(text)
                .parseMode("HTML")
                .disableWebPagePreview(true)
                .disableNotification(false)
                .build();
    }
    
    public static TelegramMessage createMarkdownMessage(String chatId, String text) {
        return TelegramMessage.builder()
                .chatId(chatId)
                .text(text)
                .parseMode("Markdown")
                .disableWebPagePreview(true)
                .disableNotification(false)
                .build();
    }
}
