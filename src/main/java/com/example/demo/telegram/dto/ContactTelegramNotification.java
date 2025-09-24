package com.example.demo.telegram.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contact Telegram Notification DTO
 * Specific notification format for contact-related events
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactTelegramNotification {
    
    private String contactId;
    private String contactPerson;
    private String action; // CREATE, UPDATE, DELETE
    private String address;
    private String telephone;
    private String email;
    private String province;
    private String city;
    private String createdBy;
    private LocalDateTime timestamp;
    
    public String toFormattedMessage() {
        String emoji = getActionEmoji();
        String actionText = getActionText();
        String time = timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        
        return String.format("""
            %s <b>%s Contact</b>
            
            👤 <b>Name:</b> %s
            📧 <b>Email:</b> %s
            📞 <b>Phone:</b> %s
            🏠 <b>Address:</b> %s
            🏙️ <b>City:</b> %s
            🌍 <b>Province:</b> %s
            👨‍💼 <b>Created by:</b> %s
            ⏰ <b>Time:</b> %s
            
            <i>Contact ID: %s</i>
            """, 
            emoji, actionText, contactPerson, email, telephone, 
            address, city, province, createdBy, time, contactId
        );
    }
    
    private String getActionEmoji() {
        return switch (action) {
            case "CREATE" -> "✅";
            case "UPDATE" -> "🔄";
            case "DELETE" -> "❌";
            default -> "📝";
        };
    }
    
    private String getActionText() {
        return switch (action) {
            case "CREATE" -> "New Contact Added";
            case "UPDATE" -> "Contact Updated";
            case "DELETE" -> "Contact Deleted";
            default -> "Contact Activity";
        };
    }
    
    public static ContactTelegramNotification createContactAdded(
            String contactId, String contactPerson, String address, 
            String telephone, String email, String province, 
            String city, String createdBy) {
        return ContactTelegramNotification.builder()
                .contactId(contactId)
                .contactPerson(contactPerson)
                .action("CREATE")
                .address(address)
                .telephone(telephone)
                .email(email)
                .province(province)
                .city(city)
                .createdBy(createdBy)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
