package be.pxl.mobdev.views;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import be.pxl.mobdev.R;
import be.pxl.mobdev.activities.CarActivity;
import be.pxl.mobdev.activities.DetailActivity;
import be.pxl.mobdev.models.Car;
import be.pxl.mobdev.util.FirebaseUtil;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    public ArrayList<Car> favCars;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    private ImageView imageCar;
    public static final String CAR = "car";

    public CarAdapter(){
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        this.favCars = new ArrayList<Car>();
        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Car car = snapshot.getValue(Car.class);
                assert car != null;
                car.setId(snapshot.getKey());
                favCars.add(car);
                notifyItemInserted(favCars.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildListener);
    }

    @NonNull
    @Override
    public CarAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.rv_carfav, parent, false);
        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Car car = favCars.get(position);
        holder.bind(car);
    }

    @Override
    public int getItemCount() {
        return favCars.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public ArrayList<Car> getFavCars(){
        return favCars;
    }


    public class CarViewHolder extends RecyclerView.ViewHolder{

        TextView cBrand;
        TextView cModel;
        TextView cYear;
        TextView cDoors;
        TextView cSeats;
        TextView cPrice;
        Button btnDetail;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            cBrand = (TextView) itemView.findViewById(R.id.cCarTitle);
            cModel = (TextView) itemView.findViewById(R.id.cCarModel);
            cYear = (TextView) itemView.findViewById(R.id.cCarYear);
            cDoors = (TextView) itemView.findViewById(R.id.cCarDoors);
            cSeats = (TextView) itemView.findViewById(R.id.cCarSeats);
            cPrice = (TextView) itemView.findViewById(R.id.cCarPrice);
            imageCar = (ImageView) itemView.findViewById(R.id.cCarImage);
            btnDetail = (Button) itemView.findViewById(R.id.cBtnDetail);
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Car selectedCar = favCars.get(position);
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, selectedCar);
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void bind(Car car){
            cBrand.setText(car.getBrand());
            cModel.setText(car.getModel());
            cYear.setText(String.valueOf(car.getYear()));
            cDoors.setText(String.valueOf(car.getDoors()));
            cSeats.setText(String.valueOf(car.getSeats()));
            cPrice.setText(String.valueOf(car.getDayPrice()));
            showImage(car.getImageUrl());
        }

        private void showImage(String url) {
            if (url != null && !url.isEmpty()){
                Picasso.get()
                        .load(url)
                        .resize(200, 130)
                        .centerInside()
                        .into(imageCar);
            }
        }
    }
}
