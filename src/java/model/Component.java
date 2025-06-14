/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
public class Component {
    private String componentId;
    private int categoryId;
    // constructors, getters, setters...

    public Component() {
    }

    public Component(String componentId, int categoryId) {
        this.componentId = componentId;
        this.categoryId = categoryId;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Component{" + "componentId=" + componentId + ", categoryId=" + categoryId + '}';
    }
    
}

