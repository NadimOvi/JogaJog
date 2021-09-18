package com.nadim.jogajogapplication.Login.Model;

public class EmpInfo {
    private Integer empId;
    private String empCode;
    private String empName;
    private String desigName;
    private String empImage;
    private Integer desigId;
    private Designation designation;

    public EmpInfo(Integer empId, String empCode, String empName, String desigName, String empImage, Integer desigId, Designation designation) {
        this.empId = empId;
        this.empCode = empCode;
        this.empName = empName;
        this.desigName = desigName;
        this.empImage = empImage;
        this.desigId = desigId;
        this.designation = designation;
    }

    public EmpInfo() {
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDesigName() {
        return desigName;
    }

    public void setDesigName(String desigName) {
        this.desigName = desigName;
    }

    public String getEmpImage() {
        return empImage;
    }

    public void setEmpImage(String empImage) {
        this.empImage = empImage;
    }

    public Integer getDesigId() {
        return desigId;
    }

    public void setDesigId(Integer desigId) {
        this.desigId = desigId;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }
}
