package com.qdxiaogutou.boneclient.Activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.qdxiaogutou.boneclient.Util.Config;
import com.qdxiaogutou.boneclient.Util.util;
import com.qdxiaogutou.boneclient.WebInterface.AndroidJavaScript;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import com.qdxiaogutou.boneclient.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WebActivity extends Activity {
    private final Set<String> offlineResources = new HashSet<>();
    private WebView web_view;
    private ProgressBar pg1;
    private String TAG = "WebActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web);
        fetchOfflineResources();
        String url = util.REPLACE_URL(getIntent().getStringExtra("url"));
        InitWebView();
        InitClient();
        //加载远程网页
        if (null != savedInstanceState) {
            web_view.restoreState(savedInstanceState);
        } else {
            web_view.loadUrl(url);
        }
    }



    private void InitWebView() {
        pg1=(ProgressBar) findViewById(R.id.progressBar1);
        web_view = (WebView) findViewById(R.id.web_view);
        WebSettings settings = web_view.getSettings();
        // 设置支持javascript
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);//设置是否显示缩放工具
        settings.setSupportZoom(false);//设置是否支持缩放
        settings.setDefaultFontSize(15);
        settings.setSupportZoom(false); // 设置是否支持变焦

        // 增加接口方法,让html页面调用
        AndroidJavaScript aj = new AndroidJavaScript(WebActivity.this, new AndroidJavaScript.JSCallBack() {
            @Override
            public void reload(String oldToken) {
                web_view.loadUrl(web_view.getUrl().replace(oldToken,LoginActivity.manager.getTmp_token()));
            }

            @Override
            public void loadUrl(String url) {
                web_view.loadUrl(Config.base+url);
            }
        });
        web_view.addJavascriptInterface(aj, "icesea");
    }

    private boolean web_err = false;
    private void fetchOfflineResources() {
        AssetManager am = getAssets();
        try {
            String[] res = am.list("offline_res");
            if(res != null) {
                Collections.addAll(offlineResources, res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void InitClient() {
        web_view.setWebViewClient(new WebViewClient(){
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                int lastSlash = url.lastIndexOf("/");
                if(lastSlash != -1) {
                    String suffix = url.substring(lastSlash + 1);
                    if(offlineResources.contains(suffix)) {
                        String mimeType;
                        if(suffix.endsWith(".js")) {
                            mimeType = "application/x-javascript";
                        } else if(suffix.endsWith(".css")) {
                            mimeType = "text/css";
                        } else {
                            mimeType = "text/html";
                        }
                        Log.i(TAG,suffix+" is replaced as local.");
                        try {
                            InputStream is = getAssets().open("offline_res/" + suffix);
                            return new WebResourceResponse(mimeType, "UTF-8", is);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return super.shouldInterceptRequest(view, url);
            }

        });


        web_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                super.onProgressChanged(webView, newProgress);

                if(newProgress==100){
                    pg1.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pg1.setProgress(newProgress);//设置进度值
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        web_view.saveState(outState);
    }
}
