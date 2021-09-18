package com.nadim.jogajogapplication.CustomerContactActivity.Model;

public class GTProduct {
    private Integer gtProductId;
    private String productName;
    private Integer categoryId;

    public GTProduct() {
    }

    public GTProduct(Integer gtProductId, String productName, Integer categoryId) {
        this.gtProductId = gtProductId;
        this.productName = productName;
        this.categoryId = categoryId;
    }

    public Integer getGtProductId() {
        return gtProductId;
    }

    public void setGtProductId(Integer gtProductId) {
        this.gtProductId = gtProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
