package com.nadim.jogajogapplication.MainDashboard.Models;

public class OldContactShow {
    private Integer contactId;
    private String name;
    private Integer custId;
    private String designation;
    private String email;
    private String phoneNumber;

    private String dateAdded;
    private String dateUpdated;
    private Integer addedByUserId;
    private Integer updateByUserId;
    private CompanyProfile customer;

    public OldContactShow() {
    }

    public OldContactShow(Integer contactId, String name, Integer custId, String designation, String email, String phoneNumber, String dateAdded, String dateUpdated, Integer addedByUserId, Integer updateByUserId, CompanyProfile customer) {
        this.contactId = contactId;
        this.name = name;
        this.custId = custId;
        this.designation = designation;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateAdded = dateAdded;
        this.dateUpdated = dateUpdated;
        this.addedByUserId = addedByUserId;
        this.updateByUserId = updateByUserId;
        this.customer = customer;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Integer getAddedByUserId() {
        return addedByUserId;
    }

    public void setAddedByUserId(Integer addedByUserId) {
        this.addedByUserId = addedByUserId;
    }

    public Integer getUpdateByUserId() {
        return updateByUserId;
    }

    public void setUpdateByUserId(Integer updateByUserId) {
        this.updateByUserId = updateByUserId;
    }

    public CompanyProfile getCustomer() {
        return customer;
    }

    public void setCustomer(CompanyProfile customer) {
        this.customer = customer;
    }
}
