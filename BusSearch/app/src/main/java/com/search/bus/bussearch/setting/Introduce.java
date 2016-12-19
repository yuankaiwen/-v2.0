package com.search.bus.bussearch.setting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.search.bus.bussearch.MainActivity;
import com.search.bus.bussearch.R;

/**
 * 使用介绍            解决问题
 * 作者：李烨          修改者：李越
 * 时间：2016/12/6     时间：2016.12.6
 */
public class Introduce extends Activity{
    AnimationDrawable frameAnimation;
    ImageView imgView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_introduce);
        //获取视图控件
        TextView txtView = (TextView)findViewById(R.id.animation);

        ImageView imgView = (ImageView)findViewById(R.id.Iback);
        //为视图控件加载视图动画资源
        txtView.setBackgroundResource(R.drawable.introduce);
        /******************动画的启动****************************/
        //获得动画对象
        final    AnimationDrawable animation = (AnimationDrawable) txtView.getBackground();

        //给animation添加监听事件


        //开始动画
       animation.start();
        /*********************动画关闭***************************/
        //停止动画
        //null;


        backListener();

    }
    //我们的简介页面返回按钮的点击事件
    private void backListener(){
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent();
                intent3.setClass(Introduce.this,MainActivity.class);
                intent3.putExtra("Fid",2);
                startActivity(intent3);
            }
        });
    }
}
