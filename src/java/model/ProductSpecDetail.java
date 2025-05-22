package model;

public class ProductSpecDetail {

    private String productId;
    private int specId;
    private String specDetail;

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

    public int getSpecId() {
        return specId;
    }

    public String getSpecDetail() {
        return specDetail;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public void setSpecDetail(String specDetail) {
        this.specDetail = specDetail;
    }

}
