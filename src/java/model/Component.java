package model;

public class Component {

    private String componentId;
    private int categoryId;

    public Component() {
    }

    public Component(String componentId, int categoryId) {
        this.componentId = componentId;
        this.categoryId = categoryId;
    }

    public String getComponentId() {
        return componentId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

}
