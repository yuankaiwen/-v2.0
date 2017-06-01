package com.search.bus.bussearch.search;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.search.bus.bussearch.R;
import com.search.bus.bussearch.load.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者 夏晔       修改者：苑凯文                                 修改者：苑凯文
 * 2016/11/29.     2016/11/29                                     2017/5/25
 *                 编写获取输入地址坐标及跳转到路线页面           编写驾车查询跳转
 */
public class Search_Fragment extends Fragment  implements
        GeocodeSearch.OnGeocodeSearchListener,TextWatcher, View.OnClickListener, Inputtips.InputtipsListener, AMapLocationListener, LocationSource {
    public static String city;
    private ProgressDialog progDialog = null;
    private GeocodeSearch geocoderSearch;
    private LatLonPoint a = new LatLonPoint(1,1);
    private LatLonPoint x = new LatLonPoint(1,1);
    public static LatLonPoint addressName = new LatLonPoint(1,1);
    public static LatLonPoint addressName1 = new LatLonPoint(0,0);
    private String addressname;
    private AMap aMap;
    private MapView mapView;
    private AutoCompleteTextView et1;
    private AutoCompleteTextView et2;
    private EditText et5;
    private TextView tv1;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private int b = 0;
    private String Cname="xixi";
    private String Cname1="22";
    private String Cname2="11";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.search_fragment, container, false);
        mapView = (MapView)view1.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        /*
        * 编写下拉城市

        List<String> list = new ArrayList<String>();
        list.add("石家庄");
        list.add("天津");
        list.add("北京");
        list.add("保定");
        list.add("邯郸");
        list.add("廊坊");
        list.add("邢台");
        list.add("沧州");
        list.add("秦皇岛");
        list.add("张家口");
        list.add("衡水");
        list.add("承德");
        list.add("唐山");
        list.add("天津");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        Spinner sp = (Spinner)view1.findViewById(R.id.spacer);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                city = adapter.getItem(position);
            }
            //没有选中时的处理
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city = "石家庄";
            }
        });
        */
        et5 =(EditText)view1.findViewById(R.id.Et_5);
        et1 =(AutoCompleteTextView)view1.findViewById(R.id.Et_1);
        et2 =(AutoCompleteTextView)view1.findViewById(R.id.Et_2);
        tv1 = (TextView)view1.findViewById(R.id.Tv_1);
        et1.addTextChangedListener(this);
        et2.addTextChangedListener(this);
        Button geoButton = (Button)view1.findViewById(R.id.geoButton);
        Button geoButton1 = (Button)view1.findViewById(R.id.geoButton1);
        Button geoButton2 = (Button)view1.findViewById(R.id.geoButton2);
        Button bt1 =(Button)view1.findViewById(R.id.Bt_1);
        Button bt2 =(Button)view1.findViewById(R.id.Bt_2);
        bt2.setOnClickListener(this);
        bt1.setOnClickListener(this);
        geoButton.setOnClickListener(this);
        geoButton1.setOnClickListener(this);
        geoButton2.setOnClickListener(this);

        //读取存储的起始位置
        SharedPreferences preferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String name = preferences.getString(Cname, "11");
        String name1 = preferences.getString(Cname1, "11");
        String name2 = preferences.getString(Cname2, "11");
        if (name.equals("我的位置")){
            tv1.setText(name2+"---"+name1);
        }
        else{
            if (name1.equals("我的位置")){
                tv1.setText(name+"---"+name2);
            }
            else{
                tv1.setText(name+"---"+name1);
            }
        }
        tv1.setOnClickListener(new View.OnClickListener() {
            SharedPreferences preferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            String name = preferences.getString(Cname, "11");
            String name1 = preferences.getString(Cname1, "11");
            String name2 = preferences.getString(Cname2, "11");
            @Override
            public void onClick(View v) {
                if (name.equals("我的位置")){
                    getAddress(addressName);
                    et1.setText(name2);
                }
                else{
                    et1.setText(name);
                }
                if (name1.equals("我的位置")){
                    getAddress(addressName1);
                    et2.setText(name2);
                }
                else{
                    et2.setText(name1);
                }
            }
        });

        init();
        setLocation();
        return view1;
    }
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {// 正确返回
            List<String> listString = new ArrayList<String>();
            for (int i = 0; i < tipList.size(); i++) {
                listString.add(tipList.get(i).getName());
            }
            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.route_inputs, listString);
            et1.setAdapter(aAdapter);
            et2.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();
        } else {
            ToastUtil.showerror(getActivity(), rCode);
        }
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
    public void getLatlon( String name) {


        GeocodeQuery query = new GeocodeQuery(name,city);// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
    }
    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }
    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                addressname = result.getRegeocodeAddress().getFormatAddress()
                        + "附近";
            } else {
            }
        } else {
            ToastUtil.showerror(getActivity(), rCode);
        }

    }
    /**
     * 地理编码查询回调
     */
    public void onGeocodeSearched(GeocodeResult result, int rCode) {

        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
                if(addressName.getLatitude() == x.getLatitude()){
                    GeocodeAddress address = result.getGeocodeAddressList().get(0);
                    addressName = address.getLatLonPoint();
                }
                if(addressName.getLatitude() != x.getLatitude()){
                    GeocodeAddress address = result.getGeocodeAddressList().get(0);
                    addressName1 = address.getLatLonPoint();
                }
            } else {

            }
        } else {
            ToastUtil.showerror(getActivity(), rCode);
        }
    }

    /**
     * 设置定位属性
     */
    private void setLocation() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationType()
    }
    /**
     * 定位成功后回调函数
     */
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            String name = et1.getText().toString();
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0){
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                if(name.equals("我的位置")){
                    getAddress(addressName);
                    addressName.setLatitude(amapLocation.getLatitude());
                    addressName.setLongitude(amapLocation.getLongitude());
                }
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }
    /**
     * 激活定位
     */

    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
    @Override
    public void onClick(View v) {
        String name = et1.getText().toString();
        String name1 = et2.getText().toString();
        city = et5.getText().toString();
        switch (v.getId()) {
            case R.id.Bt_1:
                et1.setText("我的位置");
                break;
            /**
             * 响应地理编码按钮
             * 执行公交查询
             */
            case R.id.geoButton:
                if(b == 0){
                    String Name = et1.getText().toString();
                    String Name1 = et2.getText().toString();
                    if(Name.equals("我的位置")){
                        getLatlon(Name1);
                    }
                    else{
                        addressName.setLongitude(x.getLongitude());
                        addressName.setLatitude(x.getLatitude());
                        getLatlon(Name);
                        getLatlon(Name1);
                    }
                }
                b = 0;

                showDialog();
                /*
                * 将跳转页面延迟几秒进行
                * */
                Timer timer=new Timer();
                TimerTask task=new TimerTask(){
                    public void run(){
                        Intent intent = new Intent();
                        intent.setClass(getActivity(),BusRouteActivity.class);
                        startActivity(intent);
                        dismissDialog();
                        getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    }
                };
                timer.schedule(task, 2000);
                break;
            /*
            * 执行驾车路线查询
            * */
            case R.id.geoButton1:
                if(b == 0){
                    String Name = et1.getText().toString();
                    String Name1 = et2.getText().toString();
                    if(Name.equals("我的位置")){
                        getLatlon(Name1);
                    }
                    else{
                        addressName.setLongitude(x.getLongitude());
                        addressName.setLatitude(x.getLatitude());
                        getLatlon(Name);
                        getLatlon(Name1);
                    }
                }
                b = 0;

                showDialog();
                /*
                * 将跳转页面延迟几秒进行
                * */
                Timer timer1=new Timer();
                TimerTask task1=new TimerTask(){
                    public void run(){
                        Intent intent = new Intent();
                        intent.setClass(getActivity(),DriveRouteActivity.class);
                        startActivity(intent);
                        dismissDialog();
                        getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    }
                };
                timer1.schedule(task1, 2000);
                break;
            /*
            * 执行步行路线查询
            * */
            case R.id.geoButton2:
                if(b == 0){
                    String Name = et1.getText().toString();
                    String Name1 = et2.getText().toString();
                    if(Name.equals("我的位置")){
                        getLatlon(Name1);
                    }
                    else{
                        addressName.setLongitude(x.getLongitude());
                        addressName.setLatitude(x.getLatitude());
                        getLatlon(Name);
                        getLatlon(Name1);
                    }
                }
                b = 0;

                showDialog();
                /*
                * 将跳转页面延迟几秒进行
                * */
                Timer timer2=new Timer();
                TimerTask task2=new TimerTask(){
                    public void run(){
                        Intent intent = new Intent();
                        intent.setClass(getActivity(),WalkRouteActivity.class);
                        startActivity(intent);
                        dismissDialog();
                        getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    }
                };
                timer2.schedule(task2, 2000);
                break;
            /*
            * 交换起始地点
            * */
            case R.id.Bt_2:
                et1.setText(name1);
                et2.setText(name);
                b = 1;
                a = addressName;
                addressName = addressName1;
                addressName1 = a;
                break;
            default:
                break;
        }
        // 用SharedPreferences的方法存储数据
        SharedPreferences preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(Cname, name);
        editor.putString(Cname1,name1);
        if (name.equals("我的位置")){
            editor.putString(Cname2,addressname);
        }
        editor.commit();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        if (!AMapUtil.IsEmptyOrNullString(newText)) {
            InputtipsQuery inputquery = new InputtipsQuery(newText, city);
            Inputtips inputTips = new Inputtips(getActivity(), inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}