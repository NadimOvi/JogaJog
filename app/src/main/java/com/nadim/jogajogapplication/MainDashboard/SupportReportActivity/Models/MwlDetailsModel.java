package com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.Models;

public class MwlDetailsModel {
    private Integer aID;
    private Integer mwlId;
    private String mWlDate;
    private Integer locationId;
    private Integer clientId;
    private String clientName;
    private String contPerson;
    private String contDesig;
    private String contMobile;
    private Integer supportId;
    private String supportNameShort;
    private String purpos;
    private String description;
    private String withP;
    private String stTime;
    private String endTime;
    private String next_step;
    private Integer isExistConv;
    private CatLocation cat_Location;

    public MwlDetailsModel() {
    }

    public MwlDetailsModel(Integer aID, Integer mwlId, String mWlDate, Integer locationId, Integer clientId, String clientName, String contPerson, String contDesig, String contMobile, Integer supportId, String supportNameShort, String purpos, String description, String withP, String stTime, String endTime, String next_step, Integer isExistConv, CatLocation cat_Location) {
        this.aID = aID;
        this.mwlId = mwlId;
        this.mWlDate = mWlDate;
        this.locationId = locationId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.contPerson = contPerson;
        this.contDesig = contDesig;
        this.contMobile = contMobile;
        this.supportId = supportId;
        this.supportNameShort = supportNameShort;
        this.purpos = purpos;
        this.description = description;
        this.withP = withP;
        this.stTime = stTime;
        this.endTime = endTime;
        this.next_step = next_step;
        this.isExistConv = isExistConv;
        this.cat_Location = cat_Location;
    }

    public Integer getaID() {
        return aID;
    }

    public void setaID(Integer aID) {
        this.aID = aID;
    }

    public Integer getMwlId() {
        return mwlId;
    }

    public void setMwlId(Integer mwlId) {
        this.mwlId = mwlId;
    }

    public String getmWlDate() {
        return mWlDate;
    }

    public void setmWlDate(String mWlDate) {
        this.mWlDate = mWlDate;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getContPerson() {
        return contPerson;
    }

    public void setContPerson(String contPerson) {
        this.contPerson = contPerson;
    }

    public String getContDesig() {
        return contDesig;
    }

    public void setContDesig(String contDesig) {
        this.contDesig = contDesig;
    }

    public String getContMobile() {
        return contMobile;
    }

    public void setContMobile(String contMobile) {
        this.contMobile = contMobile;
    }

    public Integer getSupportId() {
        return supportId;
    }

    public void setSupportId(Integer supportId) {
        this.supportId = supportId;
    }

    public String getSupportNameShort() {
        return supportNameShort;
    }

    public void setSupportNameShort(String supportNameShort) {
        this.supportNameShort = supportNameShort;
    }

    public String getPurpos() {
        return purpos;
    }

    public void setPurpos(String purpos) {
        this.purpos = purpos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWithP() {
        return withP;
    }

    public void setWithP(String withP) {
        this.withP = withP;
    }

    public String getStTime() {
        return stTime;
    }

    public void setStTime(String stTime) {
        this.stTime = stTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNext_step() {
        return next_step;
    }

    public void setNext_step(String next_step) {
        this.next_step = next_step;
    }

    public Integer getIsExistConv() {
        return isExistConv;
    }

    public void setIsExistConv(Integer isExistConv) {
        this.isExistConv = isExistConv;
    }

    public CatLocation getCat_Location() {
        return cat_Location;
    }

    public void setCat_Location(CatLocation cat_Location) {
        this.cat_Location = cat_Location;
    }
}
