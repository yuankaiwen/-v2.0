package com.search.bus.bussearch;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    private LinearLayout ll;
    FrameLayout fl;
    private ImageButton btn1;
    private ImageButton btn2;
    private ImageButton btn3;
    //声明Fragment属性
    private ChaxunFragment mChaxun;
    private ZhoubianFragment mZhoubian;
    private ShezhiFragment mShezhi;
    View.OnClickListener Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.获取一个FragmentManager对象
            FragmentManager fm = getFragmentManager();
            //2.获取FragmentTransaction对象
            FragmentTransaction transaction = fm.beginTransaction();
            switch (v.getId()) {
                case R.id.btn11:
                    if (mChaxun == null) {
                        mChaxun = new ChaxunFragment();
                    }
                    //3.设置页面
                    transaction.replace(R.id.fl, mChaxun);
                    break;
                case R.id.btn22:
                    if (mZhoubian == null) {
                        mZhoubian = new ZhoubianFragment();
                    }
                    transaction.replace(R.id.fl, mZhoubian);
                    break;
                case R.id.btn33:
                    if (mShezhi == null) {
                        mShezhi = new ShezhiFragment();
                    }
                    transaction.replace(R.id.fl, mShezhi);
                    break;
            }
            //4.执行更改
            transaction.commit();
            fl.invalidate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1.获取界面控件
        getViews();
        //2.注册事件监听器
        setListener();
        //3.设置默认的页面（fragment页面）
        setDefaultPage();
    }

    //获取界面控件
    private void getViews() {
        fl = (FrameLayout) findViewById(R.id.fl);
        ll = (LinearLayout) findViewById(R.id.ll);
        btn1 = (ImageButton) findViewById(R.id.btn11);
        btn2 = (ImageButton) findViewById(R.id.btn22);
        btn3 = (ImageButton) findViewById(R.id.btn33);
    }
    //注册事件监听器
    private void setListener() {
        btn1.setOnClickListener(Listener);
        btn2.setOnClickListener(Listener);
        btn3.setOnClickListener(Listener);
    }

    //设置默认的界面
    private void setDefaultPage() {
        //1.获取一个FragmentManager对象
        FragmentManager fm = getFragmentManager();
        //2.获取FragmentTransaction对象
        FragmentTransaction transaction = fm.beginTransaction();
        mChaxun = new ChaxunFragment();
        //3.设置页面
        transaction.replace(R.id.fl, mChaxun);
        //4.执行更改
        transaction.commit();
    }
}
