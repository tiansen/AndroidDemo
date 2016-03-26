package com.example.ts.slifdemo.update;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.app.Activity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import com.example.ts.slifdemo.R;


import java.util.logging.LogRecord;

public class ShakeActivity extends Activity {
    public static String TAG = "ShakeActivity";
    ShakeListener mShakeListener = null;
    Vibrator mVibrator;
    private RelativeLayout mImgUp;
    private RelativeLayout mImgDn;
    Handler handler;
    boolean finish = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shake_activity);

        mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);

        mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
        mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown);

        new Thread(){
            @Override
            public void run() {
                super.run();

                synchronized (ShakeActivity.this){
                    try {
                        Log.i(TAG,"线程1等待");
                        ShakeActivity.this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG,"线程1完成");
                    finish = true;
                }



            }
        }.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(){
            @Override
            public void run() {
                super.run();

                synchronized (ShakeActivity.this){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG,"线程2完成");
                    ShakeActivity.this.notifyAll();
                }
            }
        }.start();

        mShakeListener = new ShakeListener(this);
        startAnim();  //开始 摇一摇手掌动画
        mShakeListener.stop();
        startVibrato(); //开始 震动


    }

    public void startAnim() {   //定义摇一摇动画动画
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f);
        mytranslateanimup0.setDuration(1000);
        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.5f);
        mytranslateanimup1.setDuration(1000);
        mytranslateanimup1.setStartOffset(1000);
        animup.addAnimation(mytranslateanimup0);
        animup.addAnimation(mytranslateanimup1);
        mImgUp.startAnimation(animup);

        AnimationSet animdn = new AnimationSet(true);
        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.5f);
        mytranslateanimdn0.setDuration(1000);
        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f);
        mytranslateanimdn1.setDuration(1000);
        mytranslateanimdn1.setStartOffset(1000);
        animdn.addAnimation(mytranslateanimdn0);
        animdn.addAnimation(mytranslateanimdn1);
        mImgDn.startAnimation(animdn);
        //监听动画执行完毕
        animdn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(!finish){
                    startAnim();
                }else{
                    ShakeActivity.this.finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void startVibrato() {        //定义震动
        mVibrator.vibrate(new long[]{500, 200, 500, 200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"振动结束");
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }
}