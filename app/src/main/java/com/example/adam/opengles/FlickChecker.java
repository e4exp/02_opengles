package com.example.adam.opengles;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class FlickChecker {

    private static final int LEFT_FLICK=0;
    private static final int RIGHT_FLICK=1;
    private static final int UP_FLICK=2;
    private static final int DOWN_FLICK=3;

    static final int LEFT_SCROLL=4;
    static final int RIGHT_SCROLL=5;
    static final int UP_SCROLL=6;
    static final int DOWN_SCROLL=7;

    private float adjustX=50.0f;
    private float adjustY=50.0f;
    private float touchX;
    private float touchY;
    private float nowTouchX;
    private float nowTouchY;




    /**
     *
     set View to frickView to detect flick
     set distances of holizontal flick to adjustX, vertical flick to adjustY

     * @param flickView view
     * @param adjustX threshold x
     * @param adjustY threshold y
     */
    FlickChecker(View flickView, float adjustX, float adjustY){
        this.adjustX=adjustX;
        this.adjustY=adjustY;



        flickView.setOnTouchListener(new View.OnTouchListener(){

            //before the event
            int oldX=0, oldY=0;

            //center pos when the event occur
            int originX, originY;


            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        touchX=event.getX();
                        touchY=event.getY();

                        oldX=(int)event.getX();
                        oldY=(int)event.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        nowTouchX=event.getX();
                        nowTouchY=event.getY();
                        check();
                        v.performClick();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        originX=(int)event.getX();
                        originY=(int)event.getY();
                        onViewScroll(originX,originY, oldX, oldY);
                        //tell the event
                        oldX=originX;
                        oldY=originY;

                        break;

                    case MotionEvent.ACTION_CANCEL:
                        break;

                }
                return true;

            }


        });
    }



    private void clearVars(){
        touchX=0;
        touchY=0;

    }

    /**
     * which direciton?
     */
    private void check(){
        Log.d("FlickPoint", "startX:"+touchX+" endX:"+nowTouchX+
                " startY:" +touchY+ " endY:" +nowTouchY);

        checkFlickH();
        checkFlickV();

    }

    private void checkFlickH(){

        //left
        if(touchX>nowTouchX){
            float gapLeft=touchX-nowTouchX;
            if(gapLeft>adjustX){
                getFlick(LEFT_FLICK, gapLeft);

            }

        }else if(nowTouchX>touchX){//right
            float gapRight=nowTouchX-touchX;
            if(gapRight>adjustX){
                getFlick(RIGHT_FLICK,gapRight);

            }
        }

    }

    private void checkFlickV(){

        //up
        if(touchY>nowTouchY){
            float gapUp=touchY-nowTouchY;
            if(gapUp>adjustY){
                getFlick(UP_FLICK,gapUp);

            }

        }else if(nowTouchY>touchY){//down

            float gapDown=nowTouchY-touchY;
            if(gapDown>adjustY){
                getFlick(DOWN_FLICK,gapDown);

            }
        }
    }


    //for scroll
    private void onViewScroll(int orgX, int orgY, int oldX, int oldY){
        Log.d("scrolla","orgX="+String.valueOf(orgX)+" orgY="+
                String.valueOf(orgY)+ " oldX="+String.valueOf(oldX)+" oldY="+String.valueOf(oldY));


        checkScrollH(orgX,oldX);
        checkScrollV(orgY,oldY);


    }


    private void checkScrollH(int orgX, int oldX){
        //left
        if(orgX<oldX){
            float gapLeft=oldX-orgX;
            getFlick(LEFT_SCROLL, gapLeft);


        }else if(orgX>oldX){//right

            float gapRight=orgX-oldX;
            getFlick(RIGHT_SCROLL,gapRight);

        }

    }

    private void checkScrollV(int orgY, int oldY){
        //up
        if(orgY<oldY){
            float gapUp=oldY-orgY;
            getFlick(UP_SCROLL, gapUp);

        }else if(orgY>oldY){//down
            float gapDown=orgY-oldY;
            getFlick(DOWN_SCROLL,gapDown);

        }
    }



    /**
     * abstract method: set direction when detecting flick
     * @param swipe
     */
    public abstract void getFlick(int swipe, float gap);


}
