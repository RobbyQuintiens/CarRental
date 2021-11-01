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

    public DetailFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_short, container, false);

        Bundle bundle = getArguments();
        TextView fuelText =(TextView) view.findViewById(R.id.fragmentCarPetrolInput);
        String fuel = "";

        if(bundle != null){
            assert getArguments() != null;
            fuel = getArguments().getString("item");
        }

        fuelText.setText(fuel);

        return view;
    }

}
