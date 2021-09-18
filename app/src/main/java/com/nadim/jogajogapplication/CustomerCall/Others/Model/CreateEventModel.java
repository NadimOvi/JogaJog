package com.nadim.jogajogapplication.CustomerCall.Others.Model;

public class CreateEventModel {
    Integer callHistoryId;
    String todayDiscussion;
    String nextDiscussion;
    String meetingReason;
    String reminderDate;
    String contactId;
    String empId;

    public CreateEventModel() {
    }

    public CreateEventModel(Integer callHistoryId, String todayDiscussion, String nextDiscussion, String meetingReason, String reminderDate, String contactId, String empId) {
        this.callHistoryId = callHistoryId;
        this.todayDiscussion = todayDiscussion;
        this.nextDiscussion = nextDiscussion;
        this.meetingReason = meetingReason;
        this.reminderDate = reminderDate;
        this.contactId = contactId;
        this.empId = empId;
    }

    public Integer getCallHistoryId() {
        return callHistoryId;
    }

    public void setCallHistoryId(Integer callHistoryId) {
        this.callHistoryId = callHistoryId;
    }

    public String getTodayDiscussion() {
        return todayDiscussion;
    }

    public void setTodayDiscussion(String todayDiscussion) {
        this.todayDiscussion = todayDiscussion;
    }

    public String getNextDiscussion() {
        return nextDiscussion;
    }

    public void setNextDiscussion(String nextDiscussion) {
        this.nextDiscussion = nextDiscussion;
    }

    public String getMeetingReason() {
        return meetingReason;
    }

    public void setMeetingReason(String meetingReason) {
        this.meetingReason = meetingReason;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
