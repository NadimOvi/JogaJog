package com.nadim.jogajogapplication.MainDashboard.NewContact.Model;

import org.apache.commons.lang3.StringUtils;


public class Profile {
    private Integer id;
    private String name;
    private String jobTitle;

    private String primaryContactNumber;
    private String secondaryContactNumber;
    private String email;

    public boolean isValid(){
        boolean isValid = StringUtils.isNotBlank(name);
        return isValid;
    }

    public Profile(Integer id, String name, String jobTitle, String primaryContactNumber, String secondaryContactNumber, String email) {
        this.id = id;
        this.name = name;
        this.jobTitle = jobTitle;
        this.primaryContactNumber = primaryContactNumber;
        this.secondaryContactNumber = secondaryContactNumber;
        this.email = email;
    }

    public Profile(String s, String toString, String string, String s1) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPrimaryContactNumber() {
        return primaryContactNumber;
    }

    public void setPrimaryContactNumber(String primaryContactNumber) {
        this.primaryContactNumber = primaryContactNumber;
    }

    public String getSecondaryContactNumber() {
        return secondaryContactNumber;
    }

    public void setSecondaryContactNumber(String secondaryContactNumber) {
        this.secondaryContactNumber = secondaryContactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
