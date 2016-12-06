package com.search.bus.bussearch;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 作者 夏晔
 *  2016/11/29.
 */

/**
 *版本信息、检查更新的编写
 * 作者：李烨
 * 2016/12/5
 */

public class Setting_Fragment extends Fragment {

    private FragmentManager fm;
    private TextView typenews;
    private TextView update;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_fragment, container, false);

    }

}