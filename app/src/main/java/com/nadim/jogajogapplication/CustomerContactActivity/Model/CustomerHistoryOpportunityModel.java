package com.nadim.jogajogapplication.CustomerContactActivity.Model;

import java.util.List;

public class CustomerHistoryOpportunityModel {
    private Integer oppMasterId;
    private Integer contactId;

    private List<OpportunityDetails> opportunityDetailses;
    private boolean isExisting;
    private String remarks;
    private String dateAdded;
    private String dateUpdated;
    private Integer addedByUserId;

    public CustomerHistoryOpportunityModel() {
    }

    public CustomerHistoryOpportunityModel(Integer oppMasterId, Integer contactId, List<OpportunityDetails> opportunityDetailses, boolean isExisting, String remarks, String dateAdded, String dateUpdated, Integer addedByUserId) {
        this.oppMasterId = oppMasterId;
        this.contactId = contactId;
        this.opportunityDetailses = opportunityDetailses;
        this.isExisting = isExisting;
        this.remarks = remarks;
        this.dateAdded = dateAdded;
        this.dateUpdated = dateUpdated;
        this.addedByUserId = addedByUserId;
    }

    public Integer getOppMasterId() {
        return oppMasterId;
    }

    public void setOppMasterId(Integer oppMasterId) {
        this.oppMasterId = oppMasterId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public List<OpportunityDetails> getOpportunityDetailses() {
        return opportunityDetailses;
    }

    public void setOpportunityDetailses(List<OpportunityDetails> opportunityDetailses) {
        this.opportunityDetailses = opportunityDetailses;
    }

    public boolean isExisting() {
        return isExisting;
    }

    public void setExisting(boolean existing) {
        isExisting = existing;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
}
