package com.halaltokens.halaltokens.Runnables;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.halaltokens.halaltokens.R;
import com.halaltokens.halaltokens.RoomInfo;
import com.halaltokens.halaltokens.ScraperWorker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ArtsRunnable implements Runnable {

    private static Context context = null;

    public ArtsRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        ArrayList<String> artsArraylist = new ArrayList<String>();
        ArrayList<String> artsArraylist2 = new ArrayList<String>();
        ArrayList<String> artsArraylist3 = new ArrayList<String>();
        ArrayList<String> artsArraylist4 = new ArrayList<String>();
        artsArraylist.add(context.getString(R.string.A_A105));
        artsArraylist.add(context.getString(R.string.A_A106));
        artsArraylist.add(context.getString(R.string.A_A109));
        artsArraylist.add(context.getString(R.string.A_B101));
        artsArraylist.add(context.getString(R.string.A_C214));
        artsArraylist.add(context.getString(R.string.A_F101));
        artsArraylist.add(context.getString(R.string.A_F102));
        artsArraylist2.add(context.getString(R.string.A_F103));
        artsArraylist2.add(context.getString(R.string.A_F103A));
        artsArraylist2.add(context.getString(R.string.A_F104));
        artsArraylist2.add(context.getString(R.string.A_F106));
        artsArraylist2.add(context.getString(R.string.A_F107));
        artsArraylist2.add(context.getString(R.string.A_F308));
        artsArraylist2.add(context.getString(R.string.A_G102));
        artsArraylist2.add(context.getString(R.string.A_G107));
        artsArraylist3.add(context.getString(R.string.A_G108));
        artsArraylist3.add(context.getString(R.string.A_G109));
        artsArraylist3.add(context.getString(R.string.A_J102));
        artsArraylist3.add(context.getString(R.string.A_J104));
        artsArraylist3.add(context.getString(R.string.A_J109));
        artsArraylist3.add(context.getString(R.string.A_J110));
        artsArraylist3.add(context.getString(R.string.A_J112));
        artsArraylist4.add(context.getString(R.string.A_J114));
        artsArraylist4.add(context.getString(R.string.A_NTH1));
        artsArraylist4.add(context.getString(R.string.A_THL));
        artsArraylist4.add(context.getString(R.string.A_THM));
        artsArraylist4.add(context.getString(R.string.A_THP));
        artsArraylist4.add(context.getString(R.string.A_THQ));
        artsArraylist4.add(context.getString(R.string.A_THR));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("");
        threadPoolExecutor.submit(() -> {
            for (String page : artsArraylist) {
                try {
                    Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                    for (int i = 1; i <= 10; i++) {
                        try {
                            RoomInfo roomInfo = new RoomInfo();
                            roomInfo.setRoomName(doc.title().substring(10));
                            roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                            Log.v("ArtsRunnable", roomInfo.toString());
                            ref.child("Arts").push().setValue(roomInfo);
                        } catch (NullPointerException e) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPoolExecutor.submit(() -> {
            for (String page : artsArraylist2) {
                try {
                    Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                    for (int i = 1; i <= 10; i++) {
                        try {
                            RoomInfo roomInfo = new RoomInfo();
                            roomInfo.setRoomName(doc.title().substring(10));
                            roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                            Log.v("ArtsRunnable", roomInfo.toString());
                            ref.child("Arts").push().setValue(roomInfo);
                        } catch (NullPointerException e) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPoolExecutor.submit(() -> {
            for (String page : artsArraylist3) {
                try {
                    Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                    for (int i = 1; i <= 10; i++) {
                        try {
                            RoomInfo roomInfo = new RoomInfo();
                            roomInfo.setRoomName(doc.title().substring(10));
                            roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                            Log.v("ArtsRunnable", roomInfo.toString());
                            ref.child("Arts").push().setValue(roomInfo);
                        } catch (NullPointerException e) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPoolExecutor.submit(() -> {
            for (String page : artsArraylist4) {
                try {
                    Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                    for (int i = 1; i <= 10; i++) {
                        try {
                            RoomInfo roomInfo = new RoomInfo();
                            roomInfo.setRoomName(doc.title().substring(10));
                            roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                            Log.v("ArtsRunnable", roomInfo.toString());
                            ref.child("Arts").push().setValue(roomInfo);
                        } catch (NullPointerException e) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPoolExecutor.shutdown();
    }
}
