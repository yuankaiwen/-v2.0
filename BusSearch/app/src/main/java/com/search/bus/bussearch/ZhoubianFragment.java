package com.search.bus.bussearch;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者 夏晔
 *  2016/11/29.
 */
public class ZhoubianFragment extends Fragment {

    private FragmentManager fm;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.zhoubianfragment, container, false);
    }

}