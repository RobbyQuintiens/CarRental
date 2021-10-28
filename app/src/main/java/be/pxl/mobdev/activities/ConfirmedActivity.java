package be.pxl.mobdev.activities;

import static java.util.concurrent.TimeUnit.DAYS;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import be.pxl.mobdev.R;
import be.pxl.mobdev.models.Car;
import be.pxl.mobdev.util.FirebaseUtil;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ConfirmedActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    TextView car;
    TextView type;
    TextView fromDate;
    TextView tillDate;
    TextView price;
    ImageView carImage;
    Car carObject;
    Button btnHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed);

        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        car = (TextView) findViewById(R.id.textCar);
        type = (TextView) findViewById(R.id.textType);
        fromDate = (TextView) findViewById(R.id.textFromDate);
        tillDate = (TextView) findViewById(R.id.textTillDate);
        price = (TextView) findViewById(R.id.textPrice);
        carImage = (ImageView) findViewById(R.id.imageViewCar);
        btnHome = (Button) findViewById(R.id.btnHome);

        Intent detailIntent = getIntent();
        if (detailIntent.hasExtra(Intent.EXTRA_TEXT)) {
            carObject = (Car) detailIntent.getSerializableExtra(Intent.EXTRA_TEXT);
        }

        car.setText(carObject.getBrand() + " " + carObject.getModel());
        type.setText(carObject.getType().toString());
        fromDate.setText(carObject.getHiredFromDate());
        tillDate.setText(carObject.getHiredTillDate());
        try {
            price.setText(String.valueOf(getPrice(carObject.getHiredFromDate(), carObject.getHiredTillDate(), carObject.getDayPrice())) + " EUR");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        showImage(carObject.getImageUrl());

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmedActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private int getPrice(String fromDate, String tillDate, int price) throws ParseException {
        LocalDate from = LocalDate.parse(fromDate, FORMATTER);
        LocalDate till = LocalDate.parse(tillDate, FORMATTER);
        Period period = Period.between(till, from);
        int days = Math.abs(period.getDays());
        if (days == 0) {
            days = 1;
        }
        return days * price;
    }

    private void showImage(String url) {
        int width = getDisplayWidth();
        if (url != null && !url.isEmpty()) {
            Picasso.get()
                    .load(url)
                    .resize(width, 500)
                    .centerInside()
                    .into(carImage);
        }
    }

    private int getDisplayWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
