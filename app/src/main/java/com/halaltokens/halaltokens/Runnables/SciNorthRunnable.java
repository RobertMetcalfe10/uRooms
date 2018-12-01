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

public class SciNorthRunnable implements Runnable {

    private static Context context = null;

    public SciNorthRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        ArrayList<String> sciNorthArraylist = new ArrayList<String>();
        sciNorthArraylist.add(context.getString(R.string.N129));
        sciNorthArraylist.add(context.getString(R.string.N231));
        sciNorthArraylist.add(context.getString(R.string.N232));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("");

        for (String page : sciNorthArraylist) {
            try {
                Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                for (int i = 1; i <= 10; i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomName(doc.title().substring(10));
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                        Log.v("SciNorthRunnable", roomInfo.toString());
                        ref.child("SciNorth").push().setValue(roomInfo);
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
