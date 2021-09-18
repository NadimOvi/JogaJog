package com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.Models;

import java.util.List;

public class MwlGetByDate {
    private Integer mwlId;
    private Integer empId;
    private String mwlMonth;
    private Integer lUserId;
    private Integer comId;
    private Double convAmount;
    private Integer aId;
    private String wId;
    private List<MwlDetailsModel> mwL_Detailses;

    public MwlGetByDate() {
    }

    public MwlGetByDate(Integer mwlId, Integer empId, String mwlMonth, Integer lUserId, Integer comId, Double convAmount, Integer aId, String wId, List<MwlDetailsModel> mwL_Detailses) {
        this.mwlId = mwlId;
        this.empId = empId;
        this.mwlMonth = mwlMonth;
        this.lUserId = lUserId;
        this.comId = comId;
        this.convAmount = convAmount;
        this.aId = aId;
        this.wId = wId;
        this.mwL_Detailses = mwL_Detailses;
    }

    public Integer getMwlId() {
        return mwlId;
    }

    public void setMwlId(Integer mwlId) {
        this.mwlId = mwlId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getMwlMonth() {
        return mwlMonth;
    }

    public void setMwlMonth(String mwlMonth) {
        this.mwlMonth = mwlMonth;
    }

    public Integer getlUserId() {
        return lUserId;
    }

    public void setlUserId(Integer lUserId) {
        this.lUserId = lUserId;
    }

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public Double getConvAmount() {
        return convAmount;
    }

    public void setConvAmount(Double convAmount) {
        this.convAmount = convAmount;
    }

    public Integer getaId() {
        return aId;
    }

    public void setaId(Integer aId) {
        this.aId = aId;
    }

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
        this.wId = wId;
    }

    public List<MwlDetailsModel> getMwL_Detailses() {
        return mwL_Detailses;
    }

    public void setMwL_Detailses(List<MwlDetailsModel> mwL_Detailses) {
        this.mwL_Detailses = mwL_Detailses;
    }
}
