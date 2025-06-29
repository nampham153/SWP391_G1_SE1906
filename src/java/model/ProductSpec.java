package model;

public class ProductSpec {
    private String specId;
    private String specName;

    public ProductSpec() {}

    public ProductSpec(String specId, String specName) {
        this.specId = specId;
        this.specName = specName;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }
}
