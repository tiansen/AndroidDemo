package com.example.ts.slifdemo.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ts.slifdemo.MainActivity;
import com.example.ts.slifdemo.R;

public class Login extends Activity {
    EditText name, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //初始化数据
        name = (EditText) findViewById(R.id.login_user_edit);
        pwd = (EditText) findViewById(R.id.login_passwd_edit);

    }

    public void login(View view) {
        if (name.getText().toString() == null || "".equals(name.getText().toString()) || pwd.getText().toString() == null || "".equals(pwd.getText().toString())) {
            Toast.makeText(Login.this,"用户名和密码不能为空",Toast.LENGTH_LONG).show();
        }
        if (name.getText().toString().equals("admin")&&pwd.getText().toString().equals("admin")) {
            writeLoginState(name.getText().toString(),pwd.getText().toString());
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 记录登录数据
     * @param name 用户名
     * @param pwd 密码
     */
    public void writeLoginState(String name, String pwd) {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("name", name);
        editor.putString("pwd", pwd);
        editor.commit();//提交修改
    }

}
