package com.example.adam.opengles;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

/*
    private GLRenderer mRenderer;
    private GLSurfaceView glSurfaceView;
*/
    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        mGLSurfaceView = new GLSurfaceView(this);

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();

        // 端末がOpenGL ES 2.0をサポートしているかチェック
        if (configurationInfo.reqGlEsVersion >= 0x20000) {
            mGLSurfaceView.setEGLContextClientVersion(2);  // OpenGLバージョンを設定
            mGLSurfaceView.setRenderer(new MyRenderer());  // レンダラを設定
        } else {
            return;
        }
        setContentView(mGLSurfaceView);



        float ADJ_X=150.0f;
        float ADJ_Y=150.0f;
        FlickChecker fc=new FlickChecker(mGLSurfaceView, ADJ_X, ADJ_Y) {
            @Override
            public void getFlick(int swipe, float gap) {


                switch(swipe){

                    case FlickChecker.LEFT_SCROLL:
                        Log.d("FlickCheck","left");
                        MyRenderer.mRotX-=1;

                        break;
                    case FlickChecker.RIGHT_SCROLL:
                        Log.d("FlickCheck","right");
                        MyRenderer.mRotX+=1;

                        break;

                }

                switch(swipe){

                    case FlickChecker.UP_SCROLL:
                        Log.d("FlickCheck","up");
                        MyRenderer.mRotY-=1;
                        break;
                    case FlickChecker.DOWN_SCROLL:
                        Log.d("FlickCheck","down");
                        MyRenderer.mRotY+=1;
                        break;

                }

            }
        };


        /*
        //opengl es 1
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        mRenderer = new GLRenderer(getApplicationContext());
        //mRenderer = new GLRenderer();
        glSurfaceView.setRenderer(mRenderer);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        setContentView(glSurfaceView);
        */

    }


    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();  // 忘れずに！
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();  // 忘れずに！
    }






}
