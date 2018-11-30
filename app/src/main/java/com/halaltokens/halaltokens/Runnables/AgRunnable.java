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


public class AgRunnable implements Runnable {

    private static Context context = null;

    public AgRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        ArrayList<String> agArrayList = new ArrayList<String>();
        agArrayList.add(context.getString(R.string.AG1_01));
        agArrayList.add(context.getString(R.string.AG1_16));
        agArrayList.add(context.getString(R.string.AG1_18));
        agArrayList.add(context.getString(R.string.AG1_19));
        agArrayList.add(context.getString(R.string.AG1_47));
        agArrayList.add(context.getString(R.string.AG3_26));
        agArrayList.add(context.getString(R.string.AG_FS_G01));
        agArrayList.add(context.getString(R.string.AG_G_06));
        agArrayList.add(context.getString(R.string.AG_G_07));
        agArrayList.add(context.getString(R.string.AG_G_08));
        agArrayList.add(context.getString(R.string.AG_G_09));
        agArrayList.add(context.getString(R.string.AG_G_15));
        agArrayList.add(context.getString(R.string.AG_G_24));
        agArrayList.add(context.getString(R.string.AG_G_30_PCLAB));
        agArrayList.add(context.getString(R.string.AG_LG_08A));
        agArrayList.add(context.getString(R.string.AG_LG_08B));
        agArrayList.add(context.getString(R.string.AG_LG_20));
        for (String page : agArrayList) {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("");
            try {
                Document doc = Jsoup.connect(page).cookies(ScraperWorker.response.cookies()).get();
                for (int i = 1; i <= 10; i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomName(doc.title().substring(10));
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                        Log.v("Ag", roomInfo.toString());
                        ref.child("Ag").push().setValue(roomInfo);
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
