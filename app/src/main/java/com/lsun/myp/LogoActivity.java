package com.lsun.myp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LogoActivity extends AppCompatActivity {
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LogoActivity.this, SelectLoginActivity.class);
                startActivity(intent);
                finish();
            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1000);

    }




    @Override
    protected void onStart() {
        super.onStart();
//        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
//        Sprite doubleBounce = new DoubleBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);
    }
    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mRunnable);
        super.onDestroy();
    }


}
