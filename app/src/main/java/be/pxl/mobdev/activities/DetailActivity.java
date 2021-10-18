package be.pxl.mobdev.activities;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import be.pxl.mobdev.R;
import be.pxl.mobdev.models.Car;
import be.pxl.mobdev.util.FirebaseUtil;

public class DetailActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    TextView detailBrand;
    TextView detailModel;
    TextView detailYear;
    TextView detailDoors;
    TextView detailSeats;
    ImageView detailImage;
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        detailBrand = (TextView) findViewById(R.id.fragmentCarBrand);
        detailModel = (TextView) findViewById(R.id.fragmentCarModel);
        detailYear = (TextView) findViewById(R.id.fragmentCarYear);
        detailDoors = (TextView) findViewById(R.id.fragmentCarDoors);
        detailSeats = (TextView) findViewById(R.id.fragmentCarSeats);
        detailImage = (ImageView) findViewById(R.id.fragmentImage);
        Intent mainIntent = getIntent();
        if (mainIntent.hasExtra(Intent.EXTRA_TEXT)) {
            car = (Car) mainIntent.getSerializableExtra(Intent.EXTRA_TEXT);
        }
        detailBrand.setText(car.getBrand());
        detailModel.setText(car.getModel());
        detailYear.setText(String.valueOf(car.getYear()));
        detailDoors.setText(String.valueOf(car.getDoors()));
        detailSeats.setText(String.valueOf(car.getSeats()));
        showImage(car.getImageUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_activity_menu, menu);
        if (FirebaseUtil.isAdmin) {
            menu.findItem(R.id.edit_menu).setVisible(true);
            menu.findItem(R.id.edit_menu).setEnabled(true);
        } else {
            menu.findItem(R.id.edit_menu).setVisible(false);
            menu.findItem(R.id.edit_menu).setEnabled(false);
        }
        return true;
    }

    private void showImage(String url) {
        int width = getDisplayWidth();
        if (url != null && !url.isEmpty()) {
            Picasso.get()
                    .load(url)
                    .resize(width, 500)
                    .centerInside()
                    .into(detailImage);
        }
    }

    private int getDisplayWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

}
