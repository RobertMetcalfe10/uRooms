package com.halaltokens.halaltokens.Runnables;

import android.content.Context;
import android.util.Log;

import com.halaltokens.halaltokens.R;
import com.halaltokens.halaltokens.RoomInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class SciSouthRunnable implements Runnable {

    private static Context context = null;

    public SciSouthRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        ArrayList<String> sciSouthArraylist = new ArrayList<String>();
        sciSouthArraylist.add(context.getString(R.string.S1_67));
        sciSouthArraylist.add(context.getString(R.string.S3_56));
        for (String page : sciSouthArraylist) {
            try {
                Document doc = Jsoup.connect(page).get();
                for (int i = 1; i <= 10; i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomName(doc.title().substring(10));
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                        Log.v("SciSouthRunnable", roomInfo.toString());
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
