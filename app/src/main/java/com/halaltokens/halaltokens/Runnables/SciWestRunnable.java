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

public class SciWestRunnable implements Runnable {

    private static Context context = null;

    public SciWestRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        ArrayList<String> sciWestArraylist = new ArrayList<String>();
        sciWestArraylist.add(context.getString(R.string.B101A));
        sciWestArraylist.add(context.getString(R.string.B101B));
        sciWestArraylist.add(context.getString(R.string.B154A));
        sciWestArraylist.add(context.getString(R.string.B154B));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("");

        for (String page : sciWestArraylist) {
            try {
                Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                for (int i = 1; i <= 10; i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomName(doc.title().substring(10));
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                        Log.v("SciWestRunnable", roomInfo.toString());
                        ref.child("SciWest").push().setValue(roomInfo);
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
