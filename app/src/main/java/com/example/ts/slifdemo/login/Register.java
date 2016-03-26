package com.example.ts.slifdemo.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ts.slifdemo.R;
import com.example.ts.slifdemo.utils.DialogUtil;
import com.example.ts.slifdemo.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class Register extends Activity {
    EditText registerName, registerPwd, registerSex, registerLabel;
    final String TAG = "Register";
    private String response = "";
    JSONArray responseArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        registerName = (EditText) findViewById(R.id.register_user_edit);
        registerPwd = (EditText) findViewById(R.id.register_passwd_edit);
        registerSex = (EditText) findViewById(R.id.register_sex_edit);
        registerLabel = (EditText) findViewById(R.id.register_label_edit);
        registerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    Log.i(TAG, "获得焦点");
                } else {
                    validateNameUnique();
                    Log.i(TAG, "失去焦点");
                }
            }
        });
        Log.i(TAG, "用户名已存在" + response);
        //测试服务器是否能连接
        try {
            response = HttpUtil.getRequest(HttpUtil.BASE_URL + "/note/test.jsp");
            Log.i(TAG, response);
            if (response == null || "".equals(response)) {
                connFail();
            }

        } catch (Exception e) {
            connFail();
            Log.i(TAG, "连接服务器失败");
        }
    }

    private void connFail() {
        DialogUtil.showDialog(this, "连接服务器失败", true);
    }

    public void register(View view) {
        String name = registerName.getText().toString();
        String pwd = registerPwd.getText().toString();
        String sex = registerSex.getText().toString();
        String label = registerLabel.getText().toString();
        if (!validate(name) && !validate(pwd) && !validate(sex) && !validate(label)) {
            if (!validateNameUnique()) {
                return;
            }
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("name", name);
            params.put("pwd", pwd);
            params.put("sex", sex);
            params.put("label", label);
            try {
                String result = HttpUtil.postRequest(HttpUtil.BASE_URL + "/note/addUser.jsp", params);
                if ("OK".equals(result.toString().trim())) {
                    Toast.makeText(Register.this, "注册成功", Toast.LENGTH_LONG).show();
                    new AlertDialog.Builder(Register.this)
                            .setMessage("注册成功，请登陆").setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Register.this.finish();
                        }
                    }).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            DialogUtil.showDialog(this, "不能为空", false);
        }
    }

    /**
     * 验证输入是否为空
     *
     * @param str
     * @return
     */
    boolean validate(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 验证用户名的唯一性
     *
     * @return
     */
    boolean validateNameUnique() {
        String name = registerName.getText().toString();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("name", name);
        try {
            response = HttpUtil.postRequest(HttpUtil.BASE_URL + "/note/getName.jsp", param);
            Log.i(TAG, "结果" + response.toString().trim());
            //妈蛋，忘了去除\n\r
            if ("exists".equals(response.toString().trim())) {
                DialogUtil.showDialog(Register.this, "用户名已存在:" + name, false);
                registerName.setText("");
                return false;
            }
        } catch (Exception e) {
            Log.i(TAG, "连接服务器失败:getName");
            DialogUtil.showDialog(Register.this, "服务器连接异常", false);
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
