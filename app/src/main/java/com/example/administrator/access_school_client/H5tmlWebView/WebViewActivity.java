package com.example.administrator.access_school_client.H5tmlWebView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.administrator.access_school_client.R;

public class WebViewActivity extends AppCompatActivity {

    private Html5Webview webview;
    String bannerurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        bannerurl = getIntent().getStringExtra("url");

        webview = (Html5Webview) findViewById(R.id.h5wv);

        webview.loadUrl(bannerurl);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        } else {
            //程序退出
            System.exit(0);
            return false;
        }
    }
}
