package com.qdxiaogutou.boneclient.Util;

import com.qdxiaogutou.boneclient.Activity.LoginActivity;

/**
 * Created by Administrator on 2017/1/20.
 */
public class util {
    /**
     * 将url中大写的变量标示替换为变量
     * */
    public static String REPLACE_URL(String url) {
        if(url.contains("MANAGERID")){
            url = url.replace("MANAGERID",String.valueOf(LoginActivity.manager.getId()));
        }
        if(url.contains("PHONE")){
            url = url.replace("PHONE",String.valueOf(LoginActivity.manager.getPhone()));
        }
        if(url.contains("SCHOOLID")){
            url = url.replace("SCHOOLID",String.valueOf(LoginActivity.manager.getTmp_schoolId()));
        }
        if(url.contains("TOKEN")){
            url = url.replace("TOKEN",LoginActivity.manager.getTmp_token());
        }
        if(url.contains("SCHOOLNAME")){
            url = url.replace("SCHOOLNAME",LoginActivity.manager.getTmp_schoolName());
        }
        return url;
    }
}
