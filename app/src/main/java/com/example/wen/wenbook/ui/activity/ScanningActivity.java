package com.example.wen.wenbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.wen.wenbook.R;
import com.example.wen.wenbook.di.component.AppComponent;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by wen on 2017/3/25.
 */

public class ScanningActivity extends BaseActivity implements ZXingScannerView.ResultHandler {

    private static final String FLASH_STATE = "FLASH_STATE";
    @BindView(R.id.content_frame)
    FrameLayout mContentFrame;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ZXingScannerView mScannerView;
    private boolean mFlash;


    @Override
    public int setLayoutId() {
        return R.layout.activity_scanning;
    }

    @Override
    public void init() {
        mScannerView = new ZXingScannerView(this);
        mContentFrame.addView(mScannerView);

        setSupportActionBar(mToolbar);

        // 返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Activity标题
        setTitle("ISBN条形码");
    }

    @Override
    public void setUpActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void handleResult(Result result) {
        // 判断是否是EAN13码
        if (result.getBarcodeFormat() != BarcodeFormat.EAN_13) {
            // 提示错误
            Toast.makeText(this, "不是ISBN条形码", Toast.LENGTH_SHORT).show();

            // 重新扫码
            mScannerView.resumeCameraPreview(this);
        } else {
            // 显示图书添加界面
            Intent intent = new Intent(this, BookInfoAddActivity2.class);
            intent.putExtra("ISBN", result.getText());
            startActivity(intent);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        mScannerView.setFlash(mFlash);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
    }

    public void toggleFlash(View v) {
        mFlash = !mFlash;
        mScannerView.setFlash(mFlash);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
