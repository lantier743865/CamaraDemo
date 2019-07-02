package com.example.camarademo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.camarademo.custom.CamaraPreview;
import com.example.camarademo.custom.TipView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private CamaraPreview mCametaview;
    private TipView tipview;
    private ImageView btTakePhoto;
    private File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFile = new File(Environment.getExternalStorageDirectory(),"custompic.jpg");

        initView();
    }

    private void initView() {
        initTestView();
        tipview = findViewById(R.id.tipview_activity);
        mCametaview = findViewById(R.id.cametaview_activity);
        btTakePhoto = findViewById(R.id.bt_take_photo);
        btTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCametaview.takePhoto(new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(final byte[] data, Camera camera) {
                        camera.stopPreview();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                OutputStream os = null;
                                boolean ret = false;
                                try {
                                    os = new BufferedOutputStream(new FileOutputStream(mFile));
                                    ret = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                                    if (!bitmap.isRecycled()) {
                                        bitmap.recycle();
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        os.close();
                                        String absolutePath = mFile.getAbsolutePath();
                                        Log.e(TAG, "---->>absolutePath: " + absolutePath);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();

                    }
                });
            }
        });
    }




    private void initTestView() {

        findViewById(R.id.bt_rotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipview.setRoutateDegrees(75);
                tipview.invalidate();

            }
        });
        findViewById(R.id.bt_setwidth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipview.setWidth(400);
                tipview.invalidate();

            }
        });
        findViewById(R.id.bt_setstartpoint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipview.setStartPoint(new Point(160,220));
                tipview.invalidate();
            }
        });

        findViewById(R.id.bt_changetheme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipview.setYellowTheme();
                tipview.invalidate();

            }
        });

        findViewById(R.id.bt_yellow_line).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipview.setHorizontalThinLineY(15);
                tipview.setVertialThinLineX(-8);
                tipview.invalidate();

            }
        });
    }
}
