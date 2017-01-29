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
        url = url.replace("MANAGERID",String.valueOf(LoginActivity.manager.getId()));
        url = url.replace("PHONE",String.valueOf(LoginActivity.manager.getPhone()));
        url = url.replace("SCHOOLID",String.valueOf(LoginActivity.manager.getTmp_schoolId()));
        url = url.replace("TOKEN",LoginActivity.manager.getTmp_token());
        url = url.replace("SCHOOLNAME",LoginActivity.manager.getTmp_schoolName());
        return url;
    }
}
