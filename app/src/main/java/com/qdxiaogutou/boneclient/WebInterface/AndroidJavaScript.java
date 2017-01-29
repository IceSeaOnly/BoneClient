package com.qdxiaogutou.boneclient.WebInterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.liang.scancode.CommonScanActivity;
import com.liang.scancode.utils.Constant;
import com.qdxiaogutou.boneclient.Activity.LoginActivity;
import com.qdxiaogutou.boneclient.Activity.makeQrCode;
import com.qdxiaogutou.boneclient.Util.util;

import cn.pedant.SweetAlert.SweetAlertDialog;

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


}
