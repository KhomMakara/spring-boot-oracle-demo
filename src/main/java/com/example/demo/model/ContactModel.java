package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactModel {
    private String contactID;
    private String address;
    private String district;
    private String amphur;
    private String province;
    private String postalCode;
    private String telephone;
    private String fax;
    private String city;
    private String state;
    private String country;
    private String contactPerson;
    private String email;
    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;
    
    public ContactModel() {
        
    }

    public ContactModel(String contactID, String address, String district, 
                        String amphur, String province, String postalCode,
                        String telephone, String fax, String city,
                        String state, String country, String contactPerson,
                        String email, String createdBy, String createdDate,
                        String updatedBy, String updatedDate) {
        this.contactID = contactID;
        this.address = address;
        this.district = district;
        this.amphur = amphur;
        this.province = province;
        this.postalCode = postalCode;
        this.telephone = telephone;
        this.fax = fax;
        this.city = city;
        this.state = state;
        this.country = country;
        this.contactPerson = contactPerson;
        this.email = email;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }
}
