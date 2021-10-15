package be.pxl.mobdev.models;

public enum Type {
    SEDAN("Sedan"), BREAK("Break"), COUPE("Coupe"), SPORT("Sport"), SUV("SUV"), VAN("Van");

    private String label;

    private Type(String label) {
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
