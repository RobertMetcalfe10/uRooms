package com.halaltokens.halaltokens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class BuildingFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static BuildingFragment fragment;
    private static OnItemClickListener callback;

    private RecyclerView recyclerView;
    private CardViewAdapter cAdapter;
    private ArrayList<String> listOfBuildings;

    public interface OnItemClickListener {
        void onBuildingClicked(String building);
    }

    public BuildingFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BuildingFragment newInstance(OnItemClickListener onBuildingClicked) {
        fragment = new BuildingFragment();
        callback = onBuildingClicked;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //initialize recyclerview
        recyclerView = rootView.findViewById(R.id.recycler_view);

        //initialize Adapterclass with List
        cAdapter = new CardViewAdapter(prepareData(), callback, getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter); //add adapter to recycler view

        recyclerView.setOnClickListener(view -> {
            int itemPosition = recyclerView.getChildAdapterPosition(view);
            String item = listOfBuildings.get(itemPosition);
            callback.onBuildingClicked(item);
        });

        return rootView;
    }

    public List<String> prepareData(){
        //creating an arraylist which contains available rooms
        listOfBuildings = new ArrayList<>();
        listOfBuildings.add("HealthScience");
        listOfBuildings.add("Arts");
        listOfBuildings.add("SciEast");
        listOfBuildings.add("SciHub");
        listOfBuildings.add("SciNorth");
        listOfBuildings.add("SciSouth");
        listOfBuildings.add("SciWest");
        listOfBuildings.add("CompSci");
        listOfBuildings.add("Eng");

        return listOfBuildings;
    }
}
