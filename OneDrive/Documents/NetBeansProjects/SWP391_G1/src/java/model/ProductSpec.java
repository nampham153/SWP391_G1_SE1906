package model;

public class ProductSpec {

    private int specId;
    private String specName;
    private int categoryId;

    public ProductSpec() {
    }

    public ProductSpec(int specId, String specName, int categoryId) {
        this.specId = specId;
        this.specName = specName;
        this.categoryId = categoryId;
    }

    public int getSpecId() {
        return specId;
    }

    public String getSpecName() {
        return specName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

}
