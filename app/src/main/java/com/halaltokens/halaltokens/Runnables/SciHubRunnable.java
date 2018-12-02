/*
 *  Scrapes every room for this building
 */

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

public class SciHubRunnable implements Runnable {

    private static Context context = null;

    public SciHubRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        ArrayList<String> sciHubArraylist = new ArrayList<String>();
        sciHubArraylist.add(context.getString(R.string.H0_12));
        sciHubArraylist.add(context.getString(R.string.H1_12));
        sciHubArraylist.add(context.getString(R.string.H1_13));
        sciHubArraylist.add(context.getString(R.string.H1_26));
        sciHubArraylist.add(context.getString(R.string.H1_37));
        sciHubArraylist.add(context.getString(R.string.H1_49));
        sciHubArraylist.add(context.getString(R.string.H1_51));
        sciHubArraylist.add(context.getString(R.string.H2_12));
        sciHubArraylist.add(context.getString(R.string.H2_18));
        sciHubArraylist.add(context.getString(R.string.H2_20));
        sciHubArraylist.add(context.getString(R.string.H2_21));
        sciHubArraylist.add(context.getString(R.string.H2_22));
        sciHubArraylist.add(context.getString(R.string.H2_32));
        sciHubArraylist.add(context.getString(R.string.H2_38));
        sciHubArraylist.add(context.getString(R.string.H2_40));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("");

        for (String page : sciHubArraylist) {
            try {
                Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                for (int i = 1; i <= 10; i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomName(doc.title().substring(10));
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                        Log.v("SciHubRunnable", roomInfo.toString());
                        ref.child("SciHub").push().setValue(roomInfo);
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
