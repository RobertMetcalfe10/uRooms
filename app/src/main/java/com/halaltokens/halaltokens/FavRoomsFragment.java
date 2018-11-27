package com.halaltokens.halaltokens;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
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

    private RecyclerView recyclerView;
    private FavCardViewAdapter cAdapter;
    private static ArrayList<String> listOfFavRooms;
    private FavCardViewAdapter.FavOnItemClickedListener favOnItemClickedListener = new FavCardViewAdapter.FavOnItemClickedListener() {
        @Override
        public void OnFavItemClicked(String roomName) {
            cAdapter = new FavCardViewAdapter(prepareData());
            recyclerView.setAdapter(cAdapter);
        }

        @Override
        public void OnShareItemClicked(String roomName) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<RoomInfo> result = realm.where(RoomInfo.class).findAll();
            for (int i=0; i<result.size(); i++) {
                if (result.get(i).getRoomName().trim().equals(roomName)) {
                    sendIntent.putExtra(Intent.EXTRA_TITLE, result.get(i).getRoomName());
                    sendIntent.putExtra(Intent.EXTRA_TEXT, result.get(i).toString());
                }
            }
            realm.commitTransaction();
            sendIntent.setType("text/plain");
            // Verify that the intent will resolve to an activity
            if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(sendIntent);
            }
        }

        @Override
        public void OnTextItemClicked(String roomName) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<RoomInfoRealmList> result = realm.where(RoomInfoRealmList.class).findAll();
            for (int i=0; i<result.size(); i++) {
                if (result.get(i).getRoomInfo(0).getRoomName().trim().equals(roomName)) {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(FavRoomsFragment.this.getContext(), SweetAlertDialog.NORMAL_TYPE);
                    sweetAlertDialog.setContentText(result.get(i).toString());
                    sweetAlertDialog.show();
                    Log.v("FavDialog", result.get(i).toString());
                }
            }
            realm.commitTransaction();
        }
    };

    private OnFragmentInteractionListener mListener;

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

        //initialize Adapterclass with List
        cAdapter = new FavCardViewAdapter(prepareData());
        cAdapter.setOnItemClickedListener(favOnItemClickedListener);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter); //add adapter to recycler view
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
        cAdapter = new FavCardViewAdapter(prepareData());
        cAdapter.setOnItemClickedListener(favOnItemClickedListener);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (recyclerView.getAdapter().getItemCount() == 0) {
                Toast toast = Toast.makeText(getContext(),"You Have No Favourites", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        }
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
