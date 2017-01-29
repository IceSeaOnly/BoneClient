package com.qdxiaogutou.boneclient.Common;

import android.content.Context;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/11/26.
 */
public class ToastUtil {
    public void shortToast(String msg, Context context){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public void noticeAlert(Context context,String msg){
        new SweetAlertDialog(context)
                .setTitleText(msg)
                .show();
    }

    public void errorAlert(Context context,String msg){
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("错误")
                .setContentText(msg)
                .show();
    }

    public void warningAlert(Context context, String msg, final SingleCallBack callBack){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("警告")
                .setContentText(msg)
                .setCancelText("取消")
                .setConfirmText("确认")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        callBack.callback();
                    }
                })
                .show();
    }

    public void successAlert(Context context,String msg){
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("完成")
                .setContentText(msg)
                .show();
    }
}
