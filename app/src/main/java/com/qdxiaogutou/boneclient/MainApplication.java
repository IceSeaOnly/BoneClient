package com.qdxiaogutou.boneclient;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * Created by Administrator on 2017/1/7.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initCloudChannel(this);
        Bugly.init(getApplicationContext(), "3cc46a4f51", false);
        CrashReport.initCrashReport(getApplicationContext(), "3cc46a4f51", false);
        initX5Browse();
    }

    public static CloudPushService pushService;
    /**
     * 初始化云推送通道
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response){
                Log.i("AliPush","success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.i("AliPush","failed"+errorCode+","+errorMessage);
                Log.i("AliPush","DeviceId=  "+pushService.getDeviceId());
            }
        });
    }


    private void initX5Browse() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        //TbsDownloader.needDownload(getApplicationContext(), false);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                Log.e("app", " onViewInitFinished is " + arg0);
            }
            @Override
            public void onCoreInitFinished() {
                Log.e("app", "onCoreInitFinished");
            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.d("app","onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d("app","onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d("app","onDownloadProgress:"+i);
            }
        });

        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }
}
