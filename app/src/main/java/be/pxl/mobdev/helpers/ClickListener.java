package be.pxl.mobdev.helpers;

import android.view.View;

import be.pxl.mobdev.models.Car;

public interface ClickListener {
    void onItemClick(View view, int position, Car car);
}
