package com.search.bus.bussearch;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**                         作者：李烨                           作者：李烨
 * 作者 夏晔                版本信息、检查更新的编写            关于我们跳转、点击
 *  2016/11/29.             2016/12/5                           2016/12/6
 */


public class Setting_Fragment extends Fragment {

    private FragmentManager fm;
    private Context context;
    private TextView typeNews;
    private TextView upDate;
    private  TextView about_us;
    private  TextView introduce;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);

        //获取点击对象
            //版本信息、检查更新
        typeNews = (TextView)view.findViewById(R.id.type_news);
        upDate = (TextView)view.findViewById(R.id.update);

            //关于我们、使用介绍
        about_us = (TextView)view.findViewById(R.id.about_us);
        introduce = (TextView)view.findViewById(R.id.introduce);


        //版本信息、检查更新
        typeNewsListener();
        upDateListener();
        //关于我们


        return view;

    }

    /**
     * 给版本信息添加点击事件
     * 作者：李烨
     * 时间：2016/12/6
     */

    private void typeNewsListener(){
        context=getActivity().getApplicationContext();

        typeNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"版本 V1.0.0",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 给检查更新添加点击事件
     * 作者：李烨
     * 时间：2016/12/6
     */
    private void upDateListener(){
        context=getActivity().getApplicationContext();

        upDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"已是最新版本",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 给关于我们添加点击事件及跳转
     * 作者：李烨
     * 时间：2016/12/6
     */
    private void about_usListener(){
        context=getActivity().getApplicationContext();
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setClass(context,AboutusActivity.class);
                startActivity(intent1);
            }
        });
    }

}