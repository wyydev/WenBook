package com.example.wen.wenbook.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    @BindView(R.id.tv_web_view_error)
    TextView mTvWebViewError;

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
        if (supportActionBar != null && mGank != null) {

            supportActionBar.setDisplayHomeAsUpEnabled(true);

            //标题
            supportActionBar.setTitle(mGank.desc);
            supportActionBar.setSubtitle(mGank.who);
            mWebContent.loadUrl(mGank.url);
        } else {
            loadError("出错了点击重试");
        }

    }

    private void initWebView() {
        WebSettings settings = mWebContent.getSettings();

      /*  settings.setJavaScriptEnabled(true);
        //适应屏幕
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);//设定支持缩放
        settings.setDisplayZoomControls(true);//支持缩放*/

        //java script
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // access Assets and resources
        settings.setAllowFileAccess(true);
        //zoom page
        settings.setSupportZoom(true);//设定支持缩放
       // settings.setBuiltInZoomControls(true);
       // settings.setPluginsEnabled(true);
        //set xml dom cache
        settings.setDomStorageEnabled(true);
        //提高渲染的优先级
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        mWebContent.setWebChromeClient(new MyWebViewClient(mWebviewPb));


        mWebContent.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

           /*     if (openWithWevView(url)) {//如果是超链接，执行此方法
                    view.loadUrl(url);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return true;*/

                if(url == null) return false;

                try {
                    if(url.startsWith("weixin://") || url.startsWith("aliplays://") ||
                            url.startsWith("mailto://") || url.startsWith("tel://")||url.startsWith("miaopai://")||
                            url.startsWith("bilibili://")
                        //其他自定义的scheme
                            ) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }

                //处理http和https开头的url
                view.loadUrl(url);
                return true;

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                loadError("错误码："+errorCode+"\n"+"无法连接以下链接：\n"+failingUrl);
            }

        });

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mWebContent.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mWebContent.onPause();
    }


    protected boolean openWithWevView(String url) {//处理判断url的合法性
        if (url.startsWith("http:") || url.startsWith("https:")) {
            return true;
        }

        return false;


    }

    private void loadError(String error) {
        mWebContent.setVisibility(View.GONE);
        mTvWebViewError.setText(error);
        mLoadError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.load_error)
    public void errorClick(View view) {
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
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mWebContent.canGoBack()) {
                    //返回键，可以回退，则回退，否则退出
                    mWebContent.goBack();
                } else {
                    this.finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebContent.canGoBack()) {
                //返回键，可以回退，则回退，否则退出
                mWebContent.goBack();
                return true;
            } else {
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }

        if (mWebContent != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mWebContent.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebContent);
            }

            mWebContent.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebContent.getSettings().setJavaScriptEnabled(false);
            mWebContent.clearHistory();
            mWebContent.clearView();
            mWebContent.removeAllViews();

            try {
                mWebContent.destroy();
            } catch (Throwable ex) {

            }
        }

    }
}
