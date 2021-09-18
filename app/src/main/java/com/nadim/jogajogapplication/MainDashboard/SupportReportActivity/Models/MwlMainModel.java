package com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.Models;

import java.util.List;

public class MwlMainModel {
    private List<MwlGetByDate> mwl;

    public MwlMainModel(List<MwlGetByDate> mwl) {
        this.mwl = mwl;
    }

    public List<MwlGetByDate> getMwl() {
        return mwl;
    }

    public void setMwl(List<MwlGetByDate> mwl) {
        this.mwl = mwl;
    }
}
