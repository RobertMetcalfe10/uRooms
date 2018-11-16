package com.halaltokens.halaltokens;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import io.realm.Realm;

public class SplashScreen extends AppCompatActivity {

    private static int WELCOME_TIMEOUT = 5000;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        Realm.init(this);


        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            //check class is dashboard and if so dont start inside if
            if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }, WELCOME_TIMEOUT);
            } else {
                new Handler().postDelayed(() -> {
                    Intent i = new Intent(SplashScreen.this, LoginScreen.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }, WELCOME_TIMEOUT);
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
//        PeriodicWorkRequest.Builder scraperBuilder = new PeriodicWorkRequest.Builder(ScraperWorker.class, 24, TimeUnit.HOURS);
//        PeriodicWorkRequest scraperWork = scraperBuilder.build();
//        WorkManager.getInstance().cancelAllWork();
//        WorkManager.getInstance().enqueueUniquePeriodicWork("Scraper",ExistingPeriodicWorkPolicy.KEEP,scraperWork);

    }
}
