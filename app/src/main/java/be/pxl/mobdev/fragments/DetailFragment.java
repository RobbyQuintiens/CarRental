package be.pxl.mobdev.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import be.pxl.mobdev.R;

public class DetailFragment extends Fragment {
    private View mDetailFragmentView;
    private RecyclerView mRvCars;

    public DetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_short, container, false);

        Bundle bundle = getArguments();
        TextView fuelText = (TextView) view.findViewById(R.id.fragmentCarPetrolInput);
        TextView doorsText = (TextView) view.findViewById(R.id.fragmentCarDoorsInput);
        TextView seatsText = (TextView) view.findViewById(R.id.fragmentCarSeatsInput);
        TextView typeText = (TextView) view.findViewById(R.id.fragmentCarTypeInput);
        TextView priceText = (TextView) view.findViewById(R.id.fragmentCarPrice);
        TextView brandText = (TextView) view.findViewById(R.id.fragmentCarBrand);
        TextView modelText = (TextView) view.findViewById(R.id.fragmentCarModel);
        String fuel = "";
        String doors = "";
        String price = "";
        String seats = "";
        String type = "";
        String brand = "";
        String model = "";

        if (bundle != null) {
            assert getArguments() != null;
            fuel = getArguments().getString("fuel");
            doors = getArguments().getString("doors");
            seats = getArguments().getString("seats");
            price = getArguments().getString("price");
            type = getArguments().getString("type");
            brand = getArguments().getString("brand");
            model = getArguments().getString("model");
        }

        fuelText.setText(fuel);
        doorsText.setText(doors);
        seatsText.setText(seats);
        typeText.setText(type);
        priceText.setText(price);
        brandText.setText(brand);
        modelText.setText(model);

        return view;
    }

}
