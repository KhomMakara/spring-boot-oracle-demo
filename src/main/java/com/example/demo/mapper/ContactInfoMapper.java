package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.ContactModel;

@Mapper
public interface ContactInfoMapper {
    List<ContactModel> retrieveListContactInfo() throws Exception;
    boolean registerContactInfo(ContactModel param) throws Exception;
}
