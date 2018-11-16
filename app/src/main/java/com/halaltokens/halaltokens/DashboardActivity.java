package com.halaltokens.halaltokens;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;



public class DashboardActivity extends AppCompatActivity implements BuildingFragment.OnItemClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.mipmap.ic_launcher);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Favourites", R.mipmap.ic_launcher);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("QR", R.mipmap.ic_launcher);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Settings", R.mipmap.ic_launcher);
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
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            public void onPageSelected(int position) {
                Log.i("Position ViewPager", String.valueOf(position));
                ahBottomNavigation.setCurrentItem(position);
            }
        });

        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                Log.i("Position", String.valueOf(position));
                mViewPager.setCurrentItem(position);
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBuildingClicked(String building) {
//        Log.v("Building", building);
        Intent intent = new Intent(this, RoomsAvailable.class);
        intent.putExtra("Building", building);
        startActivity(intent);
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a BuildingFragment (defined as a static inner class below).
//            return IndividualRoomFragment.newInstance("Blah");

            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return BuildingFragment.newInstance(position);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return BuildingFragment.newInstance(position + 1);
                case 2: // Fragment # 0 - This will show FirstFragment
                    return BuildingFragment.newInstance(position + 2);
                case 3: // Fragment # 0 - This will show FirstFragment different title
                    return SettingsFragment.newInstance();
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }

    @Override
    public void onBackPressed(){
        //TODO: When user pressed back from home page, close the app...
    }
}

