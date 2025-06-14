/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
public class ComponentSpec {
    private int specId;
    private String specName;
    private int categoryId;
    // constructors, getters, setters...

    public ComponentSpec() {
    }

    public ComponentSpec(int specId, String specName, int categoryId) {
        this.specId = specId;
        this.specName = specName;
        this.categoryId = categoryId;
    }

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "ComponentSpec{" + "specId=" + specId + ", specName=" + specName + ", categoryId=" + categoryId + '}';
    }
    
}

