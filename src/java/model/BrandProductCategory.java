/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
public class BrandProductCategory {
    private int brandId;
    private int categoryId;
    // constructors, getters, setters...

    public BrandProductCategory() {
    }

    public BrandProductCategory(int brandId, int categoryId) {
        this.brandId = brandId;
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "BrandProductCategory{" + "brandId=" + brandId + ", categoryId=" + categoryId + '}';
    }
    
}
