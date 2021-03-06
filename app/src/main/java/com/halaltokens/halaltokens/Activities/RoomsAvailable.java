/*
 * Rooms available displays the available rooms based on data pulled from firebase
 */


package com.halaltokens.halaltokens.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.halaltokens.halaltokens.Adapters.ExpandableListAdapter;
import com.halaltokens.halaltokens.Helpers.RoomInfo;
import com.halaltokens.halaltokens.Helpers.RoomInfoRealmList;
import com.halaltokens.halaltokens.R;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.realm.Realm;
import io.realm.RealmResults;


public class RoomsAvailable extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<String>> listDataChild = new HashMap<>();
    List<String> roomsAvailableNow = new ArrayList<>();
    List<String> roomsAvailableNowIn1hr = new ArrayList<>();
    String building;
    String response = null;
    Map<String, ArrayList<RoomInfo>> roomsMap = new ConcurrentHashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_available);


        Intent intent = getIntent();
        building = intent.getStringExtra("Building");
        Log.v("Building", building);

        // Async Task to pull information from firebase and separate it into rooms available now and in an hour
        @SuppressLint("StaticFieldLeak") AsyncTask<Context, String, String> asyncTask = new AsyncTask<Context, String, String>() {

            @Override
            protected String doInBackground(Context... contexts) {
                try {

                    try {
                        response = Jsoup.connect("https://halaltokens.firebaseio.com/" + building + ".json?print=pretty").ignoreContentType(true).execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    roomsMap.clear();
                    ArrayList<RoomInfo> roomInfoArrayList = new ArrayList<>();
                    String roomName = "";
                    for (Map.Entry<String, JsonElement> object : jsonObject.entrySet()) {
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
                    roomsMap.put(roomName.trim(), roomInfoArrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(String str) {

                for (Map.Entry<String, ArrayList<RoomInfo>> roomInfos : roomsMap.entrySet()) {
                    boolean checked = false;
                    boolean checked1 = false;
                    for (RoomInfo roomInfo : roomInfos.getValue()) {
                        if (Integer.parseInt(roomInfo.getStartTime().substring(0, 2)) == Integer.parseInt(roomInfo.getEndTime().substring(0, 2)) - 1) {
                            if (Integer.parseInt(roomInfo.getStartTime().substring(0, 2)) == LocalDateTime.now().getHour() || Integer.parseInt(roomInfo.getEndTime().substring(0, 2)) == LocalDateTime.now().getHour()) {
                                checked = true;
                                continue;
                            }
                        }
                        if (roomInfo.getStartTime().substring(0, 2).equals(roomInfo.getEndTime().substring(0, 2))) {
                            if (Integer.parseInt(roomInfo.getStartTime().substring(0, 2)) == LocalDateTime.now().getHour()) {
                                checked = true;
                            }
                        }
                    }
                    if (!checked) {
                        roomsAvailableNow.add(roomInfos.getKey());
                    }
                    for (RoomInfo roomInfo : roomInfos.getValue()) {
                        if (Integer.parseInt(roomInfo.getStartTime().substring(0, 2)) == LocalDateTime.now().getHour() + 1) {
                            checked1 = true;
                        }
                    }
                    if (!checked1) {
                        roomsAvailableNowIn1hr.add(roomInfos.getKey());
                    }

                }
                prepareListData();

                listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild);
                // setting list adapter
                expListView.setAdapter(listAdapter);
                try {
                    Thread.sleep(400);
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        asyncTask.execute(this);

        expListView = findViewById(R.id.expandableListView);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener((expandableListView, view, i, l) -> {

            if(expandableListView.getExpandableListAdapter().getChildrenCount(0) == 0){
                Toast.makeText(expandableListView.getContext(), "No Rooms Available", Toast.LENGTH_SHORT).show();
            }


            return false;
        });

        // adds a room to favourites in Realm or unfavourites
        expListView.setOnChildClickListener((expandableListView, view, groupPosition, childPosition, id) -> {
            TextView tv = view.findViewById(R.id.lblListItem);
            ImageView iv = view.findViewById(R.id.lblListImage);
            String data = tv.getText().toString();
            if (iv.getTag().equals("notFav")) {
                iv.setImageResource(R.drawable.ic_favorite_black_24dp);
                iv.setTag("Fav");
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                RoomInfoRealmList roomInfoRealmList = new RoomInfoRealmList();
                roomInfoRealmList.addList(roomsMap.get(data));
                realm.copyToRealmOrUpdate(roomInfoRealmList);
                realm.commitTransaction();
            } else {
                iv.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                iv.setTag("notFav");
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                RealmResults<RoomInfoRealmList> result = realm.where(RoomInfoRealmList.class).findAll();
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getRoomInfo(0).getRoomName().trim().equals(tv.getText().toString().trim())) {
                        result.deleteFromRealm(i);
                    }
                }
                realm.commitTransaction();
            }

            return true;
        });

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        listDataHeader.add("Rooms Available now");
        listDataHeader.add("Rooms Available in an hour");

        listDataChild.put(listDataHeader.get(0), roomsAvailableNow); // Header, Child data
        listDataChild.put(listDataHeader.get(1), roomsAvailableNowIn1hr);
    }
}