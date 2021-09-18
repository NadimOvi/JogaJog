package com.nadim.jogajogapplication.MainDashboard.PhoneBookContactList.contacts;


public class SystemContact {

    private long rawContactId;
    private long contactId;
    private String number;
    private String name;

    public SystemContact() {
    }

    public long getRawContactId() {
        return rawContactId;
    }

    public void setRawContactId(long rawContactId) {
        this.rawContactId = rawContactId;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SystemContact{" +
                "rawContactId=" + rawContactId +
                ", contactId=" + contactId +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
