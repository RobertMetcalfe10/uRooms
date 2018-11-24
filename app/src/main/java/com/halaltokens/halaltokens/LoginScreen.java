package com.halaltokens.halaltokens;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private EditText editEmail, editPassword;
    private LottieAnimationView signUpProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        askForPermission();

        findViewById(R.id.sign_up_button).setOnClickListener(this);
        findViewById(R.id.login_button).setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();


        editEmail = findViewById(R.id.edit_text_email);
        editPassword = findViewById(R.id.edit_text_password);
        signUpProgress = findViewById(R.id.sign_up_progress);



        editPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                hideKeyboard(editPassword);
                userLogin();
                return true;
            }
            return false;
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_button:
                finish();
                startActivity(new Intent(this, RegistrationScreen.class));
                break;

            case R.id.login_button:
                userLogin();
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void userLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please enter a valid email address");
            editEmail.requestFocus();
            return;
        }

        if (!email.toLowerCase().contains("ucd.ie") && !email.toLowerCase().contains("ucdconnect.ie")) {
            editEmail.setError("Please enter a valid UCD email");
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editPassword.setError("Password should be at least 6 characters long");
            editPassword.requestFocus();
            return;
        }


        signUpProgress.playAnimation();


        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            signUpProgress.cancelAnimation();
            signUpProgress.setVisibility(View.GONE);
            if (task.isSuccessful()) {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    if (firebaseUser.isEmailVerified()) {

                        Intent i = new Intent(LoginScreen.this, DashboardActivity.class);
                        startActivity(i);

                    } else {
                        firebaseAuth.signOut();
                        new SweetAlertDialog(LoginScreen.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Verification Required")
                                .setContentText("Please check your email for a verification link")
                                .show();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void askForPermission(){
        String permission = Manifest.permission.CAMERA;
        int requestCode = 1;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
