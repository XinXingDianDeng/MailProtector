package com.example.mailprotector.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mailprotector.R;

public class AppStart extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        //延迟一秒钟进入MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //启动MainActivity主页面，这段代码是在主线程执行
                startActivity(new Intent(AppStart.this,MainActivity.class));
                //关闭当前页面（结束WelcomeActivity）
                finish();
            }
        },1000);

    }
}
