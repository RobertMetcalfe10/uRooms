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
                            Log.v("Comp",roomInfo.getOrganization());
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
                                Log.v("AG", roomInfo.getOrganization());
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

            return null;
        }
    }
}
