package com.halaltokens.halaltokens.Runnables;

import android.content.Context;
import android.util.Log;

import com.halaltokens.halaltokens.R;
import com.halaltokens.halaltokens.RoomInfo;

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
        for (String page : sciNorthArraylist) {
            try {
                Document doc = Jsoup.connect(page).get();
                for (int i = 1; i <= 10; i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomName(doc.title().substring(10));
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                        Log.v("SciNorthRunnable", roomInfo.toString());
                    } catch (NullPointerException e) {
                        break;
                    }
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
