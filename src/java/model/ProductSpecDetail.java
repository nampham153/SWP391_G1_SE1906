package model;

public class ProductSpecDetail {
    private String productId;
    private String specId;
    private String specDetail; // chính là Item.SerialNumber

    public ProductSpecDetail() {}

    public ProductSpecDetail(String productId, String specId, String specDetail) {
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

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getSpecDetail() {
        return specDetail;
    }

    public void setSpecDetail(String specDetail) {
        this.specDetail = specDetail;
    }
}
