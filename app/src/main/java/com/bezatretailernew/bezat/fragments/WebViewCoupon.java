package com.bezatretailernew.bezat.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.utils.SharedPrefs;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewCoupon extends Fragment {

    private String url;
    ImageView imgBack;
    String lang = "";


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
        if (SharedPrefs.getKey(getActivity(), "selectedlanguage").contains("ar")) {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            lang = "_ar";
        } else {
            getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            lang = "";
        }
        if(lang.equals("_ar")){
            imgBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back_rtl));
        }
        view.getSettings().setLoadWithOverviewMode(true);
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setBuiltInZoomControls(true);
        view.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("when you click on any interlink on webview that time you got url :-" + url);
                if(url.equals("https://bezatapp.com/manage_App/admin/mobile_app/success")){
                    Log.d("---Found---",url);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                        fm.popBackStack();
                    }
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        view.loadUrl(url);

        imgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return hview;
    }
}