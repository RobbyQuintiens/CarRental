package be.pxl.mobdev.models;

public enum Brandstof {
    BENZINE("Benzine"), DIESEL("Diesel"), HYBRIDE("Hybride"), ELEKTRISCH("Elektrisch");

    private String label;

    private Brandstof(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
