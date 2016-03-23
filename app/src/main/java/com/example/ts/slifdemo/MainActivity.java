package com.example.ts.slifdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ts.slifdemo.login.Login;
import com.example.ts.slifdemo.myLayout.MyLinearLayout;

import java.util.HashMap;

public class MainActivity extends Activity {

    MyLinearLayout myLL;
    Button left;
    public static int screenWidth;
    public static int scrrenHeight;
    LinearLayout leftItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myLL = (MyLinearLayout) findViewById(R.id.fl);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;
        scrrenHeight = metric.heightPixels;
        leftItem = (LinearLayout) findViewById(R.id.left);
        //添加一个监听，当此控件被重绘的时候获取出空间的宽度，直接在此处回去的时候是空值
        ViewTreeObserver vto = leftItem.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                int width = leftItem.getMeasuredWidth();
                myLL.setLeftWidth(width);
                return true;
            }

        });

    }

    public void move(View view) {
        System.out.println("move");
        myLL.smoothScrollTo();
    }

    /**
     * 相应登录按钮
     *
     * @param view
     */
    public void login(View view) {
        System.out.println("login");
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }





}
