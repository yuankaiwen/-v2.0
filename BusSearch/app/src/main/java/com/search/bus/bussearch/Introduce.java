package com.search.bus.bussearch;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 * 使用介绍            解决问题
 * 作者：李烨          修改者：李越
 * 时间：2016/12/6     时间：2016.12.6
 */
public class Introduce extends Activity{
    AnimationDrawable frameAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_introduce);
        //获取视图控件
        TextView txtView = (TextView)findViewById(R.id.animation);
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
    }
}
