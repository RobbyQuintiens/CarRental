package be.pxl.mobdev.models;

import java.time.LocalDate;

public interface Vehicle {

    public void getStatus();
    public LocalDate getLockedAt();
    public LocalDate getConfirmedAt();

}
