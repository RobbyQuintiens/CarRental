package be.pxl.mobdev.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import be.pxl.mobdev.R;
import be.pxl.mobdev.models.Car;
import be.pxl.mobdev.models.Status;
import be.pxl.mobdev.util.FirebaseUtil;

public class DetailActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private final static long DATE = System.currentTimeMillis();
    TextView detailBrand;
    TextView detailModel;
    TextView detailYear;
    TextView detailDoors;
    TextView detailSeats;
    TextView detailPrice;
    TextView detailType;
    TextView detailPetrol;
    TextView tillText;
    TextView fromText;
    Button btnSchedule;
    Button btnNext;
    Button btnBook;
    Button btnCancel;
    ImageView detailImage;
    CalendarView calendarViewFrom;
    CalendarView calendarViewTill;
    Car car;
    ConstraintLayout detailLayoutBackground;
    String dateFrom;
    String dateTill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        detailBrand = (TextView) findViewById(R.id.fragmentCarBrand);
        detailModel = (TextView) findViewById(R.id.fragmentCarModel);
        detailYear = (TextView) findViewById(R.id.fragmentCarYear);
        detailDoors = (TextView) findViewById(R.id.fragmentCarDoorsInput);
        detailSeats = (TextView) findViewById(R.id.fragmentCarSeatsInput);
        detailType = (TextView) findViewById(R.id.fragmentCarTypeInput);
        detailPetrol = (TextView) findViewById(R.id.fragmentCarPetrolInput);
        detailImage = (ImageView) findViewById(R.id.fragmentImage);
        detailImage.setVisibility(View.VISIBLE);
        detailPrice = (TextView) findViewById(R.id.fragmentPrice);
        btnSchedule = (Button) findViewById(R.id.fragmentButtonSchedule);
        btnBook = (Button) findViewById(R.id.fragmentButtonBook);
        btnNext = (Button) findViewById(R.id.fragmentButtonNext);
        btnCancel = (Button) findViewById(R.id.fragmentButtonCancel);
        fromText = (TextView) findViewById(R.id.textFrom);
        tillText = (TextView) findViewById(R.id.textTill);
        calendarViewFrom = (CalendarView) findViewById(R.id.fragmentCalendarViewFrom);
        calendarViewTill = (CalendarView) findViewById(R.id.fragmentCalendarViewTill);
        detailLayoutBackground = (ConstraintLayout) findViewById(R.id.detailLayoutBackground);
        detailLayoutBackground.setVisibility(View.VISIBLE);
        Intent mainIntent = getIntent();
        if (mainIntent.hasExtra(Intent.EXTRA_TEXT)) {
            car = (Car) mainIntent.getSerializableExtra(Intent.EXTRA_TEXT);
        }
        detailBrand.setText(car.getBrand());
        detailModel.setText(car.getModel());
        detailYear.setText(String.valueOf(car.getYear()));
        detailDoors.setText(String.valueOf(car.getDoors()));
        detailSeats.setText(String.valueOf(car.getSeats()));
        detailType.setText(String.valueOf(car.getType()));
        detailPetrol.setText(String.valueOf(car.getBrandstof()));
        detailPrice.setText(String.valueOf(car.getDayPrice()));
        showImage(car.getImageUrl());
        fromText.setVisibility(View.GONE);
        tillText.setVisibility(View.GONE);
        calendarViewFrom.setVisibility(View.GONE);
        calendarViewTill.setVisibility(View.GONE);
        btnBook.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                car.setStatus(Status.RESERVED);
                detailImage.setVisibility(View.GONE);
                btnSchedule.setVisibility(View.GONE);
                detailLayoutBackground.setVisibility(View.GONE);
                fromText.setVisibility(View.VISIBLE);
                calendarViewFrom.setVisibility(View.VISIBLE);
                calendarViewFrom.setMinDate(DATE);
                btnCancel.setVisibility(View.VISIBLE);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fromText.setVisibility(View.GONE);
                        tillText.setVisibility(View.GONE);
                        calendarViewFrom.setVisibility(View.GONE);
                        calendarViewTill.setVisibility(View.GONE);
                        btnBook.setVisibility(View.GONE);
                        btnCancel.setVisibility(View.GONE);
                        detailImage.setVisibility(View.VISIBLE);
                        btnSchedule.setVisibility(View.VISIBLE);
                        detailLayoutBackground.setVisibility(View.VISIBLE);
                    }
                });
                calendarViewFrom.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                        int m = month + 1;
                        dateFrom = day + "/" + m  + "/" + year;
                    }
                });
                btnNext.setVisibility(View.VISIBLE);
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fromText.setVisibility(View.GONE);
                        tillText.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.GONE);
                        btnBook.setVisibility(View.VISIBLE);
                        calendarViewFrom.setVisibility(View.GONE);
                        calendarViewTill.setVisibility(View.VISIBLE);
                        calendarViewTill.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                            @Override
                            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                                int m = month + 1;
                                dateTill = day + "/" + m  + "/" + year;
                            }
                        });
                        btnBook.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //TODO attach userInfo to DB (Create new Model - User - Reservation)
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                String dateConfirmed = sdf.format(new Date(DATE));
                                car.setStatus(Status.HIRED);
                                car.setConfirmedAt(dateConfirmed);
                                car.setHiredFromDate(dateFrom);
                                car.setHiredTillDate(dateTill);
                                mDatabaseReference.child(car.getId()).setValue(car);
                                calendarViewTill.setVisibility(View.GONE);
                                btnBook.setVisibility(View.GONE);
                                btnCancel.setVisibility(View.GONE);
                                tillText.setVisibility(View.GONE);
                                detailImage.setVisibility(View.VISIBLE);
                                btnSchedule.setVisibility(View.VISIBLE);
                                detailLayoutBackground.setVisibility(View.VISIBLE);
                                //TODO new intentpage final info of booking (userinfo, cardetail, date);
                                Intent confirmIntent = new Intent(DetailActivity.this, ConfirmedActivity.class);
                                confirmIntent.putExtra(Intent.EXTRA_TEXT, car);
                                startActivity(confirmIntent);
                            }
                        });
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit_menu) {
            Car selectedCar = car;
            Intent editIntent = new Intent(DetailActivity.this, CarActivity.class);
            editIntent.putExtra(Intent.EXTRA_TEXT, selectedCar);
            startActivity(editIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
