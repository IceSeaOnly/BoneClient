package com.qdxiaogutou.boneclient.WebInterface;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.util.Log;
import android.webkit.JavascriptInterface;


import com.liang.scancode.CommonScanActivity;
import com.liang.scancode.utils.Constant;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qdxiaogutou.boneclient.Activity.LoginActivity;
import com.qdxiaogutou.boneclient.Activity.makeQrCode;
import com.qdxiaogutou.boneclient.Util.Config;
import com.qdxiaogutou.boneclient.Util.HttpUtil;
import com.qdxiaogutou.boneclient.Util.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/9/19.
 */
public class AndroidJavaScript {
    private Context context;
    private Activity activity;
    private String TAG = "WebInterface";
    private JSCallBack callBack;
    private static String oldToken;

    public interface JSCallBack {
        void reload(String oldToken);
        void loadUrl(String url);
        void processing(String msg);
        void processComplete(String msg);
    }
    public AndroidJavaScript(Activity a,JSCallBack callBack) {
        activity = a;
        context = a;
        this.callBack = callBack;
    }

    /**
     * 生成二维码
     * */
    @JavascriptInterface
    public void makeQRcode(String data){
        Intent intent = new Intent(activity,makeQrCode.class);
        intent.putExtra("data",data);
        activity.startActivity(intent);
    }
  /**
     * 扫描二维码
     * */
    @JavascriptInterface
    public void scanQRcode(){
        Intent intent=new Intent(activity,CommonScanActivity.class);
        intent.putExtra("open_mode",2);
        intent.putExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_QRCODE_MODE);
        activity.startActivity(intent);
    }

    /**
     * 更新token
     * */
    @JavascriptInterface
    public void updateToken(String token){
        Log.i(TAG,"Token update.");
        oldToken = LoginActivity.manager.getTmp_token();
        LoginActivity.manager.setTmp_token(token.trim());
    }

    /**
     * 向服务器提交订单的快递单号
     * */
    @JavascriptInterface
    public void uploadCourierNumber(int orderId){
        Intent intent=new Intent(activity,CommonScanActivity.class);
        intent.putExtra("open_mode",0);
        intent.putExtra("orderId",orderId);
        intent.putExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_BARCODE_MODE);
        activity.startActivity(intent);
    }

    /**
     * 重新加载网页
     * */
    @JavascriptInterface
    public void reload(){
        callBack.reload(oldToken);
    }

    /**
     * 打开其他网页
     * */
    @JavascriptInterface
    public void loadUrl(String url){
        callBack.loadUrl(util.REPLACE_URL(url));
    }
    /**
     * 拨打电话的方法
     */
    @JavascriptInterface
    public void call(String num) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + num));
        context.startActivity(intent);
    }

    /**
     * 关闭浏览器
     */

    @JavascriptInterface
    public void finish() {
        activity.finish();
    }

    /**
     * 成功提示并关闭浏览器
     */

    @JavascriptInterface
    public void finish_success(String msg) {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("完成")
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        activity.finish();
                    }
                })
                .show();
    }

    /**
     * 成功错误并关闭浏览器
     */

    @JavascriptInterface
    public void finish_error(String msg) {
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("错误")
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        activity.finish();
                    }
                })
                .show();
    }

    /**
     * 仅提示成功
     * */
    @JavascriptInterface
    public void notice_success(String msg) {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("提示")
                .setContentText(msg)
                .show();
    }

    /**
     * 仅提示错误
     * */
    @JavascriptInterface
    public void notice_error(String msg) {
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("提示")
                .setContentText(msg)
                .show();
    }

    /**
     * 复制内容到剪贴板
     * */
    @JavascriptInterface
    public void copy2clipboard(String content) {
        ClipboardManager cm = (ClipboardManager)activity.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("bone_client_url", content));
    }


    /**
     * 调起支付页面
     * */
    @JavascriptInterface
    public void start2pay(int orderid){
        callBack.processing("正在支付...");
        RequestParams p = new RequestParams();
        p.put("orderId",orderid);
        p.put("managerId",LoginActivity.manager.getId());
        p.put("token",LoginActivity.manager.getTmp_token());
        HttpUtil.get(Config.payUrl, p,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Log.e("PayError",response.getString("result"));
                    callBack.processComplete(response.getString("result"));
                } catch (JSONException e) {
                    callBack.processComplete(response.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                callBack.processComplete("支付系统出现问题");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                callBack.processComplete("支付系统出现问题");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                callBack.processComplete("支付系统出现问题");
            }
        });
    }
}
