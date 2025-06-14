/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
public class ProductSpecDetail {
    private String productId;
    private int specId;
    private String specDetail;
    // constructors, getters, setters...

    public ProductSpecDetail() {
    }

    public ProductSpecDetail(String productId, int specId, String specDetail) {
        this.productId = productId;
        this.specId = specId;
        this.specDetail = specDetail;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public String getSpecDetail() {
        return specDetail;
    }

    public void setSpecDetail(String specDetail) {
        this.specDetail = specDetail;
    }

    @Override
    public String toString() {
        return "ProductSpecDetail{" + "productId=" + productId + ", specId=" + specId + ", specDetail=" + specDetail + '}';
    }
    
}

