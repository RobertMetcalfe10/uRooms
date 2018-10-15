package com.halaltokens.halaltokens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class IndividualRoomFragment extends Fragment {

    private static TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.individual_room_frag, container, false);
        tv = view.findViewById(R.id.textViewFrag);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (getArguments() != null) {
            tv.setText(getArguments().getString("msg"));
            Toast.makeText(getContext(), "INSIDE NEW FRAGMENT: " + tv.getText(), Toast.LENGTH_LONG).show();
        }
    }

    public IndividualRoomFragment(){}

    public static IndividualRoomFragment newInstance(String msg) {
        IndividualRoomFragment fragment = new IndividualRoomFragment();
        Bundle args = new Bundle();
        args.putString("msg", msg);
        fragment.setArguments(args);
        return fragment;
    }

}
