package com.example.wen.wenbook.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Gank;
import com.example.wen.wenbook.ui.widget.MyWebViewClient;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.web_toolbar)
    Toolbar mWebToolbar;
    @BindView(R.id.webview_pb)
    ProgressBar mWebviewPb;
    @BindView(R.id.web_content)
    WebView mWebContent;
    @BindView(R.id.load_error)
    LinearLayout mLoadError;

    private Gank mGank;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mUnbinder = ButterKnife.bind(this);

        initWebView();

        init();

    }

    private void init() {

        setSupportActionBar(mWebToolbar);
        ActionBar supportActionBar = getSupportActionBar();

        mGank = (Gank) getIntent().getSerializableExtra("gank");
        if (supportActionBar != null && mGank != null){

            supportActionBar.setDisplayHomeAsUpEnabled(true);

            //标题
            supportActionBar.setTitle(mGank.desc);
            supportActionBar.setSubtitle(mGank.who);

            mWebContent.loadUrl(mGank.url);
        }else {
            loadError();
        }

    }

    private void initWebView() {
        WebSettings settings = mWebContent.getSettings();

        settings.setJavaScriptEnabled(true);
        //适应屏幕
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
       // settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        settings.setDisplayZoomControls(true);//支持缩放

        mWebContent.setWebChromeClient(new MyWebViewClient(mWebviewPb));

        mWebContent.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                //出错，显示errorView
                loadError();
            }
        });

    }

    private void loadError() {
        mWebContent.setVisibility(View.GONE);
        mLoadError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.load_error)
    public void errorClick(View view){
        //出错点击重新加载
        errorReload();
    }

    private void errorReload() {
        mWebContent.setVisibility(View.VISIBLE);
        mLoadError.setVisibility(View.GONE);
        mWebContent.loadUrl(mGank.url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (mWebContent.canGoBack()){
                    //返回键，可以回退，则回退，否则退出
                    mWebContent.goBack();
                }else {
                    this.finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (mWebContent.canGoBack()){
                //返回键，可以回退，则回退，否则退出
                mWebContent.goBack();
                return true;
            }else {
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
    }
}
