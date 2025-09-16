package com.example.demo.service.impl;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.MErrException;
import com.example.demo.exception.MRegisterErrException;
import com.example.demo.mapper.ContactInfoMapper;
import com.example.demo.model.ContactModel;
import com.example.demo.service.ContactServiceInfo;
import com.example.demo.util.DateUtil;
import com.example.demo.util.StringValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContactServiceInfoImpl  implements ContactServiceInfo{
    @Autowired
    private ContactInfoMapper contactInfoMapper;
    private static final Logger logg = LoggerFactory.getLogger(ContactServiceInfoImpl.class);

    @Override
    public List<ContactModel> retrieveListContactInfo() throws Exception {
       return contactInfoMapper.retrieveListContactInfo();
    }

    @Override
    public boolean  registerContactInfo(ContactModel categoryItem) throws Exception {
        try {
    		validationField(categoryItem);
    		
            String contactId = generateContactID();
            categoryItem.setContactID(contactId);
    		categoryItem.setCreatedBy("Developer");
			categoryItem.setCreatedDate(DateUtil.getCurrentDate());
			categoryItem.setUpdatedDate(DateUtil.getCurrentDate());
            contactInfoMapper.registerContactInfo(categoryItem);
    		
    		return true;
		} catch (MRegisterErrException e) {
			logg.error("Register Category Error",e);
			return false;
		}catch (Exception e) {
			throw new MErrException("There has problem while register Category",e);
		}

	}

    public String generateContactID() {
        Random random = new Random();
        return String.valueOf(random.nextInt(900000) + 100000);
    }

    private void validationField(ContactModel contactModel) {
        if (StringValidateUtil.isEmpty(contactModel.getAddress())) {
            throw new MErrException("Address is required.");
        }
        if (StringValidateUtil.isEmpty(contactModel.getDistrict())) {
            throw new MErrException("District is required.");
        }
        if (StringValidateUtil.isEmpty(contactModel.getAmphur())) {
            throw new MErrException("Amphur is required.");
        }
        if (StringValidateUtil.isEmpty(contactModel.getProvince())) {
            throw new MErrException("Province is required.");
        }
        if (StringValidateUtil.isEmpty(contactModel.getPostalCode())) {
            throw new MErrException("Postal Code is required.");
        }
        if (StringValidateUtil.isEmpty(contactModel.getTelephone())) {
            throw new MErrException("Telephone is required.");
        }
        if (StringValidateUtil.isEmpty(contactModel.getFax())) {
            throw new MErrException("Fax is required.");
        }
        if (StringValidateUtil.isEmpty(contactModel.getCity())) {
            throw new MErrException("City is required.");
        }
        if (StringValidateUtil.isEmpty(contactModel.getState())) {
            throw new MErrException("State is required.");
        }
        if (StringValidateUtil.isEmpty(contactModel.getCountry())) {
            throw new MErrException("Country is required.");
        }
        if (StringValidateUtil.isEmpty(contactModel.getContactPerson())) {
            throw new MErrException("Contact Person is required.");
        }
        if (StringValidateUtil.isEmpty(contactModel.getEmail())) {
            throw new MErrException("Email is required.");
        }
    }
    
}
