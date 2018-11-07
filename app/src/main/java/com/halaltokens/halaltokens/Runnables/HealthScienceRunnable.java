package com.halaltokens.halaltokens.Runnables;

import android.content.Context;
import android.util.Log;

import com.halaltokens.halaltokens.R;
import com.halaltokens.halaltokens.RoomInfo;
import com.halaltokens.halaltokens.ScraperWorker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HealthScienceRunnable implements Runnable {

    private static Context context = null;

    public HealthScienceRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        ArrayList<String> healthScienceArraylist = new ArrayList<String>();
        ArrayList<String> healthScienceArraylist2 = new ArrayList<String>();
        ArrayList<String> healthScienceArraylist3 = new ArrayList<String>();
        ArrayList<String> healthScienceArraylist4 = new ArrayList<String>();
        healthScienceArraylist.add(context.getString(R.string.A003));
        healthScienceArraylist.add(context.getString(R.string.A004));
        healthScienceArraylist.add(context.getString(R.string.A005));
        healthScienceArraylist.add(context.getString(R.string.A006));
        healthScienceArraylist.add(context.getString(R.string.A007));
        healthScienceArraylist.add(context.getString(R.string.A008));
        healthScienceArraylist.add(context.getString(R.string.B004));
        healthScienceArraylist2.add(context.getString(R.string.B005));
        healthScienceArraylist2.add(context.getString(R.string.B006));
        healthScienceArraylist2.add(context.getString(R.string.C004));
        healthScienceArraylist2.add(context.getString(R.string.C005));
        healthScienceArraylist2.add(context.getString(R.string.C006));
        healthScienceArraylist2.add(context.getString(R.string.C007));
        healthScienceArraylist2.add(context.getString(R.string.C112));
        healthScienceArraylist2.add(context.getString(R.string.C113));
        healthScienceArraylist3.add(context.getString(R.string.C114));
        healthScienceArraylist3.add(context.getString(R.string.C115));
        healthScienceArraylist3.add(context.getString(R.string.C116));
        healthScienceArraylist3.add(context.getString(R.string.C117));
        healthScienceArraylist3.add(context.getString(R.string.C118));
        healthScienceArraylist3.add(context.getString(R.string.C120));
        healthScienceArraylist3.add(context.getString(R.string.C122));
        healthScienceArraylist3.add(context.getString(R.string.C311));
        healthScienceArraylist4.add(context.getString(R.string.C314));
        healthScienceArraylist4.add(context.getString(R.string.D114));
        healthScienceArraylist4.add(context.getString(R.string.D115));
        healthScienceArraylist4.add(context.getString(R.string.B227));
        healthScienceArraylist4.add(context.getString(R.string.B237));
        healthScienceArraylist4.add(context.getString(R.string.B311));
        healthScienceArraylist4.add(context.getString(R.string.B333));
        healthScienceArraylist4.add(context.getString(R.string.B341));
        healthScienceArraylist4.add(context.getString(R.string.B342));
        healthScienceArraylist4.add(context.getString(R.string.B109));

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        threadPoolExecutor.submit(() -> {
            for (String page : healthScienceArraylist) {
                try {
                    Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                    for (int i = 1; i <= 10; i++) {
                        try {
                            RoomInfo roomInfo = new RoomInfo();
                            roomInfo.setRoomName(doc.title().substring(10));
                            roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                            Log.v("HealthScienceRunnable", roomInfo.toString());
                        } catch (NullPointerException e) {
                            break;
                        }
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

        threadPoolExecutor.submit(() -> {
            for (String page : healthScienceArraylist2) {
                try {
                    Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                    for (int i = 1; i <= 10; i++) {
                        try {
                            RoomInfo roomInfo = new RoomInfo();
                            roomInfo.setRoomName(doc.title().substring(10));
                            roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                            Log.v("HealthScienceRunnable", roomInfo.toString());
                        } catch (NullPointerException e) {
                            break;
                        }
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

        threadPoolExecutor.submit(() -> {
            for (String page : healthScienceArraylist3) {
                try {
                    Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                    for (int i = 1; i <= 10; i++) {
                        try {
                            RoomInfo roomInfo = new RoomInfo();
                            roomInfo.setRoomName(doc.title().substring(10));
                            roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                            Log.v("HealthScienceRunnable", roomInfo.toString());
                        } catch (NullPointerException e) {
                            break;
                        }
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

        threadPoolExecutor.submit(() -> {
            for (String page : healthScienceArraylist4) {
                try {
                    Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                    for (int i = 1; i <= 10; i++) {
                        try {
                            RoomInfo roomInfo = new RoomInfo();
                            roomInfo.setRoomName(doc.title().substring(10));
                            roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                            Log.v("HealthScienceRunnable", roomInfo.toString());
                        } catch (NullPointerException e) {
                            break;
                        }
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

    }
}
