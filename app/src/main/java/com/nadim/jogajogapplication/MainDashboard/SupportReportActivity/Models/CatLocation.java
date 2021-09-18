package com.nadim.jogajogapplication.MainDashboard.SupportReportActivity.Models;

public class CatLocation {
    private Integer locationId;
    private String locationName;
    private Integer areaId;

    public CatLocation(Integer locationId, String locationName, Integer areaId) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.areaId = areaId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}
