package com.halaltokens.halaltokens;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RoomsAvailable extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader = new ArrayList<>();;
    HashMap<String, List<String>> listDataChild = new HashMap<>();
    List<String> roomsAvailableNow = new ArrayList<>();
    List<String> roomsAvailable1hr = new ArrayList<>();
    List<String> roomsAvailableMoreThan1hr = new ArrayList<>();
    String building;
    String response = null;
    Map<String,ArrayList<RoomInfo>> roomsMap = new ConcurrentHashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_available);

        Intent intent = getIntent();
        building = intent.getStringExtra("Building");
        Log.v("Building", building);

        @SuppressLint("StaticFieldLeak") AsyncTask<Context,String,String> asyncTask = new AsyncTask<Context, String, String>() {

            @Override
            protected String doInBackground(Context... contexts) {
                try  {

                    try {
                        response = Jsoup.connect("https://halaltokens.firebaseio.com/"+building+".json?print=pretty").ignoreContentType(true).execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    roomsMap.clear();
                    ArrayList<RoomInfo> roomInfoArrayList= new ArrayList<>();
                    String roomName = "";
                    for (Map.Entry<String, JsonElement> object: jsonObject.entrySet()) {
                        RoomInfo roomInfo = gson.fromJson(object.getValue(), (Type) RoomInfo.class);
                        if (roomName.equals("")) {
                            roomName = roomInfo.getRoomName();
                            roomInfoArrayList.add(roomInfo);
                            continue;
                        }
                        if (!roomInfo.getRoomName().equals(roomName)) {
                            roomsMap.put(roomName.trim(), (ArrayList<RoomInfo>) roomInfoArrayList.clone());
                            roomInfoArrayList.clear();
                            roomName = roomInfo.getRoomName();
                            roomInfoArrayList.add(roomInfo);
                        } else {
                            roomInfoArrayList.add(roomInfo);
                        }
                    }
                    roomsMap.put(roomName.trim(),roomInfoArrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(String str) {
                prepareListData();

                listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild);
                // setting list adapter
                expListView.setAdapter(listAdapter);
            }
        };
        asyncTask.execute(this);

        expListView = findViewById(R.id.expandableListView);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);


        expListView.setOnChildClickListener((expandableListView, view, groupPosition, childPosition, id) -> {

            //getting the data clicked on
            TextView tv = view.findViewById(R.id.lblListItem);
            String data = tv.getText().toString();

            AlertDialog alertDialog = Utils.showOkAlertDialog(RoomsAvailable.this, data, roomsMap.get(data).toString());
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
        listDataHeader.add("Rooms Available now");
        listDataHeader.add("Rooms Available in 1hr");
        listDataHeader.add("Rooms Available in > 1h");

        // Adding child data
        roomsAvailableNow.addAll(roomsMap.keySet());

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