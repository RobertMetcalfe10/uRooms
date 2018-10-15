package com.halaltokens.halaltokens.Runnables;

import android.content.Context;
import android.util.Log;

import com.halaltokens.halaltokens.R;
import com.halaltokens.halaltokens.RoomInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class ArtsRunnable implements Runnable {

    private static Context context = null;

    public ArtsRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        ArrayList<String> artsArraylist = new ArrayList<String>();
        artsArraylist.add(context.getString(R.string.A_A105));
        artsArraylist.add(context.getString(R.string.A_A106));
        artsArraylist.add(context.getString(R.string.A_A109));
        artsArraylist.add(context.getString(R.string.A_B101));
        artsArraylist.add(context.getString(R.string.A_C214));
        artsArraylist.add(context.getString(R.string.A_F101));
        artsArraylist.add(context.getString(R.string.A_F102));
        artsArraylist.add(context.getString(R.string.A_F103));
        artsArraylist.add(context.getString(R.string.A_F103A));
        artsArraylist.add(context.getString(R.string.A_F104));
        artsArraylist.add(context.getString(R.string.A_F106));
        artsArraylist.add(context.getString(R.string.A_F107));
        artsArraylist.add(context.getString(R.string.A_F308));
        artsArraylist.add(context.getString(R.string.A_G102));
        artsArraylist.add(context.getString(R.string.A_G107));
        artsArraylist.add(context.getString(R.string.A_G108));
        artsArraylist.add(context.getString(R.string.A_G109));
        artsArraylist.add(context.getString(R.string.A_J102));
        artsArraylist.add(context.getString(R.string.A_J104));
        artsArraylist.add(context.getString(R.string.A_J109));
        artsArraylist.add(context.getString(R.string.A_J110));
        artsArraylist.add(context.getString(R.string.A_J112));
        artsArraylist.add(context.getString(R.string.A_J114));
        artsArraylist.add(context.getString(R.string.A_NTH1));
        artsArraylist.add(context.getString(R.string.A_THL));
        artsArraylist.add(context.getString(R.string.A_THM));
        artsArraylist.add(context.getString(R.string.A_THP));
        artsArraylist.add(context.getString(R.string.A_THQ));
        artsArraylist.add(context.getString(R.string.A_THR));
        for (String page : artsArraylist) {
            try {
                Document doc = Jsoup.connect(page).get();
                for (int i = 1; i <= 10; i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomName(doc.title().substring(10));
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                        Log.v("ArtsRunnable", roomInfo.toString());
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
