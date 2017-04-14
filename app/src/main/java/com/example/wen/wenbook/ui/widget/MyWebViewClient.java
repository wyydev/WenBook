package com.example.wen.wenbook.ui.widget;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by wen on 2017/4/14.
 */

public class MyWebViewClient extends WebChromeClient {

    private ProgressBar pb;

    public MyWebViewClient(ProgressBar pb){
        this.pb=pb;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        pb.setProgress(newProgress);
        if(newProgress==100){
            pb.setVisibility(View.GONE);
        }
        super.onProgressChanged(view, newProgress);
    }

}
