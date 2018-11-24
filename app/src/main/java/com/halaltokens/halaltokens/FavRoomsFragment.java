package com.halaltokens.halaltokens;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavRoomsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavRoomsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavRoomsFragment extends Fragment {

    private static OnItemClickListener callback;

    private RecyclerView recyclerView;
    private ImageView shareButton;
    private ImageView unFaveButton;
    private FavCardViewAdapter cAdapter;
    private static ArrayList<String> listOfFavRooms;


    private OnFragmentInteractionListener mListener;

    public interface OnItemClickListener {
        void onBuildingClicked(String building);
    }

    public FavRoomsFragment() {
        // Required empty public constructor
    }

    public static FavRoomsFragment newInstance() {
        return new FavRoomsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fav_rooms_fragments, container, false);

        //initialize recyclerview
        recyclerView = rootView.findViewById(R.id.recycler_view_fav);
        shareButton = rootView.findViewById(R.id.shareButton);


        //initialize Adapterclass with List
        cAdapter = new FavCardViewAdapter(prepareData(), callback);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter); //add adapter to recycler view

        recyclerView.setOnClickListener(view -> {
            int itemPosition = recyclerView.getChildAdapterPosition(view);
            String item = listOfFavRooms.get(itemPosition);
            callback.onBuildingClicked(item);
        });

        shareButton.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT, roomsMap.get(data).toString());
            sendIntent.setType("text/plain");

// Verify that the intent will resolve to an activity
//            if (sendIntent.resolveActivity(getPackageManager()) != null) {
//                startActivity(sendIntent);
//            }
        });


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        cAdapter = new FavCardViewAdapter(prepareData(), callback);
        recyclerView.setAdapter(cAdapter);
    }


    public static List<String> prepareData(){
        //creating an arraylist which contains available rooms
        listOfFavRooms = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RoomInfo> rooms = realm.where(RoomInfo.class).findAll();
        for (RoomInfo roomInfo:rooms) {
            listOfFavRooms.add(roomInfo.getRoomName());
        }
        return listOfFavRooms;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
