package com.halaltokens.halaltokens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomsAvailable extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> roomsAvailableNow;
    List<String> roomsAvailable1hr;
    List<String> roomsAvailableMoreThan1hr;
    String building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_available);

        Intent intent = getIntent();
        building = intent.getStringExtra("building"); //will be useful later

        expListView = findViewById(R.id.expandableListView);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {

                //getting the data clicked on
                TextView tv = view.findViewById(R.id.lblListItem);
                String data = tv.getText().toString();

                //showing an alart dialog for each specific rooms info
                Utils.showOkAlertDialog(view.getContext(), data, "ROOM INFO");

                return true;
            }

        });

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        roomsAvailableNow = new ArrayList<>();
        roomsAvailable1hr = new ArrayList<>();
        roomsAvailableMoreThan1hr = new ArrayList<>();

        // Adding child data
        listDataHeader.add("Rooms Available now in");
        listDataHeader.add("Rooms Avaiable in 1hr");
        listDataHeader.add("Rooms Avaiable in > 1h");

        // Adding child data
        roomsAvailableNow.add("Room 1");
        roomsAvailableNow.add("Room 2");
        roomsAvailableNow.add("Room 3");
        roomsAvailableNow.add("Room 4");
        roomsAvailableNow.add("Room 5");

        //rooms available in 1 hr
        roomsAvailable1hr.add("Room 1");
        roomsAvailable1hr.add("Room 2");
        roomsAvailable1hr.add("Room 3");
        roomsAvailable1hr.add("Room 4");
        roomsAvailable1hr.add("Room 5");

        //rooms available in > 1hr
        roomsAvailableMoreThan1hr.add("Room 1");
        roomsAvailableMoreThan1hr.add("Room 2");
        roomsAvailableMoreThan1hr.add("Room 3");
        roomsAvailableMoreThan1hr.add("Room 4");
        roomsAvailableMoreThan1hr.add("Room 5");

        listDataChild.put(listDataHeader.get(0), roomsAvailableNow); // Header, Child data
        listDataChild.put(listDataHeader.get(1), roomsAvailable1hr);
        listDataChild.put(listDataHeader.get(2), roomsAvailableMoreThan1hr);
    }
}
