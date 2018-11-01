package com.halaltokens.halaltokens.Runnables;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.halaltokens.halaltokens.R;
import com.halaltokens.halaltokens.RoomInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import io.realm.Realm;

public class CompSciRunnable implements Runnable {

    private static Context context = null;

    public CompSciRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect(context.getString(R.string.B004CSI)).get();
            for(int i=1; i<=10;i++) {
                try {
                    RoomInfo roomInfo = new RoomInfo();
                    roomInfo.setRoomName(doc.title().substring(10));
                    roomInfo.setRoomInfo(doc.getElementById("RB200|0."+i).children());
                    Log.v("Comp",roomInfo.toString());
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(roomInfo);
                    realm.commitTransaction();
                } catch (NullPointerException e) {
                    break;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
