package com.example.demo.mapper.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dao.TemplateDao;
import com.example.demo.mapper.ContactInfoMapper;
import com.example.demo.model.ContactModel;

public class ContactInfoMapperImpl implements ContactInfoMapper {
	
    @Autowired
	private TemplateDao<ContactModel> contactModelDao;

    @Override
	public List<ContactModel> retrieveListContactInfo()throws Exception{
		try {
            return  contactModelDao.retrieveList("retrieveListContactInfo", new ContactModel());
        } catch (Exception e) {
            throw new Exception("Error retrieving Contact Info", e);
        }
	}

    @Override
    public boolean registerContactInfo(ContactModel param) throws Exception {
        try {
            return  contactModelDao.register("registerContactInfo", param);
        } catch (Exception e) {
            throw new Exception("Error retrieving Contact Info", e);
        }
    }

}
