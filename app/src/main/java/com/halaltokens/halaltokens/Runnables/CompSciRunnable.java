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

public class CompSciRunnable implements Runnable {

    private static Context context = null;

    public CompSciRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("");
        try {
            Document doc = Jsoup.connect(context.getString(R.string.B004CSI)).cookies(ScraperWorker.response.cookies()).get();
            ref.child("CompSci").setValue(null);
            for(int i=1; i<=10;i++) {
                try {
                    RoomInfo roomInfo = new RoomInfo();
                    roomInfo.setRoomName(doc.title().substring(10));
                    roomInfo.setRoomInfo(doc.getElementById("RB200|0."+i).children());
                    Log.v("Comp",roomInfo.toString());
                    ref.child("CompSci").push().setValue(roomInfo);
                } catch (NullPointerException e) {
                    break;
                }
            }
        } catch(IOException e) {
//            e.printStackTrace();
        }
    }
}
