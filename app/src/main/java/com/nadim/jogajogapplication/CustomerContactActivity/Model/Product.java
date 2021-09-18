package com.nadim.jogajogapplication.CustomerContactActivity.Model;

public class Product {
    private Integer productId;
    private Integer oppDetailsId;
    private Integer gtProductId;
    private GTProduct gtProduct;

    public Product() {
    }

    public Product(Integer productId, Integer oppDetailsId, Integer gtProductId, GTProduct gtProduct) {
        this.productId = productId;
        this.oppDetailsId = oppDetailsId;
        this.gtProductId = gtProductId;
        this.gtProduct = gtProduct;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getOppDetailsId() {
        return oppDetailsId;
    }

    public void setOppDetailsId(Integer oppDetailsId) {
        this.oppDetailsId = oppDetailsId;
    }

    public Integer getGtProductId() {
        return gtProductId;
    }

    public void setGtProductId(Integer gtProductId) {
        this.gtProductId = gtProductId;
    }

    public GTProduct getGtProduct() {
        return gtProduct;
    }

    public void setGtProduct(GTProduct gtProduct) {
        this.gtProduct = gtProduct;
    }
}
