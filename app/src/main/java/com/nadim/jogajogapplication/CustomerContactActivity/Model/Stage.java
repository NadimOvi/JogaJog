package com.nadim.jogajogapplication.CustomerContactActivity.Model;

public class Stage {
    private Integer stageId;
    private String stageName;
    private Integer slNo;

    public Stage() {
    }

    public Stage(Integer stageId, String stageName, Integer slNo) {
        this.stageId = stageId;
        this.stageName = stageName;
        this.slNo = slNo;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Integer getSlNo() {
        return slNo;
    }

    public void setSlNo(Integer slNo) {
        this.slNo = slNo;
    }
}
