package be.pxl.mobdev.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.pxl.mobdev.R;
import be.pxl.mobdev.models.Car;
import be.pxl.mobdev.models.Status;
import be.pxl.mobdev.models.Type;
import be.pxl.mobdev.util.FirebaseUtil;
import be.pxl.mobdev.util.JSONConfig;

public class CarActivity extends AppCompatActivity{

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private static final int PICTURE_RESULT = 42;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    Spinner spinnerBrand;
    EditText txtModel;
    Spinner spinnerType;
    EditText txtYear;
    EditText txtSeats;
    EditText txtDoors;
    EditText txtPrice;
    Car car;
    ImageView imageView;
    private JSONArray result;
    private List<String> carBrands = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        retrieveJSON();
        txtModel = (EditText) findViewById(R.id.txtModel);
        txtYear = (EditText) findViewById(R.id.txtYear);
        txtSeats = (EditText) findViewById(R.id.txtSeats);
        txtDoors = (EditText) findViewById(R.id.txtDoors);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        spinnerType.setAdapter(new ArrayAdapter<Type>(this, R.layout.support_simple_spinner_dropdown_item, Type.values()));
        imageView = (ImageView) findViewById(R.id.image);
        Intent detailIntent = getIntent();
//        if (detailIntent.hasExtra(Intent.EXTRA_TEXT)) {
//            car = (Car) detailIntent.getSerializableExtra(Intent.EXTRA_TEXT);
//        }
        Car car = (Car) getIntent().getSerializableExtra(Intent.EXTRA_TEXT);
        if (car == null) {
            car = new Car();
        }
        this.car = car;
        txtModel.setText(car.getModel());
        txtYear.setText(String.valueOf(car.getYear()));
        txtDoors.setText(String.valueOf(car.getDoors()));
        txtSeats.setText(String.valueOf(car.getSeats()));
        txtPrice.setText(String.valueOf(car.getDayPrice()));
        showImage(car.getImageUrl());
        Button btnImage = findViewById(R.id.btnImage);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent.createChooser(intent, "Insert Picture"), PICTURE_RESULT);
            }
        });
    }

    private void updateData(){
        spinnerBrand = (Spinner) findViewById(R.id.spinnerBrand);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, carBrands);
        spinnerBrand.setAdapter(adapter);
    }

    private void retrieveJSON() {
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, JSONConfig.urlBrand, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0; i< response.length(); i++)
                            {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String brand = jsonObject.getString("name");
                                carBrands.add(brand);
                            }
                            updateData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_menu:
                saveCar();
                Toast.makeText(this, "Car saved", Toast.LENGTH_LONG).show();
                clean();
                backToMain();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveCar() {
        car.setBrand((String) spinnerBrand.getSelectedItem());
        car.setModel(txtModel.getText().toString());
        car.setType((Type) spinnerType.getSelectedItem());
        car.setYear(Integer.parseInt(txtYear.getText().toString()));
        car.setSeats(Integer.parseInt(txtSeats.getText().toString()));
        car.setDoors(Integer.parseInt(txtDoors.getText().toString()));
        car.setDayPrice(Integer.parseInt(txtPrice.getText().toString()));
        car.setStatus(Status.AVAILABLE);
        if (car.getId() == null) {
            mDatabaseReference.push().setValue(car);
        } else {
            mDatabaseReference.child(car.getId()).setValue(car);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_RESULT && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            StorageReference ref = FirebaseUtil.mStorageRef.child(imageUri.getLastPathSegment());
            ref.putFile(imageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUrl = uri.toString();
                            String imageName = taskSnapshot.getStorage().getPath();
                            car.setImageName(imageName);
                            car.setImageUrl(downloadUrl);
                            showImage(downloadUrl);
                        }
                    });
                }
            });
        }
    }

    private void showImage(String url) {
        if (url != null && !url.isEmpty()) {
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            Picasso.get()
                    .load(url)
                    .resize(width / 2, width * 2 / 3)
                    .centerInside()
                    .into(imageView);
        }
    }

    private void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void clean() {
        txtModel.setText("");
        txtYear.setText("");
        txtDoors.setText("");
        txtSeats.setText("");
        txtPrice.setText("");
    }

}
