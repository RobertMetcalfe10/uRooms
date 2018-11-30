/*
 *  This is the activity that user first sees when they open the app
 *  The navigation drawer is initialized here at the bottom of the page
 *  so that users can seamlessly switch between fragments
 */



package com.halaltokens.halaltokens.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.halaltokens.halaltokens.Fragments.BuildingFragment;
import com.halaltokens.halaltokens.Fragments.FavRoomsFragment;
import com.halaltokens.halaltokens.Fragments.QRFragment;
import com.halaltokens.halaltokens.Fragments.SettingsFragment;
import com.halaltokens.halaltokens.R;
import com.halaltokens.halaltokens.Helpers.RoomsAvailable;


public class DashboardActivity extends AppCompatActivity implements QRFragment.OnFragmentInteractionListener, FavRoomsFragment.OnFragmentInteractionListener {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Favourites", R.drawable.ic_fav_black_nav);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("QR", R.drawable.qrcode);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Settings", R.drawable.ic_settings_black_24dp);
        final AHBottomNavigation ahBottomNavigation = findViewById(R.id.bottom_navigation);
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);
        ahBottomNavigation.addItem(item4);
        ahBottomNavigation.setCurrentItem(0);

        // Set background color
        ahBottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        // Disable the translation inside the CoordinatorLayout
        ahBottomNavigation.setBehaviorTranslationEnabled(false);

        // Change colors
        ahBottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        ahBottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        // Force to tint the drawable (useful for font with icon for example)
        ahBottomNavigation.setForceTint(true);
        Log.i("Current", String.valueOf(ahBottomNavigation.getItemsCount()));


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*
      The {@link android.support.v4.view.PagerAdapter} that will provide
      fragments for each of the sections. We use a
      {@link FragmentPagerAdapter} derivative, which will keep every
      loaded fragment in memory. If this becomes too memory intensive, it
      may be best to switch to a
      {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            public void onPageSelected(int position) {
                ahBottomNavigation.setCurrentItem(position);
            }
        });

        ahBottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            Log.i("Position", String.valueOf(position));
            mViewPager.setCurrentItem(position);
            return true;
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return BuildingFragment.newInstance(building -> {
                        Intent intent = new Intent(getApplicationContext(), RoomsAvailable.class);
                        intent.putExtra("Building", building);
                        startActivity(intent);
                    });
                case 1:
                    FavRoomsFragment.prepareData();
                    return FavRoomsFragment.newInstance();
                case 2:
                    return QRFragment.newInstance();
                case 3:
                    return SettingsFragment.newInstance();
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }
    }

    @Override
    public void onBackPressed(){
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

