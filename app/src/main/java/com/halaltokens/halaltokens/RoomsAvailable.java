package com.halaltokens.halaltokens;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.halaltokens.halaltokens.Runnables.CompSciRunnable;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

public class RoomsAvailable extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> roomsAvailableNow;
    List<String> roomsAvailable1hr;
    List<String> roomsAvailableMoreThan1hr;
    String building;
    String response = null;
    ArrayList<RoomInfo> roomInfoForRoom = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_available);
        Realm realm = Realm.getDefaultInstance();
        //make realmobject class that stores room infos, then use in Where
//        Log.v("Realm",realm.where(ArrayList.class).findAll().toString());


        Intent intent = getIntent();
        building = intent.getStringExtra("building"); //will be useful later

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {

                    try {
                        response = Jsoup.connect("https://halaltokens.firebaseio.com/CompSci.json?print=pretty").ignoreContentType(true).execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    roomInfoForRoom.clear();
                    for (Map.Entry<String, JsonElement> object: jsonObject.entrySet()) {
                        roomInfoForRoom.add(gson.fromJson(object.getValue(), (Type) RoomInfo.class));
                        Log.v("ROOMS", gson.fromJson(object.getValue(), (Type) RoomInfo.class).toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        expListView = findViewById(R.id.expandableListView);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("");
//        ArrayList<RoomInfo> compSciRooms = new ArrayList<>();
//        ValueEventListener changeListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                compSciRooms.clear();
//                for (DataSnapshot child : dataSnapshot.child("CompSci").getChildren()) {
//                    RoomInfo roomInfo = child.getValue(RoomInfo.class);
//                    compSciRooms.add(roomInfo);
//                }
//                Log.v("ROOMS", "ROOMS");
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        ref.addValueEventListener(changeListener);

        expListView.setOnChildClickListener((expandableListView, view, groupPosition, childPosition, id) -> {

            //getting the data clicked on
            TextView tv = view.findViewById(R.id.lblListItem);
            String data = tv.getText().toString();

            //showing an alart dialog for each specific rooms info

//            ref.child("Updater").setValue(LocalDateTime.now());

//            new Connect().doInBackground("");
            AlertDialog alertDialog = Utils.showOkAlertDialog(view.getContext(), data, roomInfoForRoom.toString());
            alertDialog.show();


            return true;
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

class Connect extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... strings) {
        Connection.Response response = null;
        try {
            response = Jsoup.connect("https://docs-examples.firebaseio.com/rest/saving-data/fireblog/posts.json?print=pretty").execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("ROOMS", response.statusMessage());
        return null;
    }
}
