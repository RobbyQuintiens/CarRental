package be.pxl.mobdev.activities;

import static java.util.concurrent.TimeUnit.DAYS;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    TextView car;
    TextView type;
    TextView fromDate;
    TextView tillDate;
    TextView price;
    ImageView carImage;
    Car carObject;

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

        Intent detailIntent = getIntent();
        if (detailIntent.hasExtra(Intent.EXTRA_TEXT)) {
            carObject = (Car) detailIntent.getSerializableExtra(Intent.EXTRA_TEXT);
        }

        car.setText(carObject.getBrand() + " " + carObject.getModel());
        type.setText(carObject.getType().toString());
        fromDate.setText(carObject.getHiredFromDate());
        tillDate.setText(carObject.getHiredTillDate());
//        try {
//            price.setText(String.valueOf(getPrice(carObject.getHiredFromDate(), carObject.getHiredTillDate(), carObject.getDayPrice())));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

//    private int getPrice(String fromDate, String tillDate, int price) throws ParseException {
//        String date = "16/08/2016";
//        String date2 = "20/08/2016";
//
//        LocalDate from = LocalDate.parse(date, FORMATTER);
//        LocalDate till = LocalDate.parse(date2, FORMATTER);
//        Period period = Period.between(till, from);
//        int days = Math.abs(period.getDays());
//        return days * price;
//    }
}
