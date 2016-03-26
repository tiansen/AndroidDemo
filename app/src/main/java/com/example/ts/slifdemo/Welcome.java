package com.example.ts.slifdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ts.slifdemo.login.Login;
import com.example.ts.slifdemo.login.Register;
import com.example.ts.slifdemo.notes.Notes;

import java.util.HashMap;

public class Welcome extends Activity {
    private static final String TAG = "MyLinearLayout";
    public static Activity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        readLoginState();
        Log.i(TAG, Notes.name +"___"+Notes.pwd);
        if(!"".equals(Notes.name)&&!"".equals(Notes.pwd)){
            Intent intent = new Intent(Welcome.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        instance = this;
    }

    /**
     * 登录处理
     * @param v
     */
    public void welcome_login(View v) {
        Intent intent = new Intent();
        intent.setClass(Welcome.this, Login.class);
        startActivity(intent);
        //this.finish();
    }

    /**
     * 注册的处理
     * @param v
     */
    public void welcome_register(View v) {
        Intent intent = new Intent();
        intent.setClass(Welcome.this, Register.class);
        startActivity(intent);
        //this.finish();
    }

    /**
     * 登录数据的读取
     * @return 返回登录数据
     */
    public void readLoginState() {
        SharedPreferences preferences = getSharedPreferences("userInfo",
                Activity.MODE_PRIVATE);
        Notes.name = preferences.getString("name", "");
        Notes.pwd = preferences.getString("pwd", "");
    }
}
