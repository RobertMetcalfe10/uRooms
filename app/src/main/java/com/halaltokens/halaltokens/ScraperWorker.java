package com.halaltokens.halaltokens;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.halaltokens.halaltokens.Runnables.AgRunnable;
import com.halaltokens.halaltokens.Runnables.ArtsRunnable;
import com.halaltokens.halaltokens.Runnables.CompSciRunnable;
import com.halaltokens.halaltokens.Runnables.EngRunnable;
import com.halaltokens.halaltokens.Runnables.HealthScienceRunnable;
import com.halaltokens.halaltokens.Runnables.SciEastRunnable;
import com.halaltokens.halaltokens.Runnables.SciHubRunnable;
import com.halaltokens.halaltokens.Runnables.SciNorthRunnable;
import com.halaltokens.halaltokens.Runnables.SciSouthRunnable;
import com.halaltokens.halaltokens.Runnables.SciWestRunnable;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ScraperWorker extends Worker {

    public ScraperWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Worker.Result doWork() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("");
        ref.child("Times on phone").push().setValue("Worked: "+LocalDateTime.now());

        try {

            Map<String, String> map = new HashMap<>();
            map.put("p_butn", "1");
            map.put("p_title", "Welcome to SISWeb");
            map.put("p_username", "15551647");
            map.put("p_password", "Destiny10");
            map.put("p_forward", "W_HU_MENU.P_DISPLAY_MENU¬p_menu=SI-HOME");
            map.put("p_lmet", "SISWEB");

            try {
                Jsoup.connect("https://sisweb.ucd.ie/usis/W_HU_LOGIN.P_PROC_LOGINBUT")
                        .method(Connection.Method.POST)
                        .data(map)
                        .post();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            //Comp Sci
            threadPoolExecutor.execute(new CompSciRunnable(getApplicationContext()));
            //AG
            threadPoolExecutor.execute(new AgRunnable(getApplicationContext()));
            //science east
            threadPoolExecutor.execute(new SciEastRunnable(getApplicationContext()));
            //science hub
            threadPoolExecutor.execute(new SciHubRunnable(getApplicationContext()));
            //science north
            threadPoolExecutor.execute(new SciNorthRunnable(getApplicationContext()));
            //science south
            threadPoolExecutor.execute(new SciSouthRunnable(getApplicationContext()));
            //science west
            threadPoolExecutor.execute(new SciWestRunnable(getApplicationContext()));
            //health science
            threadPoolExecutor.execute(new HealthScienceRunnable(getApplicationContext()));
            //eng
            threadPoolExecutor.execute(new EngRunnable(getApplicationContext()));
            //arts
            threadPoolExecutor.execute(new ArtsRunnable(getApplicationContext()));
            threadPoolExecutor.shutdown();

            return Result.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.FAILURE;
        }


    }

}

