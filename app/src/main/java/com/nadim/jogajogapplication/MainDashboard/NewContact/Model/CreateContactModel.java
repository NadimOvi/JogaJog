package com.nadim.jogajogapplication.MainDashboard.NewContact.Model;

import java.util.List;

public class CreateContactModel {
    private String custName;
   private List<ContactListArray> contacts;

    public CreateContactModel(String custName, List<ContactListArray> contacts) {
        this.custName = custName;
        this.contacts = contacts;
    }

    public CreateContactModel() {
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public List<ContactListArray> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactListArray> contacts) {
        this.contacts = contacts;
    }
}
