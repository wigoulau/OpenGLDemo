package com.example.opengldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class HelloWorldActivity extends AppCompatActivity {

    private GLSurfaceView mglSurfaceView;
    private boolean mRendererSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mglSurfaceView = new GLSurfaceView(this);
        setContentView(mglSurfaceView);

        initOpenGL();
    }

    public void initOpenGL() {
        if (isSupportOpenGL() == true) {
            mglSurfaceView.setEGLContextClientVersion(2);
            mglSurfaceView.setRenderer(new HelloWorldOpenGLRenderer());
            mRendererSet = true;
        }
    }

    public boolean isSupportOpenGL() {
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();

        final boolean supportGlEs2 = (configurationInfo.reqGlEsVersion > 0x20000)
            || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                    && (Build.FINGERPRINT.startsWith("generic")
                            || Build.FINGERPRINT.startsWith("unknown")
                            || Build.MODEL.contains("google_sdk")
                            || Build.MODEL.contains("Emulator")
                            || Build.MODEL.contains("Android SDK built for x86")
                        )
                );

        return supportGlEs2;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRendererSet) {
            mglSurfaceView.onPause();
        }
    }

    @Override
    protected  void onResume() {
        super.onResume();
        if (mRendererSet) {
            mglSurfaceView.onResume();
        }
    }
}

class HelloWorldOpenGLRenderer implements GLSurfaceView.Renderer {

    private final static String TAG = "penGLRenderer";
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        Log.d(TAG, "onSurfaceCreated");
        GLES20.glClearColor(1.0f, 0.6f, 0.5f, 0.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        Log.d(TAG, "onSurfaceChanged");
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        Log.d(TAG, "onDrawFrame");
        //GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
}
