package be.pxl.mobdev.models;

public enum Status {
    AVAILABLE("Available"), RESERVED("Reserved"), HIRED("Hired");

    private String label;

    private Status(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
