package com.qdxiaogutou.boneclient.Activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.liang.scancode.zxing.encode.EncodingHandler;
import com.qdxiaogutou.boneclient.R;

import java.io.UnsupportedEncodingException;

public class makeQrCode extends AppCompatActivity {

    ImageView qrcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_qr_code);
        qrcode = (ImageView) findViewById(R.id.qr_code);
        String data = getIntent().getStringExtra("data");
        qrcode.setImageBitmap(create2Code(data));
    }

    /**
     * 生成二维码
     */
    private Bitmap create2Code(String key) {
        Bitmap qrCode=null;
        try {
            qrCode= EncodingHandler.create2Code(key, 400);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(makeQrCode.this,"参数错误",Toast.LENGTH_SHORT).show();
        }
        return qrCode;
    }
}
