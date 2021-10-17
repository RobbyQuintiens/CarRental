package be.pxl.mobdev.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import be.pxl.mobdev.R;
import be.pxl.mobdev.util.FirebaseUtil;
import be.pxl.mobdev.views.CarAdapter;

public class ListActivity extends AppCompatActivity {

    //TODO add Sort By
    //TODO add Loading message in RV onCreate
    //TODO fix RecyclerView

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
        final CarAdapter adapter = new CarAdapter();
        rvAllCars.setAdapter(adapter);
        LinearLayoutManager carsLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAllCars.setLayoutManager(carsLayoutManager);
    }

    public void showMenu(){
        invalidateOptionsMenu();
    }
}
