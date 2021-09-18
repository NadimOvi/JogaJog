package com.nadim.jogajogapplication.Login.Model;

import java.util.List;

public class UserEmployeeInfo {
    private EmpInfo empInfo;
    private String token;

    public UserEmployeeInfo(EmpInfo empInfo, String token) {
        this.empInfo = empInfo;
        this.token = token;
    }

    public UserEmployeeInfo() {
    }

    public EmpInfo getEmpInfo() {
        return empInfo;
    }

    public void setEmpInfo(EmpInfo empInfo) {
        this.empInfo = empInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
