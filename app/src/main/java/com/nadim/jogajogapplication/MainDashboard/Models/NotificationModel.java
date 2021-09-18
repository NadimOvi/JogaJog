package com.nadim.jogajogapplication.MainDashboard.Models;

public class NotificationModel {
    private String todayDiscussion;
    private String nextDiscussion;
    private String meetingReason;
    private String reminderDate;
    private String dateAdded;
    private Integer empId;
    private NotificationContactObject contact;

    public NotificationModel() {
    }

    public NotificationModel(String todayDiscussion, String nextDiscussion, String meetingReason, String reminderDate, String dateAdded, Integer empId, NotificationContactObject contact) {
        this.todayDiscussion = todayDiscussion;
        this.nextDiscussion = nextDiscussion;
        this.meetingReason = meetingReason;
        this.reminderDate = reminderDate;
        this.dateAdded = dateAdded;
        this.empId = empId;
        this.contact = contact;
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

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public NotificationContactObject getContact() {
        return contact;
    }

    public void setContact(NotificationContactObject contact) {
        this.contact = contact;
    }
}
