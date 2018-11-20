package com.halaltokens.halaltokens;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Objects;

import static android.media.MediaRecorder.VideoSource.CAMERA;
import static java.util.logging.Level.parse;

public class QRFragment extends Fragment {
    private static QRFragment fragment;
    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView2;
    BarcodeDetector barcodeDetector;
    Button button;

    private OnFragmentInteractionListener mListener;

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
        askForPermission();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_qr, container, false);
        surfaceView = view.findViewById(R.id.camerapreview2);
        textView2 = view.findViewById(R.id.textview2);
        barcodeDetector = new BarcodeDetector.Builder(getActivity()).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(getActivity(), barcodeDetector).setRequestedPreviewSize(640, 480).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // getActivity.getapplication context instead in the bottom part.
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
                        myWebView.loadUrl(link);

                        WebSettings webSettings = myWebView.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        myWebView.setWebViewClient(new WebViewClient());

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

        private void askForPermission(){
            String permission = Manifest.permission.CAMERA;
            int requestCode = 1;
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                    //This is called if user has denied the permission before
                    //In this case I am just asking the permission again
                    ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
                }
            }
        }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
