package com.halaltokens.halaltokens;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class BarcodeActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    SurfaceView surfaceView2;
    CameraSource cameraSource;
    TextView textview;
    BarcodeDetector barcodeDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        surfaceView = findViewById(R.id.camerapreview);
        surfaceView2 = findViewById(R.id.camerapreview2);

        textview = findViewById(R.id.textview);
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build();
        askForPermission();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                if(qrCodes.size()!=0){
                    textview.post(new Runnable() {
                        @Override
                        public void run() {
                            cameraSource.stop();
                            String link = qrCodes.valueAt(0).displayValue;
                            Intent viewIntent =
                                    new Intent("android.intent.action.VIEW",
                                            Uri.parse(link));
                            startActivity(viewIntent);
                        }
                    });
                }
            }
        });
    }

    private void askForPermission() {
        String permission = Manifest.permission.CAMERA;
        int requestCode = 1;
        if (ContextCompat.checkSelfPermission(BarcodeActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(BarcodeActivity.this, permission)) {
//                //This is called if user has denied the permission before
//                //In this case I am just asking the permission again
//                ActivityCompat.requestPermissions(BarcodeActivity.this, new String[]{permission}, requestCode);
//            } else {
                ActivityCompat.requestPermissions(BarcodeActivity.this, new String[]{permission}, requestCode);
//            }
        } else {
            Toast.makeText(BarcodeActivity.this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }
}
