package com.example.travelapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;

public class WebsiteDetail extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_detail);
        WebView mWebview = (WebView) findViewById(R.id.wbWebsite);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mWebview.getSettings().setLoadsImagesAutomatically(true);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.loadUrl(url);

    }
}