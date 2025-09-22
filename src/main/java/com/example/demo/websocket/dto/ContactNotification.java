package com.example.demo.websocket.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contact Notification DTO
 * Specific notification for contact-related events
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactNotification {
    
    private String contactId;
    private String contactPerson;
    private String action; // CREATE, UPDATE, DELETE
    private String message;
    private LocalDateTime timestamp;
    private String createdBy;
    
    public static ContactNotification createContactAdded(String contactId, String contactPerson, String createdBy) {
        return ContactNotification.builder()
                .contactId(contactId)
                .contactPerson(contactPerson)
                .action("CREATE")
                .message("New contact added: " + contactPerson)
                .timestamp(LocalDateTime.now())
                .createdBy(createdBy)
                .build();
    }
    
    public static ContactNotification createContactUpdated(String contactId, String contactPerson, String updatedBy) {
        return ContactNotification.builder()
                .contactId(contactId)
                .contactPerson(contactPerson)
                .action("UPDATE")
                .message("Contact updated: " + contactPerson)
                .timestamp(LocalDateTime.now())
                .createdBy(updatedBy)
                .build();
    }
    
    public static ContactNotification createContactDeleted(String contactId, String contactPerson, String deletedBy) {
        return ContactNotification.builder()
                .contactId(contactId)
                .contactPerson(contactPerson)
                .action("DELETE")
                .message("Contact deleted: " + contactPerson)
                .timestamp(LocalDateTime.now())
                .createdBy(deletedBy)
                .build();
    }
}
