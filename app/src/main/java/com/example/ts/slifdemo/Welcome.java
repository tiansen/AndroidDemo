package com.example.ts.slifdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ts.slifdemo.login.Login;
import com.example.ts.slifdemo.login.Register;

import java.util.HashMap;

public class Welcome extends Activity {
    String name = "";
    String pwd = "";
    private static final String TAG = "MyLinearLayout";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        readLoginState();
        Log.i(TAG,name +"___"+pwd);
        if(!"".equals(name)&&!"".equals(pwd)){
            Intent intent = new Intent(Welcome.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
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
        name = preferences.getString("name", "");
        pwd = preferences.getString("pwd", "");
    }
}
