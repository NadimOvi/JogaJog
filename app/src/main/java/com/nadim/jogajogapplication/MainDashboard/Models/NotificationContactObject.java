package com.nadim.jogajogapplication.MainDashboard.Models;

public class NotificationContactObject {
    private Integer contactId;
    private Integer custId;
    private String name;
    private String designation;
    private String email;
    private String phoneNumber;
    private String image;
    private String dateAdded;
    private Integer addedByUserId;
    private Integer updateByUserId;

    public NotificationContactObject() {
    }

    public NotificationContactObject(Integer contactId, Integer custId, String name, String designation, String email, String phoneNumber, String image, String dateAdded, Integer addedByUserId, Integer updateByUserId) {
        this.contactId = contactId;
        this.custId = custId;
        this.name = name;
        this.designation = designation;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.dateAdded = dateAdded;
        this.addedByUserId = addedByUserId;
        this.updateByUserId = updateByUserId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
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
}
