package com.search.bus.bussearch;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 作者 夏晔
 * 2016/11/29.
 */
public class ChaxunFragment extends Fragment {
    private View view;
    private Button btn2;
    private Button btn3;
    private FragmentManager fm;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chaxunfragment, container, false);
    }

}