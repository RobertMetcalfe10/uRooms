package com.halaltokens.halaltokens;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String str = "http://www.ucd.ie";
//        Object[] objects = new Object[1];
        DoSomeTask doSomeTask = new DoSomeTask();
        doSomeTask.execute();
    }

    class DoSomeTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {

            Map<String, String> map = new HashMap<>();
            map.put("p_butn", "1");
            map.put("p_username", "15551647");
            map.put("p_password", "Destiny10");
            map.put("p_forward", "W_WEB_WELCOME_PAGE¬");
            map.put("p_lmet", "SISWEB");

            try {
                Jsoup.connect("https://sisweb.ucd.ie/usis/W_HU_LOGIN.P_PROC_LOGINBUT")
                        .method(Connection.Method.POST)
                        .data(map)
                        .post();
            } catch(IOException e) {
                e.printStackTrace();
            }
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            //Comp Sci
            threadPoolExecutor.execute(() -> {
                try {
                    Document doc = Jsoup.connect(getString(R.string.B004CSI)).get();
                    for(int i=1; i<=10;i++) {
                        try {
                            RoomInfo roomInfo = new RoomInfo();
                            roomInfo.setRoomName(doc.title().substring(10));
                            roomInfo.setRoomInfo(doc.getElementById("RB200|0."+i).children());
                            Log.v("Comp",roomInfo.toString());
                        } catch (NullPointerException e) {
                            break;
                        }
                }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            });
            //AG
            threadPoolExecutor.execute(() -> {
                    ArrayList<String> agArrayList = new ArrayList<String>();
                    agArrayList.add(getString(R.string.AG1_01));
                    agArrayList.add(getString(R.string.AG1_16));
                    agArrayList.add(getString(R.string.AG1_18));
                    agArrayList.add(getString(R.string.AG1_19));
                    agArrayList.add(getString(R.string.AG1_47));
                    agArrayList.add(getString(R.string.AG3_26));
                    agArrayList.add(getString(R.string.AG_FS_G01));
                    agArrayList.add(getString(R.string.AG_G_06));
                    agArrayList.add(getString(R.string.AG_G_07));
                    agArrayList.add(getString(R.string.AG_G_08));
                    agArrayList.add(getString(R.string.AG_G_09));
                    agArrayList.add(getString(R.string.AG_G_15));
                    agArrayList.add(getString(R.string.AG_G_24));
                    agArrayList.add(getString(R.string.AG_G_30_PCLAB));
                    agArrayList.add(getString(R.string.AG_LG_08A));
                    agArrayList.add(getString(R.string.AG_LG_08B));
                    agArrayList.add(getString(R.string.AG_LG_20));
                    for (String page : agArrayList) {
                        try {
                        Document doc = Jsoup.connect(page).get();
                        for (int i = 1; i <= 10; i++) {
                            try {
                                RoomInfo roomInfo = new RoomInfo();
                                roomInfo.setRoomName(doc.title().substring(10));
                                roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                                Log.v("AG", roomInfo.toString());
                            } catch (NullPointerException e) {
                                break;
                            }
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });
            //science east
            threadPoolExecutor.execute(() -> {
                ArrayList<String> sciEastArrayList = new ArrayList<String>();
                sciEastArrayList.add(getString(R.string.E0_01));
                sciEastArrayList.add(getString(R.string.E0_08));
                sciEastArrayList.add(getString(R.string.E1_17));
                sciEastArrayList.add(getString(R.string.E1_19));
                sciEastArrayList.add(getString(R.string.E2_16));
                sciEastArrayList.add(getString(R.string.E2_18));
                for (String page : sciEastArrayList) {
                    try {
                        Document doc = Jsoup.connect(page).get();
                        for (int i = 1; i <= 10; i++) {
                            try {
                                RoomInfo roomInfo = new RoomInfo();
                                roomInfo.setRoomName(doc.title().substring(10));
                                roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                                Log.v("SciEast", roomInfo.toString());
                            } catch (NullPointerException e) {
                                break;
                            }
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });

            //science hub
            threadPoolExecutor.execute(() -> {
                ArrayList<String> sciHubArraylist = new ArrayList<String>();
                sciHubArraylist.add(getString(R.string.H0_12));
                sciHubArraylist.add(getString(R.string.H1_12));
                sciHubArraylist.add(getString(R.string.H1_13));
                sciHubArraylist.add(getString(R.string.H1_26));
                sciHubArraylist.add(getString(R.string.H1_37));
                sciHubArraylist.add(getString(R.string.H1_49));
                sciHubArraylist.add(getString(R.string.H1_51));
                sciHubArraylist.add(getString(R.string.H2_12));
                sciHubArraylist.add(getString(R.string.H2_18));
                sciHubArraylist.add(getString(R.string.H2_20));
                sciHubArraylist.add(getString(R.string.H2_21));
                sciHubArraylist.add(getString(R.string.H2_22));
                sciHubArraylist.add(getString(R.string.H2_32));
                sciHubArraylist.add(getString(R.string.H2_38));
                sciHubArraylist.add(getString(R.string.H2_40));
                for (String page : sciHubArraylist) {
                    try {
                        Document doc = Jsoup.connect(page).get();
                        for (int i = 1; i <= 10; i++) {
                            try {
                                RoomInfo roomInfo = new RoomInfo();
                                roomInfo.setRoomName(doc.title().substring(10));
                                roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                                Log.v("SciHub", roomInfo.toString());
                            } catch (NullPointerException e) {
                                break;
                            }
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });

            //science north
            threadPoolExecutor.execute(() -> {
                ArrayList<String> sciNorthArraylist = new ArrayList<String>();
                sciNorthArraylist.add(getString(R.string.N129));
                sciNorthArraylist.add(getString(R.string.N231));
                sciNorthArraylist.add(getString(R.string.N232));
                for (String page : sciNorthArraylist) {
                    try {
                        Document doc = Jsoup.connect(page).get();
                        for (int i = 1; i <= 10; i++) {
                            try {
                                RoomInfo roomInfo = new RoomInfo();
                                roomInfo.setRoomName(doc.title().substring(10));
                                roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                                Log.v("SciNorth", roomInfo.toString());
                            } catch (NullPointerException e) {
                                break;
                            }
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });

            //science south
            threadPoolExecutor.execute(() -> {
                ArrayList<String> sciSouthArraylist = new ArrayList<String>();
                sciSouthArraylist.add(getString(R.string.S1_67));
                sciSouthArraylist.add(getString(R.string.S3_56));
                for (String page : sciSouthArraylist) {
                    try {
                        Document doc = Jsoup.connect(page).get();
                        for (int i = 1; i <= 10; i++) {
                            try {
                                RoomInfo roomInfo = new RoomInfo();
                                roomInfo.setRoomName(doc.title().substring(10));
                                roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                                Log.v("SciSouth", roomInfo.toString());
                            } catch (NullPointerException e) {
                                break;
                            }
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });

            //science west
            threadPoolExecutor.execute(() -> {
                ArrayList<String> sciWestArraylist = new ArrayList<String>();
                sciWestArraylist.add(getString(R.string.B101A));
                sciWestArraylist.add(getString(R.string.B101B));
                sciWestArraylist.add(getString(R.string.B154A));
                sciWestArraylist.add(getString(R.string.B154B));
                for (String page : sciWestArraylist) {
                    try {
                        Document doc = Jsoup.connect(page).get();
                        for (int i = 1; i <= 10; i++) {
                            try {
                                RoomInfo roomInfo = new RoomInfo();
                                roomInfo.setRoomName(doc.title().substring(10));
                                roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                                Log.v("SciWest", roomInfo.toString());
                            } catch (NullPointerException e) {
                                break;
                            }
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });

            //health science
            threadPoolExecutor.execute(() -> {
                ArrayList<String> healthScienceArraylist = new ArrayList<String>();
                healthScienceArraylist.add(getString(R.string.A003));
                healthScienceArraylist.add(getString(R.string.A004));
                healthScienceArraylist.add(getString(R.string.A005));
                healthScienceArraylist.add(getString(R.string.A006));
                healthScienceArraylist.add(getString(R.string.A007));
                healthScienceArraylist.add(getString(R.string.A008));
                healthScienceArraylist.add(getString(R.string.B004));
                healthScienceArraylist.add(getString(R.string.B005));
                healthScienceArraylist.add(getString(R.string.B006));
                healthScienceArraylist.add(getString(R.string.C004));
                healthScienceArraylist.add(getString(R.string.C005));
                healthScienceArraylist.add(getString(R.string.C006));
                healthScienceArraylist.add(getString(R.string.C007));
                healthScienceArraylist.add(getString(R.string.C112));
                healthScienceArraylist.add(getString(R.string.C113));
                healthScienceArraylist.add(getString(R.string.C114));
                healthScienceArraylist.add(getString(R.string.C115));
                healthScienceArraylist.add(getString(R.string.C116));
                healthScienceArraylist.add(getString(R.string.C117));
                healthScienceArraylist.add(getString(R.string.C118));
                healthScienceArraylist.add(getString(R.string.C120));
                healthScienceArraylist.add(getString(R.string.C122));
                healthScienceArraylist.add(getString(R.string.C311));
                healthScienceArraylist.add(getString(R.string.C314));
                healthScienceArraylist.add(getString(R.string.D114));
                healthScienceArraylist.add(getString(R.string.D115));
                healthScienceArraylist.add(getString(R.string.B227));
                healthScienceArraylist.add(getString(R.string.B237));
                healthScienceArraylist.add(getString(R.string.B311));
                healthScienceArraylist.add(getString(R.string.B333));
                healthScienceArraylist.add(getString(R.string.B341));
                healthScienceArraylist.add(getString(R.string.B342));
                healthScienceArraylist.add(getString(R.string.B109));
                for (String page : healthScienceArraylist) {
                    try {
                        Document doc = Jsoup.connect(page).get();
                        for (int i = 1; i <= 10; i++) {
                            try {
                                RoomInfo roomInfo = new RoomInfo();
                                roomInfo.setRoomName(doc.title().substring(10));
                                roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                                Log.v("HealthScience", roomInfo.toString());
                            } catch (NullPointerException e) {
                                break;
                            }
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });

            //health science
            threadPoolExecutor.execute(() -> {
                ArrayList<String> engArraylist = new ArrayList<String>();
                engArraylist.add(getString(R.string.E016));
                engArraylist.add(getString(R.string.E121));
                engArraylist.add(getString(R.string.E135));
                engArraylist.add(getString(R.string.E216));
                engArraylist.add(getString(R.string.E234));
                engArraylist.add(getString(R.string.E317));
                engArraylist.add(getString(R.string.E326));
                for (String page : engArraylist) {
                    try {
                        Document doc = Jsoup.connect(page).get();
                        for (int i = 1; i <= 10; i++) {
                            try {
                                RoomInfo roomInfo = new RoomInfo();
                                roomInfo.setRoomName(doc.title().substring(10));
                                roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                                Log.v("Eng", roomInfo.toString());
                            } catch (NullPointerException e) {
                                break;
                            }
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });


            return null;
        }
    }
}
