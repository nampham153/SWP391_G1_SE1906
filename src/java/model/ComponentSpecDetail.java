package model;

public class ComponentSpecDetail {

    private String componentId;
    private int specId;
    private String specDetail;

    public ComponentSpecDetail() {
    }

    public ComponentSpecDetail(String componentId, int specId, String specDetail) {
        this.componentId = componentId;
        this.specId = specId;
        this.specDetail = specDetail;
    }

    public String getComponentId() {
        return componentId;
    }

    public int getSpecId() {
        return specId;
    }

    public String getSpecDetail() {
        return specDetail;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public void setSpecDetail(String specDetail) {
        this.specDetail = specDetail;
    }

}
