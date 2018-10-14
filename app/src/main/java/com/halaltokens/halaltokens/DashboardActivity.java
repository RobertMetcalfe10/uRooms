package com.halaltokens.halaltokens;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

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

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("TEST", R.mipmap.ic_launcher);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("TEST2", R.mipmap.ic_launcher);
        final AHBottomNavigation ahBottomNavigation = findViewById(R.id.bottom_navigation);
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
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


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment(){}

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
//            TextView textView = rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            //creating an arraylist which contains available rooms
            ArrayList<String> listOfRooms = new ArrayList<>();
            listOfRooms.add("first room");
            listOfRooms.add("second room");
            listOfRooms.add("third room");
            listOfRooms.add("fourth room");

            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listOfRooms);

            //listview to show the rooms
            ListView lv = rootView.findViewById(R.id.listView);
            lv.setAdapter(itemsAdapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String value = (String)adapterView.getItemAtPosition(i);

                    // consider using Java coding conventions (upper first char class names!!!)
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    transaction.replace(R.id.frag, individualRoomFragment.newInstance("new frag"));
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();

                }
            });

            return rootView;
        }
    }

    //another fragment for the individual room info
    public static class individualRoomFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.individual_room_frag, container, false);

            TextView tv = v.findViewById(R.id.textViewFrag);
            tv.setText(getArguments().getString("Wagwan from individual room"));

            return v;
        }

        public individualRoomFragment(){}

        public static individualRoomFragment newInstance(String text) {

            individualRoomFragment f = new individualRoomFragment();
            Bundle b = new Bundle();
            b.putString("msg", text);

            f.setArguments(b);

            return f;
        }
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
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}
