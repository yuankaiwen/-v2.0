package com.search.bus.bussearch;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Text;
import com.amap.api.maps2d.model.TextOptions;

/**
 * 作者 夏晔         修改者：李越
 *  2016/11/29.      2016.11.29
 *  编写             添加地图控件
 */
public class Surround_Fragment extends Fragment {

    private Context context;
    private FragmentManager fm;
    private MapView mapView;
    private AMap aMap;
    private MarkerOptions markerOption,markerOption1;
    private Marker marker,marker1;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.surround_fragment, container, false);
        mapView = (MapView)view.findViewById(R.id.map);//获取控件
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        return view;
    }

    /**
     * 作者：李越
     * 2016.11.30
     * 在地图中初步添加点击事件以及添加覆盖物
     */
    private void setUpMap() {
        addMarkersToMap();
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
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
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
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
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