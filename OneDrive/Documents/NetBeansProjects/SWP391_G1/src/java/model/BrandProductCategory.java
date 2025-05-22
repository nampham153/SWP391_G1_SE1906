package model;

public class BrandProductCategory {

    private int brandId;
    private int categoryId;

    public BrandProductCategory() {
    }

    public BrandProductCategory(int brandId, int categoryId) {
        this.brandId = brandId;
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

}
