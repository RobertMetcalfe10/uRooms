package com.halaltokens.halaltokens.Runnables;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.halaltokens.halaltokens.R;
import com.halaltokens.halaltokens.Helpers.RoomInfo;
import com.halaltokens.halaltokens.Data.ScraperWorker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class SciEastRunnable implements Runnable {

    private static Context context = null;

    public SciEastRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        ArrayList<String> sciEastArrayList = new ArrayList<String>();
        sciEastArrayList.add(context.getString(R.string.E0_01));
        sciEastArrayList.add(context.getString(R.string.E0_08));
        sciEastArrayList.add(context.getString(R.string.E1_17));
        sciEastArrayList.add(context.getString(R.string.E1_19));
        sciEastArrayList.add(context.getString(R.string.E2_16));
        sciEastArrayList.add(context.getString(R.string.E2_18));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("");

        for (String page : sciEastArrayList) {
            try {
                Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                for (int i = 1; i <= 10; i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomName(doc.title().substring(10));
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                        Log.v("SciEastRunnable", roomInfo.toString());
                        ref.child("SciEast").push().setValue(roomInfo);
                    } catch (NullPointerException e) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
