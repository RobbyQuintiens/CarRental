package be.pxl.mobdev.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.pxl.mobdev.R;
import be.pxl.mobdev.models.Car;
import be.pxl.mobdev.util.FirebaseUtil;
import be.pxl.mobdev.views.CarAdapter;

public class MainActivity extends AppCompatActivity {

    ArrayList<Car> carArrayList;

    //TODO add Loading message in RV onCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnExloreAll = (Button) findViewById(R.id.btnExplore);

        btnExloreAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        if (FirebaseUtil.isAdmin) {
            menu.findItem(R.id.add_car_menu).setVisible(true);
            menu.findItem(R.id.add_car_menu).setEnabled(true);
        } else {
            menu.findItem(R.id.add_car_menu).setVisible(false);
            menu.findItem(R.id.add_car_menu).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_car_menu:
                Intent intent = new Intent(this, CarActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout_menu:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseUtil.attachListener();
                            }
                        });
                FirebaseUtil.detachListener();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.openFbReference("cardeals", this);
        RecyclerView rvFavorites = (RecyclerView) findViewById(R.id.rvFavorites);
        final CarAdapter adapter = new CarAdapter();
        carArrayList = adapter.getFavCars();
        setupSort(carArrayList, adapter);
        rvFavorites.setAdapter(adapter);
        LinearLayoutManager carsLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvFavorites.setLayoutManager(carsLayoutManager);

        FirebaseUtil.attachListener();
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