package be.pxl.mobdev.activities;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import be.pxl.mobdev.R;
import be.pxl.mobdev.models.Car;
import be.pxl.mobdev.models.Status;
import be.pxl.mobdev.util.FirebaseUtil;

public class DetailActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    TextView detailBrand;
    TextView detailModel;
    TextView detailYear;
    TextView detailDoors;
    TextView detailSeats;
    TextView detailPrice;
    Button btnSchedule;
    Button btnBook;
    ImageView detailImage;
    CalendarView calendarView;
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        detailBrand = (TextView) findViewById(R.id.fragmentCarBrand);
        detailModel = (TextView) findViewById(R.id.fragmentCarModel);
        detailYear = (TextView) findViewById(R.id.fragmentCarYear);
        detailDoors = (TextView) findViewById(R.id.fragmentCarDoors);
        detailSeats = (TextView) findViewById(R.id.fragmentCarSeats);
        detailImage = (ImageView) findViewById(R.id.fragmentImage);
        detailImage.setVisibility(View.VISIBLE);
        detailPrice = (TextView) findViewById(R.id.fragmentPrice);
        btnSchedule = (Button) findViewById(R.id.fragmentButtonSchedule);
        btnBook = (Button) findViewById(R.id.fragmentButtonBook);
        calendarView = (CalendarView) findViewById(R.id.fragmentCalendarView);
        Intent mainIntent = getIntent();
        if (mainIntent.hasExtra(Intent.EXTRA_TEXT)) {
            car = (Car) mainIntent.getSerializableExtra(Intent.EXTRA_TEXT);
        }
        detailBrand.setText(car.getBrand());
        detailModel.setText(car.getModel());
        detailYear.setText(String.valueOf(car.getYear()));
        detailDoors.setText(String.valueOf(car.getDoors()));
        detailSeats.setText(String.valueOf(car.getSeats()));
        detailPrice.setText(String.valueOf(car.getDayPrice()));
        showImage(car.getImageUrl());
        calendarView.setVisibility(View.GONE);
        btnBook.setVisibility(View.GONE);
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                car.setStatus(Status.RESERVED);
                detailImage.setVisibility(View.GONE);
                calendarView.setVisibility(View.VISIBLE);
                btnBook.setVisibility(View.VISIBLE);
                btnBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String date = sdf.format(new Date(calendarView.getDate()));
                        car.setStatus(Status.HIRED);
                        car.setConfirmedAt(date);
                        mDatabaseReference.child(car.getId()).setValue(car);
                        calendarView.setVisibility(View.GONE);
                        btnBook.setVisibility(View.GONE);
                        detailImage.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
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
