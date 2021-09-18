package com.nadim.jogajogapplication.Login.Model;

public class Designation {
    private Integer desigId;
    private String desigName;

    public Designation(Integer desigId, String desigName) {
        this.desigId = desigId;
        this.desigName = desigName;
    }

    public Designation() {
    }

    public Integer getDesigId() {
        return desigId;
    }

    public void setDesigId(Integer desigId) {
        this.desigId = desigId;
    }

    public String getDesigName() {
        return desigName;
    }

    public void setDesigName(String desigName) {
        this.desigName = desigName;
    }
}
