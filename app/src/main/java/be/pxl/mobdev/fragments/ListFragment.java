package be.pxl.mobdev.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import be.pxl.mobdev.R;
import be.pxl.mobdev.activities.DetailActivity;
import be.pxl.mobdev.models.Car;
import be.pxl.mobdev.views.CarAdapter;

public class ListFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    ArrayList<Car> mCarArrayList;
    RecyclerView carView;
    CarAdapter mAdapter;
    Car car;
    Spinner sortSpinner;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        sortSpinner = (Spinner) view.findViewById(R.id.spinnerSort);
        carView = view.findViewById(R.id.rvAllCars);
        mAdapter = new CarAdapter(getContext(), false, this);
        mCarArrayList = mAdapter.getFavCars();
        setupSort(mCarArrayList, mAdapter);
        carView.setAdapter(mAdapter);
        LinearLayoutManager carsLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        carView.setLayoutManager(carsLayoutManager);
        return view;
    }


    private void setupSort(ArrayList<Car> carArrayList, CarAdapter carAdapter) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_types, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        sortSpinner.setAdapter(adapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Collections.sort(carArrayList, Car.CarBrandAZComparator);
                    adapter.notifyDataSetChanged();
                    carAdapter.notifyDataSetChanged();
                } else if (i == 1) {
                    Collections.sort(carArrayList, Car.CarBrandZAComparator);
                    adapter.notifyDataSetChanged();
                    carAdapter.notifyDataSetChanged();
                } else if (i == 2) {
                    Collections.sort(carArrayList, Car.CarBrandPriceAscComparator);
                    adapter.notifyDataSetChanged();
                    carAdapter.notifyDataSetChanged();
                } else if (i == 3) {
                    Collections.sort(carArrayList, Car.CarBrandPriceDescComparator);
                    adapter.notifyDataSetChanged();
                    carAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        car = mCarArrayList.get(i);
        Toast.makeText(getActivity(), car.getModel(), Toast.LENGTH_LONG).show();
        DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.fragmentDetail);
        if (detailFragment != null && detailFragment.isVisible()) {
            // Visible: send bundle
            DetailFragment newFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("fuel", car.getBrandstof().toString());
            bundle.putString("price", String.valueOf(car.getDayPrice()));
            bundle.putString("doors", String.valueOf(car.getDoors()));
            bundle.putString("seats", String.valueOf(car.getSeats()));
            bundle.putString("type", car.getType().toString());
            newFragment.setArguments(bundle);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(detailFragment.getId(), newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        } else {
            toDetailIntent(view);
        }
    }

    private void toDetailIntent(View view){
        Intent intent = new Intent(view.getContext(), DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, car);
        view.getContext().startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
