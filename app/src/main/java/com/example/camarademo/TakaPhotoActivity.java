package com.example.camarademo;

import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.core.glcore.config.MRConfig;
import com.core.glcore.config.Size;
import com.immomo.moment.config.MRecorderActions;
import com.mm.mediasdk.IMultiRecorder;
import com.mm.mediasdk.MoMediaManager;

import java.io.File;

/**
 *  
 */

public class TakaPhotoActivity extends AppCompatActivity {
    private static final String TAG = "TakaPhotoActivity";
    private IMultiRecorder recorder;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taka_photo);
        recorder = MoMediaManager.createRecorder();
        recorder.prepare(this,getConfig());
        initView() ;
    }

    private void initView() {
        surfaceView = findViewById(R.id.surface_view);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                //view预览分辨率
                recorder.setVisualSize(width, height);
                recorder.setPreviewDisplay(holder);
                recorder.startPreview();

                // 大眼
                recorder.setFaceEyeScale(200);
                // 瘦脸
                recorder.setFaceThinScale(20);
                // 磨皮
//                recorder.setSkinLevel(20);
//                // 美颜
//                recorder.setSkinLightingLevel(20);

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }



    private MRConfig getConfig() {
        MRConfig mrConfig = MRConfig.obtain();
        mrConfig.setDefaultCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        Size size = new Size(1280, 720);
        mrConfig.setEncodeSize(size);
        // 设置camera 的采集分辨率
        mrConfig.setTargetVideoSize(size);
        return mrConfig;
    }

    @Override
    protected void onPause() {
        super.onPause();
        recorder.stopPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recorder.release();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                File file = new File(Environment.getExternalStorageDirectory(), "take_photo.jpg");
                recorder.takePhoto(file.getAbsolutePath(), new MRecorderActions.OnTakePhotoListener() {
                    @Override
                    public void onTakePhotoComplete(int status, Exception e) {
                        //0表示完成， -1表示失败
                        Log.e(TAG, "---->>onTakePhotoComplete: "+status );
                    }
                });
                break;

            case R.id.switch_camera:
                recorder.switchCamera();
                break;
        }
    }
}
