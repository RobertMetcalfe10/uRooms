package com.halaltokens.halaltokens.Runnables;

import android.content.Context;
import android.util.Log;

import com.halaltokens.halaltokens.R;
import com.halaltokens.halaltokens.RoomInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class HealthScienceRunnable implements Runnable {

    private static Context context = null;

    public HealthScienceRunnable(Context con) {
        context = con;
    }

    @Override
    public void run() {
        ArrayList<String> healthScienceArraylist = new ArrayList<String>();
        healthScienceArraylist.add(context.getString(R.string.A003));
        healthScienceArraylist.add(context.getString(R.string.A004));
        healthScienceArraylist.add(context.getString(R.string.A005));
        healthScienceArraylist.add(context.getString(R.string.A006));
        healthScienceArraylist.add(context.getString(R.string.A007));
        healthScienceArraylist.add(context.getString(R.string.A008));
        healthScienceArraylist.add(context.getString(R.string.B004));
        healthScienceArraylist.add(context.getString(R.string.B005));
        healthScienceArraylist.add(context.getString(R.string.B006));
        healthScienceArraylist.add(context.getString(R.string.C004));
        healthScienceArraylist.add(context.getString(R.string.C005));
        healthScienceArraylist.add(context.getString(R.string.C006));
        healthScienceArraylist.add(context.getString(R.string.C007));
        healthScienceArraylist.add(context.getString(R.string.C112));
        healthScienceArraylist.add(context.getString(R.string.C113));
        healthScienceArraylist.add(context.getString(R.string.C114));
        healthScienceArraylist.add(context.getString(R.string.C115));
        healthScienceArraylist.add(context.getString(R.string.C116));
        healthScienceArraylist.add(context.getString(R.string.C117));
        healthScienceArraylist.add(context.getString(R.string.C118));
        healthScienceArraylist.add(context.getString(R.string.C120));
        healthScienceArraylist.add(context.getString(R.string.C122));
        healthScienceArraylist.add(context.getString(R.string.C311));
        healthScienceArraylist.add(context.getString(R.string.C314));
        healthScienceArraylist.add(context.getString(R.string.D114));
        healthScienceArraylist.add(context.getString(R.string.D115));
        healthScienceArraylist.add(context.getString(R.string.B227));
        healthScienceArraylist.add(context.getString(R.string.B237));
        healthScienceArraylist.add(context.getString(R.string.B311));
        healthScienceArraylist.add(context.getString(R.string.B333));
        healthScienceArraylist.add(context.getString(R.string.B341));
        healthScienceArraylist.add(context.getString(R.string.B342));
        healthScienceArraylist.add(context.getString(R.string.B109));
        for (String page : healthScienceArraylist) {
            try {
                Document doc = Jsoup.connect(page).get();
                for (int i = 1; i <= 10; i++) {
                    try {
                        RoomInfo roomInfo = new RoomInfo();
                        roomInfo.setRoomName(doc.title().substring(10));
                        roomInfo.setRoomInfo(doc.getElementById("RB200|0." + i).children());
                        Log.v("HealthScienceRunnable", roomInfo.toString());
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
