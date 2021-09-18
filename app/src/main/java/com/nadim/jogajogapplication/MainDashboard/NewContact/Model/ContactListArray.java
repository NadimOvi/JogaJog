package com.nadim.jogajogapplication.MainDashboard.NewContact.Model;

public class ContactListArray {
    private String name;
    private String designation;
    private String email;
    private String phoneNumber;
    private Integer addedByUserId;
    private Integer updateByUserId;
    private ContactImage contactImage;

    public ContactListArray() {
    }

    public ContactListArray(String name, String designation, String email, String phoneNumber, Integer addedByUserId, Integer updateByUserId, ContactImage contactImage) {
        this.name = name;
        this.designation = designation;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addedByUserId = addedByUserId;
        this.updateByUserId = updateByUserId;
        this.contactImage = contactImage;
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

    public ContactImage getContactImage() {
        return contactImage;
    }

    public void setContactImage(ContactImage contactImage) {
        this.contactImage = contactImage;
    }
}
