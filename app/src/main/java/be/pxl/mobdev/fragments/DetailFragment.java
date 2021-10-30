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

import be.pxl.mobdev.R;

public class DetailFragment extends Fragment {
    private TextView txtBrand;
    private TextView txtModel;
    private TextView txtYear;
    private TextView txtDoors;
    private TextView txtSeats;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_short, container, false);

        Bundle bundle = getArguments();
        txtBrand = (TextView) view.findViewById(R.id.fragmentCarPetrol);
        String item = "";

        if(bundle != null){
            item = getArguments().getString("item");
        }

        txtBrand.setText(item);


        return view;
    }


}
