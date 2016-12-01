package com.search.bus.bussearch;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者 夏晔       修改者：苑凯文
 * 2016/11/29.     2016/11/29
 *                 编写获取输入地址坐标及跳转到路线页面
 */
public class Search_Fragment extends Fragment  implements
        GeocodeSearch.OnGeocodeSearchListener, View.OnClickListener{
    private ProgressDialog progDialog = null;
    private GeocodeSearch geocoderSearch;
    private LatLonPoint a = new LatLonPoint(1,1);
    public static LatLonPoint addressName = new LatLonPoint(1,1);
    public static LatLonPoint addressName1 = new LatLonPoint(0,0);
    private AMap aMap;
    private MapView mapView;
    private EditText et1;
    private EditText et2;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.search_fragment, container, false);
        mapView = (MapView)view1.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        et1 =(EditText)view1.findViewById(R.id.Et_1);
        et2 =(EditText)view1.findViewById(R.id.Et_2);
        Button geoButton = (Button)view1.findViewById(R.id.geoButton);
        geoButton.setOnClickListener(this);
        init();
        return view1;
    }
    /**
     * 初始化AMap对象
     */
    public void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        geocoderSearch = new GeocodeSearch(getActivity());
        geocoderSearch.setOnGeocodeSearchListener(this);
        progDialog = new ProgressDialog(getActivity());
    }
    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {

        super.onPause();
        mapView.onPause();
    }
    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    /**
     * 显示进度条对话框
     */
    public void showDialog() {
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在查询");
        progDialog.show();
    }
    /**
     * 隐藏进度条对话框
     */
    public void dismissDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }
    /**
     * 响应地理编码//
     */
    public void getLatlon(final String name) {
        showDialog();

        GeocodeQuery query = new GeocodeQuery(name, "石家庄");// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
    }
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }
    /**
     * 地理编码查询回调
     */
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        dismissDialog();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);
                if(addressName.getLatitude() == a.getLatitude()) {
                    addressName = address.getLatLonPoint();
                }
                else {
                    addressName1 = address.getLatLonPoint();
                }
            } else {
                ToastUtil.show(getActivity(), "对不起没有搜索到相关信息");
            }
        } else {
            ToastUtil.showerror(getActivity(), rCode);
        }
    }
    @Override
    public void onClick(View v) {
        String name = et1.getText().toString();
        String name1 = et2.getText().toString();
        switch (v.getId()) {
            /**
             * 响应地理编码按钮
             */
            case R.id.geoButton:
                getLatlon(name);
                getLatlon(name1);
                /*
                * 将跳转页面延迟几秒进行使前两个函数能够调用完成
                * */
                Timer timer=new Timer();
                TimerTask task=new TimerTask(){
                    public void run(){
                        Intent intent = new Intent();
                        intent.setClass(getActivity(),BusRouteActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    }
                };
                timer.schedule(task, 3000);

                break;
            default:
                break;
        }
    }
}