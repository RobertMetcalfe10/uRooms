package com.halaltokens.halaltokens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    Button btn = (Button)findViewById(R.id.button);
    public void goToScanner (View view){
        Intent intent = new Intent (this, BarcodeActivity.class);
        startActivity(intent);
    }
}
