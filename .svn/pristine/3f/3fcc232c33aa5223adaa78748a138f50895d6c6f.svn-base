/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liang.scancode;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import butterknife.ButterKnife;
import com.liang.scancode.utils.Constant;
import com.liang.scancode.zxing.ScanListener;
import com.liang.scancode.zxing.ScanManager;
import com.liang.scancode.zxing.decode.DecodeThread;
import com.liang.scancode.zxing.decode.Utils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qdxiaogutou.boneclient.Activity.LoginActivity;
import com.qdxiaogutou.boneclient.Activity.WebActivity;
import com.qdxiaogutou.boneclient.Common.ToastUtil;
import com.qdxiaogutou.boneclient.R;
import com.qdxiaogutou.boneclient.Util.Config;
import com.qdxiaogutou.boneclient.Util.HttpUtil;


/**
 * 二维码扫描使用
 *
 * @author 刘红亮  2015年4月29日  下午5:49:45
 */
public final class CommonScanActivity extends Activity implements ScanListener, View.OnClickListener {
    static final String TAG = CommonScanActivity.class.getSimpleName();
    SurfaceView scanPreview = null;
    View scanContainer;
    View scanCropView;
    ImageView scanLine;
    ScanManager scanManager;
    TextView iv_light;
    TextView qrcode_g_gallery;
    TextView qrcode_ic_back;
    final int PHOTOREQUESTCODE = 1111;

    Button rescan;
    ImageView scan_image;
    ImageView authorize_return;
    private int scanMode;//扫描模型（条形，二维码，全部）

    TextView title;
    TextView scan_hint;
    TextView tv_scan_result;



    public static int UplaodCourierNumber = 0;
    public static int WatchOrder = 1;
    public static int SacnQRcode = 2;
    private int cur_mod = -1;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scan_code);
        ButterKnife.bind(this);
        scanMode=getIntent().getIntExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_ALL_MODE);
        init();
        initView();
        cur_mod = getIntent().getIntExtra("open_mode",-1);
    }

    private void init() {
        rescan = (Button) findViewById(R.id.service_register_rescan);
        scan_image = (ImageView) findViewById(R.id.scan_image);
        authorize_return = (ImageView) findViewById(R.id.authorize_return);
        title = (TextView) findViewById(R.id.common_title_TV_center);
        scan_hint = (TextView) findViewById(R.id.scan_hint);
        tv_scan_result = (TextView) findViewById(R.id.tv_scan_result);

        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        scanContainer = findViewById(R.id.capture_container);
        scanCropView = findViewById(R.id.capture_crop_view);
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);
        qrcode_g_gallery = (TextView) findViewById(R.id.qrcode_g_gallery);
        qrcode_g_gallery.setOnClickListener(this);
        qrcode_ic_back = (TextView) findViewById(R.id.qrcode_ic_back);
        qrcode_ic_back.setOnClickListener(this);
        iv_light = (TextView) findViewById(R.id.iv_light);
    }

    void initView() {
        switch (scanMode){
            case DecodeThread.BARCODE_MODE:
                title.setText(R.string.scan_barcode_title);
                scan_hint.setText(R.string.scan_barcode_hint);
                break;
            case DecodeThread.QRCODE_MODE:
                title.setText(R.string.scan_qrcode_title);
                scan_hint.setText(R.string.scan_qrcode_hint);
                break;
            case DecodeThread.ALL_MODE:
                title.setText(R.string.scan_allcode_title);
                scan_hint.setText(R.string.scan_allcode_hint);
                break;
        }

        iv_light.setOnClickListener(this);
        rescan.setOnClickListener(this);
        authorize_return.setOnClickListener(this);
        //构造出扫描管理器
        scanManager = new ScanManager(this, scanPreview, scanContainer, scanCropView, scanLine, scanMode,this);
    }

    @Override
    public void onResume() {
        super.onResume();
        scanManager.onResume();
        rescan.setVisibility(View.INVISIBLE);
        scan_image.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        scanManager.onPause();
    }
    /**
     *
     */
    public void scanResult(Result rawResult, Bundle bundle) {
        //扫描成功后，扫描器不会再连续扫描，如需连续扫描，调用reScan()方法。
        //scanManager.reScan();
//		Toast.makeText(that, "result="+rawResult.getText(), Toast.LENGTH_LONG).show();

        if (!scanManager.isScanning()) { //如果当前不是在扫描状态
            //设置再次扫描按钮出现
            rescan.setVisibility(View.VISIBLE);
            scan_image.setVisibility(View.VISIBLE);
            Bitmap barcode = null;
            byte[] compressedBitmap = bundle.getByteArray(DecodeThread.BARCODE_BITMAP);
            if (compressedBitmap != null) {
                barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
                barcode = barcode.copy(Bitmap.Config.ARGB_8888, true);
            }
            scan_image.setImageBitmap(barcode);
        }
        rescan.setVisibility(View.VISIBLE);
        scan_image.setVisibility(View.VISIBLE);
        tv_scan_result.setVisibility(View.VISIBLE);
        tv_scan_result.setText("结果："+rawResult.getText());

        if(cur_mod == UplaodCourierNumber){
            RequestParams p = new RequestParams();
            p.put("managerId", LoginActivity.manager.getId());
            p.put("schoolId",LoginActivity.manager.getTmp_schoolId());
            p.put("orderId",getIntent().getIntExtra("orderId",-1));
            p.put("token",LoginActivity.manager.getTmp_token());
            p.put("data",rawResult.getText());
            HttpUtil.post(Config.ajax_update_courier_number,p,new JsonHttpResponseHandler());
            finish();
        }else if(cur_mod == WatchOrder){
            Intent intent = new Intent(CommonScanActivity.this, WebActivity.class);
            intent.putExtra("url", Config.watch_order+rawResult.getText());
            startActivity(intent);
            finish();
        }else if(cur_mod == SacnQRcode){
            if(rawResult.getText().startsWith("bone_client_web://")){
                Intent intent = new Intent(CommonScanActivity.this, WebActivity.class);
                intent.putExtra("url", Config.base+rawResult.getText().substring(18));
                startActivity(intent);
                finish();
            }
        }else{
            Toast.makeText(CommonScanActivity.this,"没有明确的启动目的",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    void startScan() {
        if (rescan.getVisibility() == View.VISIBLE) {
            rescan.setVisibility(View.INVISIBLE);
            scan_image.setVisibility(View.GONE);
            scanManager.reScan();
        }
    }

    @Override
    public void scanError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        //相机扫描出错时
        if(e.getMessage()!=null&&e.getMessage().startsWith("相机")){
            scanPreview.setVisibility(View.INVISIBLE);
        }
    }

    public void showPictures(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String photo_path;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTOREQUESTCODE:
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(data.getData(), proj, null, null, null);
                    if (cursor.moveToFirst()) {
                        int colum_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        photo_path = cursor.getString(colum_index);
                        if (photo_path == null) {
                            photo_path = Utils.getPath(getApplicationContext(), data.getData());
                        }
                        scanManager.scanningImage(photo_path);
                    }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qrcode_g_gallery:
                showPictures(PHOTOREQUESTCODE);
                break;
            case R.id.iv_light:
                scanManager.switchLight();
                break;
            case R.id.qrcode_ic_back:
                finish();
                break;
            case R.id.service_register_rescan://再次开启扫描
                startScan();
                break;
            case R.id.authorize_return:
                finish();
                break;
            default:
                break;
        }
    }

}