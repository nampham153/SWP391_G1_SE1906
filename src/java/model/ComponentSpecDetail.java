/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
public class ComponentSpecDetail {
    private String componentId;
    private int specId;
    private String specDetail;
    // constructors, getters, setters...

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

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public String getSpecDetail() {
        return specDetail;
    }

    public void setSpecDetail(String specDetail) {
        this.specDetail = specDetail;
    }

    @Override
    public String toString() {
        return "ComponentSpecDetail{" + "componentId=" + componentId + ", specId=" + specId + ", specDetail=" + specDetail + '}';
    }
    
}
