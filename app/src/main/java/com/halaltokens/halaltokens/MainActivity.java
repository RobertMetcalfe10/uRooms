package com.halaltokens.halaltokens;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Scraper scraper = new Scraper();
        scraper.execute();
    }

    class Scraper extends AsyncTask<String,Integer,String>{

        @Override
        protected void onProgressUpdate(Integer... values) {
            //Update the progress of current task
        }

        @Override
        protected String doInBackground(String... strings) {
            Map<String, String> map = new HashMap<>();
            map.put("p_butn", "1");
            map.put("p_username", "15551647");
            map.put("p_password", "Destiny10");
            map.put("p_forward", "W_WEB_WELCOME_PAGE¬");
            map.put("p_lmet", "SISWEB");

            try {
                Jsoup.connect("https://sisweb.ucd.ie/usis/W_HU_LOGIN.P_PROC_LOGINBUT")
                        .method(Connection.Method.POST)
                        .data(map)
                        .post();
            } catch(IOException e) {
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
            return "Finished all threads";
        }

        @Override
        protected void onPostExecute(String s) {
            //Show the result obtained from doInBackground
            Log.i("Finished",s);
        }
    }
}
