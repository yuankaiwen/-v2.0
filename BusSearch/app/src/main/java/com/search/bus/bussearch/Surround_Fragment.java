package com.search.bus.bussearch;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.Text;
import com.amap.api.maps2d.model.TextOptions;

/**
 * 作者 夏晔         修改者：李越     修改者：汪仑
 *  2016/11/29.      2016.11.29       2016.12.1
 *  编写             添加地图控件     添加定位功能
 */
public class Surround_Fragment extends Fragment implements LocationSource,
        AMapLocationListener {

    private Context context;
    private FragmentManager fm;
    private MapView mapView;
    private AMap aMap;
    private MarkerOptions markerOption,markerOption1;
    private Marker marker,marker1;
    private Button btn1,btn2,btn3;
    //添加定位组件
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.surround_fragment, container, false);
        btn1=(Button)view.findViewById(R.id.clearMap);//获取控件
        btn2=(Button)view.findViewById(R.id.resetMap);
        btn3=(Button)view.findViewById(R.id.Location);
        setListener();
        mapView = (MapView)view.findViewById(R.id.map);//获取控件
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }

        return view;
    }

    /**
     * 作者：李越                修改者：汪仑
     * 2016.12.1                 2016.12.6
     * 对button添加点击事件      添加定位按钮点击事件
     */
    private void setListener() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aMap != null) {
                    aMap.clear();//清除覆盖物
                    setLocation();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aMap != null) {

                    addMarkersToMap();//添加覆盖物
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocation(); //定位
            }
        });



    }
    /**
     * 作者：汪仑
     * 2016.12.1
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
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationType()
    }
    /**
     * 作者：汪仑
     * 2016.12.1
     * 定位成功后回调函数
     */
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    /**
     * 作者：汪仑
     * 2016.12.1
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

    /**
     * 作者：汪仑
     * 2016.12.1
     * 停止定位
     */

    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }






    /**
     * 作者：李越
     * 2016.11.30
     * 在地图中初步添加点击事件以及添加覆盖物
     */
    private void setUpMap() {
        context=getActivity().getApplicationContext();
        //点击infowindow提示
        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(context,"你点击了"+marker.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
        //点击标注覆盖物显示infowindow
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });
        //点击地图隐藏infowindow
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                marker.hideInfoWindow();
                marker1.hideInfoWindow();
            }
        });
    }

    /**
     * 作者：李越
     * 2016.11.30
     * 在地图中添加覆盖物
     */
    private void addMarkersToMap() {
        //文本覆盖物
        TextOptions textOptions = new TextOptions().position(new LatLng(39.918058, 116.397026))
                .text("故宫").fontColor(Color.WHITE)
                .backgroundColor(Color.BLACK).fontSize(30).align(Text.ALIGN_CENTER_HORIZONTAL, Text.ALIGN_CENTER_VERTICAL)
                .zIndex(1.f).typeface(Typeface.DEFAULT_BOLD)
                ;
        aMap.addText(textOptions);
        //标注覆盖物
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .fromResource(R.drawable.bussearch_gps))
                .position(new LatLng(39.918058, 116.397026))
                .title("故宫")
                .snippet("详细信息")
                .draggable(true);
        marker = aMap.addMarker(markerOption);

        //文本覆盖物
        TextOptions textOptions1 = new TextOptions().position(new LatLng(39.908692, 116.397477))
                .text("天安门").fontColor(Color.WHITE)
                .backgroundColor(Color.BLACK).fontSize(30).align(Text.ALIGN_CENTER_HORIZONTAL, Text.ALIGN_CENTER_VERTICAL)
                .zIndex(1.f).typeface(Typeface.DEFAULT_BOLD)
                ;
        aMap.addText(textOptions1);
        //标注覆盖物
        markerOption1 = new MarkerOptions().icon(BitmapDescriptorFactory
                .fromResource(R.drawable.bussearch_gps))
                .position(new LatLng(39.908692, 116.397477))
                .title("天安门")
                .snippet("详细信息")
                .draggable(true);
        marker1 = aMap.addMarker(markerOption1);
    }

    /**
     * 作者：李越
     * 2016.11.29
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 作者：李越
     * 2016.11.29
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 作者：李越
     * 2016.11.29
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 作者:李越
     * 2016.11.29
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}