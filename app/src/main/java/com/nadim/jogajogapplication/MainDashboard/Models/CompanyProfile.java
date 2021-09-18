package com.nadim.jogajogapplication.MainDashboard.Models;

public class CompanyProfile {
    private Integer custId;
    private String custCode;
    private String custName;
    private String srtName;

    public CompanyProfile() {
    }

    public CompanyProfile(Integer custId, String custCode, String custName, String srtName) {
        this.custId = custId;
        this.custCode = custCode;
        this.custName = custName;
        this.srtName = srtName;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getSrtName() {
        return srtName;
    }

    public void setSrtName(String srtName) {
        this.srtName = srtName;
    }
}
