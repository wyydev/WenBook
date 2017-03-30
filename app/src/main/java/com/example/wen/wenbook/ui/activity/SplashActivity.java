package com.example.wen.wenbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.eftimoff.androipathview.PathView;
import com.example.wen.wenbook.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.pathView)
    PathView mPathView;
    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mBind = ButterKnife.bind(this);


        init();

    }

    private void init() {

        mPathView.setFillAfter(true);

        mPathView.getPathAnimator()
                .delay(500)
                .duration(3000)
                .listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
                    @Override
                    public void onAnimationEnd() {
                        //动画结束跳转到主界面

                        Intent i = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(i);
                        SplashActivity.this.finish();

                    }
                })
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind!= Unbinder.EMPTY){
            mBind.unbind();
        }
    }
}
