package com.search.bus.bussearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 *作者 夏晔                                     作者：李烨
 更新  关于我们界面                             点击跳转
 时间  2016/12/5                                 2016/12/6
 */
public class AboutusActivity extends Activity{
    private ImageView imgView;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);

        imgView = (ImageView)findViewById(R.id.g_return);
    //获取点击Listener
        backListener();

    }

    //我们的简介页面返回按钮的点击事件
    private void backListener(){
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent();
                intent3.setClass(context,Setting_Fragment.class);
                startActivity(intent3);
            }
        });
    }
}
