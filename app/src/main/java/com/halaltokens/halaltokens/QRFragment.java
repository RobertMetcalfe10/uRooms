/*
In the building fragment,
 */


package com.halaltokens.halaltokens;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.iambedant.text.OutlineTextView;

import java.io.IOException;

public class QRFragment extends Fragment {
    private static QRFragment fragment;
    SurfaceView surfaceView;
    CameraSource cameraSource;
    OutlineTextView textView2;
    BarcodeDetector barcodeDetector;
    Button button;

    public QRFragment() {
        // Required empty public constructor
    }

    public static QRFragment newInstance() {
        fragment = new QRFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_qr, container, false);
        surfaceView = view.findViewById(R.id.camerapreview2);
        textView2 = view.findViewById(R.id.textview2);
        barcodeDetector = new BarcodeDetector.Builder(getActivity()).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(getActivity(), barcodeDetector).setRequestedPreviewSize(640, 480).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // getActivity.get application context instead in the bottom part.
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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
                final TextView textView2 = view.findViewById(R.id.textview2);

                if (qrCodes.size() != 0) {
                    surfaceView.post(() -> {
                        cameraSource.stop();
                        String link = qrCodes.valueAt(0).displayValue;
                        surfaceView.setVisibility(View.GONE);
                        textView2.setVisibility(View.GONE);

                        WebView myWebView = view.findViewById(R.id.webview);
                        WebSettings webSettings = myWebView.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                        webSettings.setDomStorageEnabled(true);
                        webSettings.setLoadsImagesAutomatically(true);
                        myWebView.setWebViewClient(new WebViewClient() {
                            public void onPageFinished(WebView view, String url) {
                                Log.i("Loaded", "Finished loading URL: " + url);
                            }
                        });
                        myWebView.loadUrl(link);

                        if (link.contains("www.ucd.ie/rooms/")) {
                            myWebView.loadUrl("https://sisweb.ucd.ie/usis/!W_HU_MENU.P_PUBLISH?p_tag=UROOMS&ROOMCODE=" + link.substring(17, link.length()));
                        }

                        button = view.findViewById(R.id.button);
                        button.setEnabled(true);

                        button.setOnClickListener(view1 -> {
                            surfaceView.setVisibility(View.VISIBLE);
                            textView2.setVisibility(View.VISIBLE);
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            button.setEnabled(false);
                            ft.detach(QRFragment.fragment).attach(QRFragment.fragment).commit();
                        });

                    });
                }
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        cameraSource.stop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
