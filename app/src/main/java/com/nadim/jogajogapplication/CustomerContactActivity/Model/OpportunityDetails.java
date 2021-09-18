package com.nadim.jogajogapplication.CustomerContactActivity.Model;

import java.util.List;

public class OpportunityDetails {
    private Integer oppDetailsId;
    private Integer oppMasterId;
    private Stage stage;
    private Boolean isComplete;
    private String closingDate;
    private String remarks;

    /*private String fileInfo;*/
    private List<Product> products;
    private String dateAdded;
    private String dateUpdated;
    private Integer addedByUserId;
    private Integer updateByUserId;

    public OpportunityDetails() {
    }

    public OpportunityDetails(Integer oppDetailsId, Integer oppMasterId, Stage stage, Boolean isComplete, String closingDate, String remarks, List<Product> products, String dateAdded, String dateUpdated, Integer addedByUserId, Integer updateByUserId) {
        this.oppDetailsId = oppDetailsId;
        this.oppMasterId = oppMasterId;
        this.stage = stage;
        this.isComplete = isComplete;
        this.closingDate = closingDate;
        this.remarks = remarks;
        this.products = products;
        this.dateAdded = dateAdded;
        this.dateUpdated = dateUpdated;
        this.addedByUserId = addedByUserId;
        this.updateByUserId = updateByUserId;
    }

    public Integer getOppDetailsId() {
        return oppDetailsId;
    }

    public void setOppDetailsId(Integer oppDetailsId) {
        this.oppDetailsId = oppDetailsId;
    }

    public Integer getOppMasterId() {
        return oppMasterId;
    }

    public void setOppMasterId(Integer oppMasterId) {
        this.oppMasterId = oppMasterId;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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
}
