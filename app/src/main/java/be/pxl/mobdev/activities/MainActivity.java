package be.pxl.mobdev.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import be.pxl.mobdev.R;
import be.pxl.mobdev.models.Car;
import be.pxl.mobdev.util.FirebaseUtil;
import be.pxl.mobdev.views.CarAdapter;

public class MainActivity extends AppCompatActivity {

    //TODO add Loading message in RV

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        rvFavorites.setAdapter(adapter);
        LinearLayoutManager carsLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvFavorites.setLayoutManager(carsLayoutManager);
        FirebaseUtil.attachListener();
    }

    public void showMenu(){
        invalidateOptionsMenu();
    }
}