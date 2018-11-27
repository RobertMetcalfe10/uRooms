package com.halaltokens.halaltokens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;


/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static SettingsFragment fragment;
    private FirebaseAuth firebaseAuth;

    public SettingsFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SettingsFragment newInstance() {
        fragment = new SettingsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser current_user = firebaseAuth.getCurrentUser();

        TextView user_info = rootView.findViewById(R.id.user_information_text);
        String currentUserEmail = current_user.getEmail();
        String [] emailSplit = currentUserEmail.split("@");
        String [] fullName = emailSplit[0].split("\\.");

        String firstName = fullName[0].substring(0,1).toUpperCase() + fullName[0].substring(1);
        String secondName = fullName[1].substring(0,1).toUpperCase() + fullName[1].substring(1);
        secondName.replaceAll("\\^([0-9]+)", "");

        String userName = "User Name: " + firstName + " " + secondName;
        user_info.setText(userName);

        CardView contactUsButton = rootView.findViewById(R.id.contact_us_button);
        contactUsButton.setOnClickListener(view -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"eriks.burka@ucdconnect.ie"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "uRooms Query");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Enter your message here");
            startActivity(emailIntent);
        });

        CardView faqButton = rootView.findViewById(R.id.faq_button);
        faqButton.setOnClickListener(view -> {

            String faqText =    "QA: Will we get an A+?\n\nYes." +
                                "\n-------------------------------\n QA: How do i clear my favourites?\n\nFind the clear favourites button." +
                                "\n-------------------------------\n QA: My QR Scanner does not work\n\nHave you tried turning your phone off and on again?";

            new SweetAlertDialog(getActivity())
                    .setTitleText("FAQs")
                    .setContentText(faqText)
                    .show();
        });

        CardView signOutButton = rootView.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(v -> {
            if (firebaseAuth.getCurrentUser() != null) {
                firebaseAuth.signOut();
                Intent i = new Intent(getContext(), LoginScreen.class);
                startActivity(i);
            }else Toast.makeText(getContext(), "This should not happen. If you see this message you broke the app. Congrats", Toast.LENGTH_LONG).show();

        });

        CardView clearFavsButton = rootView.findViewById(R.id.clearFavs);
        clearFavsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();
            }
        });

        return rootView;
    }
}
