package com.qdxiaogutou.boneclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.liang.scancode.CommonScanActivity;
import com.liang.scancode.utils.Constant;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qdxiaogutou.boneclient.Adapter.IndexItemAdapter;
import com.qdxiaogutou.boneclient.Common.Process;
import com.qdxiaogutou.boneclient.Common.SingleCallBack;
import com.qdxiaogutou.boneclient.Common.ToastUtil;
import com.qdxiaogutou.boneclient.Entity.IndexItemEntity;
import com.qdxiaogutou.boneclient.R;
import com.qdxiaogutou.boneclient.Util.Config;
import com.qdxiaogutou.boneclient.Util.HttpUtil;
import com.tencent.smtt.sdk.WebView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private WebView weather_show;
    private GridView index_gridview;
    private IndexItemAdapter adapter;
    private ArrayList<IndexItemEntity> items = null;
    private String TAG = "MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weather_show = (WebView) findViewById(R.id.weather_show);
        index_gridview = (GridView) findViewById(R.id.index_gridview);
        weather_show.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        weather_show.loadUrl("http://weixin.qdxiaogutou.com/header.php");
        InitAction();
    }

    private void InitData() {
        if(items != null)
            return;
        Process.onProcessing(MainActivity.this,"请稍等...",false);
        RequestParams p = new RequestParams();
        p.put("managerId",LoginActivity.manager.getId());
        p.put("token",LoginActivity.manager.getTmp_token());
        HttpUtil.post(Config.listFunction,p,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Process.dismiss();
                com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSONObject.parseObject(response.toString());
                items = (ArrayList<IndexItemEntity>) com.alibaba.fastjson.JSONObject.parseArray(data.getString("entity"),IndexItemEntity.class);
                completeFunctionList();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Process.dismiss();
                new ToastUtil().errorAlert(MainActivity.this,"E-11 功能列表下载失败，请重新登录以重试");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Process.dismiss();
                new ToastUtil().errorAlert(MainActivity.this,"E-12 功能列表下载失败，请重新登录以重试");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Process.dismiss();
                new ToastUtil().errorAlert(MainActivity.this,"E-13 功能列表下载失败，请重新登录以重试");
            }
        });

    }

    private void completeFunctionList(){
        /** 补足3的倍数*/
        int mod = items.size() % 3;
        if (mod > 0)
            for (int i = 0; i < 3 - mod; i++) {
                items.add(new IndexItemEntity(-1, null, "未启用", null,null));
            }
        adapter = new IndexItemAdapter(items, this);
        index_gridview.setAdapter(adapter);
    }

    private void InitAction() {
        index_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickFunction(items.get(position).getFunction(),items.get(position).getFunctionUrl());
            }
        });
    }

    /**
     * 根据function唤起不同的activity
     * url有效的转向web
     */
    private void onClickFunction(String function,String url) {
        if (url != null && url.length() > 0){
            Intent intent = new Intent(MainActivity.this,WebActivity.class);
            intent.putExtra("url",url);
            startActivity(intent);
            return;
        }

        if (function == null) {
            Toast.makeText(this, "动作未启用", Toast.LENGTH_SHORT).show();
            return;
        }

        if (function.equals("logout")) {
            new ToastUtil().warningAlert(this, "你确定退出当前账户?", new SingleCallBack() {
                @Override
                public void callback() {
                    SharedPreferences sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("PHONE", "");
                    editor.putString("PASSWORD", "");
                    editor.commit();
                    LoginActivity.manager = null;
                    items = null;
                    PushServiceFactory.getCloudPushService().unbindAccount(new CommonCallback() {@Override public void onSuccess(String s) {}@Override public void onFailed(String s, String s1) {}});
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
            return;
        }

        if (function.equals("change_school")) {
            startActivity(new Intent(MainActivity.this, ChangeSchool.class));
        }
        if (function.equals("scan_code")) {
            Intent intent=new Intent(this,CommonScanActivity.class);
            intent.putExtra("open_mode",1);
            intent.putExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_BARCODE_MODE);
            startActivity(intent);
        }

        if(function.equals("make_qr_code")){
            Intent intent = new Intent(MainActivity.this,makeQrCode.class);
            intent.putExtra("data","hello world.");
            startActivity(intent);
        }

        if(function.equals("scan_qr_code")){
            Intent intent=new Intent(this,CommonScanActivity.class);
            intent.putExtra("open_mode",2);
            intent.putExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_QRCODE_MODE);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginActivity.manager == null)
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        else {
            setTitle("小骨头综合调度-" + LoginActivity.manager.getTmp_schoolName());
            if(items == null)
                InitData();
        }
    }
}
