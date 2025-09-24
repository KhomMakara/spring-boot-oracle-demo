package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.MErrException;
import com.example.demo.exception.MRegisterErrException;
import com.example.demo.model.ContactModel;
import com.example.demo.model.ResultMessageModel;
import com.example.demo.service.ContactServiceInfo;
import com.example.demo.telegram.dto.ContactTelegramNotification;
import com.example.demo.telegram.service.TelegramService;
import com.example.demo.util.DateUtil;
import com.example.demo.websocket.dto.ContactNotification;
import com.example.demo.websocket.service.WebSocketService;

@RestController
@RequestMapping("/api")
public class ContactController {
    @Autowired
    private ContactServiceInfo contactServiceInfo;
    
    @Autowired
    private WebSocketService webSocketService;
    
    @Autowired
    private TelegramService telegramService;
    
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    @GetMapping("/contact-list")
	public List<ContactModel> retrieveListHistory() throws Exception {
	    return contactServiceInfo.retrieveListContactInfo();
	}
    @PostMapping("/contact-register")
    public ResultMessageModel registerContactInfo(@RequestBody ContactModel contactItem ) {
    	try {

			contactItem.setCreatedBy("Developer");
			contactItem.setCreatedDate(DateUtil.getCurrentDate());
			contactItem.setUpdatedDate(DateUtil.getCurrentDate());
			
			ResultMessageModel response = new ResultMessageModel();
			if (contactServiceInfo.registerContactInfo(contactItem)) {
				response.setResult("true");
				response.setMessage("register Contact succefull");
				
				// Send WebSocket notification
				ContactNotification notification = ContactNotification.createContactAdded(
						contactItem.getContactID(),
						contactItem.getContactPerson(),
						contactItem.getCreatedBy()
				);
				webSocketService.sendContactNotification(notification);
				
				// Send Telegram notification
				ContactTelegramNotification telegramNotification = ContactTelegramNotification.createContactAdded(
						contactItem.getContactID(),
						contactItem.getContactPerson(),
						contactItem.getAddress(),
						contactItem.getTelephone(),
						contactItem.getEmail(),
						contactItem.getProvince(),
						contactItem.getCity(),
						contactItem.getCreatedBy()
				);
				telegramService.sendContactNotification(telegramNotification);
				
			} else {
				response.setResult("false");
				response.setMessage("register Contact fail");
			}

    		return response;
    		 
    	} catch (MRegisterErrException e) {
			log.error("registerContactInfo >> Error", e);
			throw e;
		} catch (Exception e) {
			log.error("registerContactInfo >> Error", e);
			throw new MErrException("00002", "There has problem while register Contact Info ", e);
		}
		
    }

}
