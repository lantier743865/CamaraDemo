package com.example.camarademo.custom;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 *  自定义相机
 */
public class CamaraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CamaraPreview";
    private Camera mCamera;

    private int widthPixels;
    public CamaraPreview(Context context) {
        super(context);
        init();
    }

    public CamaraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setKeepScreenOn(true);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        widthPixels = displayMetrics.widthPixels;


    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        int frontCamera = findFrontCamera();
        mCamera = Camera.open(frontCamera);
        try {
//            mCamera.setPreviewDisplay(holder);
            Camera.Parameters parameters = mCamera.getParameters();
            int nv21 = ImageFormat.NV21;
            Log.e(TAG, "---->>ImageFormat.NV21: "+nv21 );
            int yv12 = ImageFormat.YV12;
            Log.e(TAG, "---->>ImageFormat.YV12: "+yv12 );
            int jpeg = ImageFormat.JPEG;
            Log.e(TAG, "---->>ImageFormat.JPEG: "+jpeg );

            List<Integer> supportedPreviewFormats = parameters.getSupportedPreviewFormats();
            for (int i = 0; i < supportedPreviewFormats.size(); i++) {
                Integer integer = supportedPreviewFormats.get(i);
                Log.e(TAG, "---->>supportedPreviewFormats: "+integer );
            }
            List<Integer> supportedPictureFormats = parameters.getSupportedPictureFormats();
            for (int i = 0; i < supportedPictureFormats.size(); i++) {
                Integer integer = supportedPictureFormats.get(i);
                Log.e(TAG, "---->>supportedPictureFormats: "+integer );
            }
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                // 竖屏拍照时，需要设置旋转90度， 否则看的的相机预览方向和界面方向不同
                mCamera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else {
                mCamera.setDisplayOrientation(0);
                parameters.setRotation(0);
            }
            // 设置比例
            Camera.Size bestSize = getBestSize(parameters.getSupportedPreviewSizes());
//            if (bestSize != null) {
//                parameters.setPreviewSize(bestSize.width, bestSize.height);
//                parameters.setPictureSize(bestSize.width, bestSize.height);
//            } else {
//                parameters.setPreviewSize(1920,1080);
//                parameters.setPictureSize(1920,1080);
//            }
            parameters.setPreviewSize(480,360);
            parameters.setPictureSize(480,360);
            mCamera.setParameters(parameters);
            mCamera.startPreview();
            mCamera.setPreviewDisplay(holder);
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
//                    Log.e(TAG, "---->>onPreviewFrame:data: "+data);
//                    Log.e(TAG, "---->>onPreviewFrame:camera: "+camera);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Camera.Parameters parameters = mCamera.getParameters();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                // 竖屏拍照时，需要设置旋转90度， 否则看的的相机预览方向和界面方向不同
                mCamera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else {
                mCamera.setDisplayOrientation(0);
                parameters.setRotation(0);
            }
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }



    private Camera.Size getBestSize(List<Camera.Size> sizes) {
        Camera.Size bestSize = null;
        for (Camera.Size size : sizes) {
            if (size.width / size.height == 16.0 /9.0) {
                if (bestSize == null) {
                    bestSize = size;
                } else {
                    if (size.width > bestSize.width) {
                        bestSize = size;
                    }
                }
            }
        }
        return bestSize;
    }


    public void takePhoto(Camera.PictureCallback callback) {
        if (mCamera != null) {
            mCamera.takePicture(null,null, callback);
        }
    }


    private int findFrontCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i,cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthPixels,widthPixels);
    }
}
