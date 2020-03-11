package com.bezatretailernew.bezat.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bezatretailernew.bezat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewCoupon extends Fragment {

    private String url;
    ImageView imgBack;


    public WebViewCoupon(String url) {
        this.url = url;
    }

    public WebViewCoupon() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View hview = inflater.inflate(R.layout.fragment_web_view_coupon, container, false);
        WebView view = hview.findViewById(R.id.help_webview);
        WebSettings webSettings = view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        imgBack = hview.findViewById(R.id.imgBack);
        view.getSettings().setLoadWithOverviewMode(true);
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setBuiltInZoomControls(true);
        view.setWebViewClient(new WebViewClient());
        view.loadUrl(url);

        imgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return hview;
    }
}