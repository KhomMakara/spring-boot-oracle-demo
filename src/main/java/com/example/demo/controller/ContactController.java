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
import com.example.demo.util.DateUtil;

@RestController
@RequestMapping("/api")
public class ContactController {
    @Autowired
    private ContactServiceInfo contactServiceInfo;
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
