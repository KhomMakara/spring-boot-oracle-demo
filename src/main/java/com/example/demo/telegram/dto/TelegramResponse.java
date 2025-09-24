package com.example.demo.telegram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Telegram API Response DTO
 * Represents the response from Telegram Bot API
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramResponse {
    
    private boolean ok;
    private String description;
    private TelegramMessage result;
    private int errorCode;
    
    public boolean isSuccess() {
        return ok;
    }
}
