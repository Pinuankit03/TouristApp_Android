package com.example.travelapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelapp.R;

public class MapFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_map, container, false);

        WebView webView = (WebView) view.findViewById(R.id.webViewMap);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        // Configure the client to use when opening URLs
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.bing.com/maps?osid=c80bfd9d-3fa8-466b-968d-91266ef2674e&cp=53.549453~-113.543615&lvl=13&v=2&sV=2&form=S00027");
        return view;
    }
}
