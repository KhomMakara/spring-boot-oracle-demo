package com.example.demo.service;

import java.util.List;

import com.example.demo.model.ContactModel;


public interface ContactServiceInfo {
    List<ContactModel> retrieveListContactInfo() throws Exception;
    boolean registerContactInfo(ContactModel param) throws Exception;

}
