/*
 * A fragment that displays the list of buildings to display to the user
 */

package com.halaltokens.halaltokens.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.halaltokens.halaltokens.Adapters.CardViewAdapter;
import com.halaltokens.halaltokens.R;

import java.util.ArrayList;
import java.util.List;

public class BuildingFragment extends Fragment {
    private static OnItemClickListener callback;

    private RecyclerView recyclerView;
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
        /*
      The fragment argument representing the section number for this
      fragment.
     */
        BuildingFragment fragment = new BuildingFragment();
        callback = onBuildingClicked;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //initialize recyclerview
        recyclerView = rootView.findViewById(R.id.recycler_view);

        //initialize Adapterclass with List
        CardViewAdapter cAdapter = new CardViewAdapter(prepareData(), callback, getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter); //add adapter to recycler view

        recyclerView.setOnClickListener(view -> {
            int itemPosition = recyclerView.getChildAdapterPosition(view);
            String item = listOfBuildings.get(itemPosition);
            callback.onBuildingClicked(item);
        });

        return rootView;
    }

    public List<String> prepareData() {
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
