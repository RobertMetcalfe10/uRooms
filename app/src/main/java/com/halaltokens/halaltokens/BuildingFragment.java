package com.halaltokens.halaltokens;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class BuildingFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static BuildingFragment fragment;
    private OnItemClickListener callback;

    public interface OnItemClickListener {
        public void onBuildingClicked(String building);
    }

    public BuildingFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BuildingFragment newInstance(int sectionNumber) {
        fragment = new BuildingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (OnItemClickListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //creating an arraylist which contains available rooms
        final ArrayList<String> listOfBuildings = new ArrayList<>();
        listOfBuildings.add("CompSci");
        listOfBuildings.add("Arts");
        listOfBuildings.add("Eng");
        listOfBuildings.add("HealthScience");
        listOfBuildings.add("SciEast");
        listOfBuildings.add("SciHub");
        listOfBuildings.add("SciNorth");
        listOfBuildings.add("SciSouth");
        listOfBuildings.add("SciWest");

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1, listOfBuildings);

        //listview to show the buildings
        ListView lv = rootView.findViewById(R.id.listView);
        lv.setAdapter(itemsAdapter);

        lv.setOnItemClickListener((adapterView, view, i, l) -> callback.onBuildingClicked((String) adapterView.getItemAtPosition(i)));

        return rootView;
    }
}
