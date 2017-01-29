package com.qdxiaogutou.boneclient.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qdxiaogutou.boneclient.Adapter.SchoolItemAdapter;
import com.qdxiaogutou.boneclient.Entity.School;
import com.qdxiaogutou.boneclient.R;
import com.qdxiaogutou.boneclient.Util.Config;
import com.qdxiaogutou.boneclient.Util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ChangeSchool extends AppCompatActivity {

    private ListView schoolList;
    private ArrayList<School> schools;
    private SchoolItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_school);
        setTitle("变更学校");
        schoolList = (ListView) findViewById(R.id.schoolList);
        InitData();
        InitAction();
    }

    private void InitAction() {
        schoolList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                School e = schools.get(position);
                LoginActivity.manager.setTmp_schoolId(e.getId());
                LoginActivity.manager.setTmp_schoolName(e.getSchoolName());
                finish();
            }
        });
    }

    private void InitData() {
        RequestParams p = new RequestParams();
        p.put("managerId",LoginActivity.manager.getId());
        p.put("token",LoginActivity.manager.getTmp_token());
        HttpUtil.post(Config.listSchool,p,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSONObject.parseObject(response.toString());
                LoginActivity.manager.setTmp_token(data.getString("token"));
                schools = (ArrayList<School>) com.alibaba.fastjson.JSONObject.parseArray(data.getString("entity"),School.class);
                adapter = new SchoolItemAdapter(schools,ChangeSchool.this);
                schoolList.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

}
