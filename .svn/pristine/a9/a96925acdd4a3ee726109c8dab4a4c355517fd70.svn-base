package com.qdxiaogutou.boneclient.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qdxiaogutou.boneclient.Common.MD5;
import com.qdxiaogutou.boneclient.Common.Process;
import com.qdxiaogutou.boneclient.Common.ToastUtil;
import com.qdxiaogutou.boneclient.Entity.Manager;

import com.qdxiaogutou.boneclient.R;
import com.qdxiaogutou.boneclient.Util.Config;
import com.qdxiaogutou.boneclient.Util.HttpUtil;


import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class LoginActivity extends AppCompatActivity {

    public static Manager manager = null;
    private EditText phone;
    private EditText password;
    private Button btnLogin;
    private CheckBox checkbox;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        checkbox = (CheckBox) findViewById(R.id.checkBox);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph = phone.getText().toString();
                String pa = password.getText().toString();
                if(ph.length() != 11 || pa.length() < 6){
                    new ToastUtil().shortToast("输入不合法",LoginActivity.this);
                }else
                    doLogin(ph, MD5.encryption(pa));
            }
        });

        String ph = sp.getString("PHONE","");
        String pw = sp.getString("PASSWORD","");
        if(ph.length() == 11 && pw.length() > 5)
            doLogin(ph,pw);
    }

    private void doLogin(final String ph, final String encryption) {
        if(checkbox.isChecked()){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("PHONE", ph);
            editor.putString("PASSWORD", encryption);
            editor.commit();
        }

        Process.onProcessing(LoginActivity.this,"正在登录...",false);
        RequestParams p = new RequestParams();
        p.put("phone",ph);
        p.put("pass",encryption);
        HttpUtil.post(Config.login,p,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject resp) {
                super.onSuccess(statusCode, headers, resp);
                Process.dismiss();
                com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSONObject.parseObject(resp.toString());
                if(!data.getBoolean("result")){
                    new ToastUtil().errorAlert(LoginActivity.this,"登录失败，请检查用户名/密码，也可能该账号暂时无法使用");
                }else{
                    manager = data.getObject("entity",Manager.class);
                    Toast.makeText(LoginActivity.this,"欢迎登录"+manager.getName(),Toast.LENGTH_SHORT);
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Process.dismiss();
                new ToastUtil().errorAlert(LoginActivity.this,"系统异常，无法登录E-1");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Process.dismiss();
                new ToastUtil().errorAlert(LoginActivity.this,"系统异常，无法登录E-2");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Process.dismiss();
                new ToastUtil().errorAlert(LoginActivity.this,"系统异常，无法登录E-3");
            }
        });
    }
}
