package be.pxl.mobdev.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import be.pxl.mobdev.R;
import be.pxl.mobdev.helpers.ClickListener;
import be.pxl.mobdev.models.Car;
import be.pxl.mobdev.util.FirebaseUtil;
import be.pxl.mobdev.views.CarAdapter;

public class ListActivity extends AppCompatActivity {
    private ClickListener listener;
    ArrayList<Car> mCarArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView rvAllCars = (RecyclerView) findViewById(R.id.rvAllCars);
        final CarAdapter adapter = new CarAdapter(false);
        mCarArrayList = adapter.getFavCars();
        setupSort(mCarArrayList, adapter);
        rvAllCars.setAdapter(adapter);
        LinearLayoutManager carsLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAllCars.setLayoutManager(carsLayoutManager);
    }

    public void showMenu(){
        invalidateOptionsMenu();
    }

    private void setupSort(ArrayList<Car> carArrayList, CarAdapter carAdapter){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_types, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Spinner sortSpinner = (Spinner) findViewById(R.id.spinnerSort);
        sortSpinner.setAdapter(adapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    Collections.sort(carArrayList, Car.CarBrandAZComparator);
                    adapter.notifyDataSetChanged();
                    carAdapter.notifyDataSetChanged();
                } else if(i == 1){
                    Collections.sort(carArrayList, Car.CarBrandZAComparator);
                    adapter.notifyDataSetChanged();
                    carAdapter.notifyDataSetChanged();
                } else if (i == 2){
                    Collections.sort(carArrayList, Car.CarBrandPriceAscComparator);
                    adapter.notifyDataSetChanged();
                    carAdapter.notifyDataSetChanged();
                } else if (i == 3){
                    Collections.sort(carArrayList, Car.CarBrandPriceDescComparator);
                    adapter.notifyDataSetChanged();
                    carAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
