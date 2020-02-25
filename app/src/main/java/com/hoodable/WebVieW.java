package com.hoodable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebVieW extends AppCompatActivity {
  private WebVieW web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_vie_w);
        WebView browser = (WebView) findViewById(R.id.webview);
        browser.loadUrl("http://www.tutorialspoint.com");
        browser.setWebViewClient(new MyBrowser());
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
