package model;

public class BrandComponentCategory {

    private int brandId;
    private int categoryId;

    public BrandComponentCategory() {
    }

    public BrandComponentCategory(int brandId, int categoryId) {
        this.brandId = brandId;
        this.categoryId = categoryId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public int getCategoryId() {
        return categoryId;
    }

}
