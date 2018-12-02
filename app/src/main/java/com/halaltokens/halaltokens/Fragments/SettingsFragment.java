/*
 *  Settings fragment that displays card views. The cardviews then can be clicked
 *  that each contain different functions
 */


package com.halaltokens.halaltokens.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.halaltokens.halaltokens.Activities.LoginScreen;
import com.halaltokens.halaltokens.R;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;


public class SettingsFragment extends Fragment {
    private FirebaseAuth firebaseAuth;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser current_user = firebaseAuth.getCurrentUser();

        TextView user_info = rootView.findViewById(R.id.user_information_text);
        String currentUserEmail = current_user.getEmail();
        String[] emailSplit = currentUserEmail.split("@");
        String[] fullName = emailSplit[0].split("\\.");

        String firstName = fullName[0].substring(0, 1).toUpperCase() + fullName[0].substring(1);
        String secondName = fullName[1].substring(0, 1).toUpperCase() + fullName[1].substring(1);
        secondName.replaceAll("\\^([0-9]+)", "");

        String userName = "User Name: " + firstName + " " + secondName;
        user_info.setText(userName);

        CardView contactUsButton = rootView.findViewById(R.id.contact_us_button);
        contactUsButton.setOnClickListener(view -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"eriks.burka@ucdconnect.ie"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "uRooms Query");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Enter your message here");
            startActivity(emailIntent);
        });

        CardView faqButton = rootView.findViewById(R.id.faq_button);
        faqButton.setOnClickListener(view -> {
            ListView qsList = new ListView(getContext());
            String[] qs = new String[]{"Q: Will we get an A+?", "A: Yes", "Q: How do I clear my favourites?", "A: The clear favourites button below", "Q: My QR scanner does not work?", "A: Have you tried turning your phone off and on again?"};
            ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, qs);
            qsList.setAdapter(modeAdapter);

            new SweetAlertDialog(getActivity())
                    .setTitleText("FAQs")
                    .setCustomView(qsList)
                    .show();
        });

        CardView signOutButton = rootView.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(v -> {
            if (firebaseAuth.getCurrentUser() != null) {
                firebaseAuth.signOut();
                Intent i = new Intent(getContext(), LoginScreen.class);
                startActivity(i);
            } else
                Toast.makeText(getContext(), "This should not happen. If you see this message you broke the app. Congrats", Toast.LENGTH_LONG).show();

        });

        CardView clearFavsButton = rootView.findViewById(R.id.clearFavs);
        clearFavsButton.setOnClickListener(v -> {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
            Toast toast = Toast.makeText(getContext(), "Favourites Cleared", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });

        return rootView;
    }
}
