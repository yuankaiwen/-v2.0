package com.search.bus.bussearch;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
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
    //覆盖物添加 李越
    private MarkerOptions markerOption,markerOption1,markerOption1l,markerOption2l,markerOption3l,markerOption4l,markerOption5l,markerOption6l,markerOption7l,markerOption8l,markerOption9l,markerOption10l,markerOption11l,markerOption12l,markerOption13l,markerOption14l,markerOption15l,markerOption16l,markerOption17l,markerOption18l,markerOption19l,markerOption20l,markerOption21l,markerOption22l,markerOption23l,markerOption24l,markerOption25l,markerOption26l,markerOption27l,markerOption28l,markerOption29l;
    private Marker marker,marker1,marker1l,marker2l,marker3l,marker4l,marker5l,marker6l,marker7l,marker8l,marker9l,marker10l,marker11l,marker12l,marker13l,marker14l,marker15l,marker16l,marker17l,marker18l,marker19l,marker20l,marker21l,marker22l,marker23l,marker24l,marker25l,marker26l,marker27l,marker28l,marker29l;
    private Button btn1,btn2,btn3;
    //天津景点覆盖物添加 -汪仑
    private MarkerOptions markerOption1w,markerOption2w,markerOption3w,markerOption4w,markerOption5w,markerOption6w,markerOption7w,markerOption8w,markerOption9w,markerOption10w;
    private Marker marker1w,marker2w,marker3w,marker4w,marker5w,marker6w,marker7w,marker8w,marker9w,marker10w;
    //保定覆盖物添加 -汪仑
    private MarkerOptions markerOption11w,markerOption12w,markerOption13w,markerOption14w,markerOption15w,markerOption16w,markerOption17w,markerOption18w,markerOption19w,markerOption20w;
    private Marker marker11w,marker12w,marker13w,marker14w,marker15w,marker16w,marker17w,marker18w,marker19w,marker20w;
    //夏晔 添加覆盖物
    private MarkerOptions markerOption1x,markerOption2x,markerOption3x,markerOption4x,markerOption5x,markerOption6x,markerOption7x,markerOption8x,markerOption9x,markerOption10x,markerOption11x,markerOption12x,markerOption13x,markerOption14x,markerOption15x,markerOption16x,markerOption17x,markerOption18x,markerOption19x,markerOption20x;
    private Marker marker1x,marker2x,marker3x,marker4x,marker5x,marker6x,marker7x,marker8x,marker9x,marker10x,marker11x,marker12x,marker13x,marker14x,marker15x,marker16x,marker17x,marker18x,marker19x,marker20x;
    //李烨 添加覆盖物
    private MarkerOptions markerOption1ly;
    private Marker marker1ly;
    //苑凯文 添加覆盖物
    private MarkerOptions markerOption1y;
    private Marker marker1y;
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
                if (aMap != null) {
                    jumpPoint(marker);
                }
                marker.showInfoWindow();
                return true;
            }
        });
        //点击地图隐藏infowindow
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                xyAddHide();
                wlAddHide();
                ykwAddHide();
                liyeAddHide();
                marker.hideInfoWindow();
                marker1.hideInfoWindow();
                marker1l.hideInfoWindow();
                marker2l.hideInfoWindow();
                marker3l.hideInfoWindow();
                marker4l.hideInfoWindow();
                marker5l.hideInfoWindow();
                marker6l.hideInfoWindow();
                marker7l.hideInfoWindow();
                marker8l.hideInfoWindow();
                marker9l.hideInfoWindow();
                marker10l.hideInfoWindow();
                marker11l.hideInfoWindow();
                marker12l.hideInfoWindow();
                marker13l.hideInfoWindow();
                marker14l.hideInfoWindow();
                marker15l.hideInfoWindow();
                marker16l.hideInfoWindow();
                marker17l.hideInfoWindow();
                marker18l.hideInfoWindow();
                marker19l.hideInfoWindow();
                marker20l.hideInfoWindow();
                marker21l.hideInfoWindow();
                marker22l.hideInfoWindow();
                marker23l.hideInfoWindow();
                marker24l.hideInfoWindow();
                marker25l.hideInfoWindow();
                marker26l.hideInfoWindow();
                marker27l.hideInfoWindow();
                marker28l.hideInfoWindow();
                marker29l.hideInfoWindow();
            }
        });
    }

    private void  liyeAddHide(){

    }

    private void  ykwAddHide(){

    }

    private void xyAddHide(){
        marker1x.hideInfoWindow();
        marker2x.hideInfoWindow();
        marker3x.hideInfoWindow();
        marker4x.hideInfoWindow();
        marker5x.hideInfoWindow();
        marker6x.hideInfoWindow();
        marker7x.hideInfoWindow();
        marker8x.hideInfoWindow();
        marker9x.hideInfoWindow();
        marker11x.hideInfoWindow();
        marker12x.hideInfoWindow();
        marker13x.hideInfoWindow();
        marker14x.hideInfoWindow();
        marker15x.hideInfoWindow();
        marker16x.hideInfoWindow();
        marker10x.hideInfoWindow();
        marker17x.hideInfoWindow();
        marker18x.hideInfoWindow();
        marker19x.hideInfoWindow();
        marker20x.hideInfoWindow();
    }

    private void wlAddHide(){
        marker1w.hideInfoWindow();
        marker2w.hideInfoWindow();
        marker3w.hideInfoWindow();
        marker4w.hideInfoWindow();
        marker5w.hideInfoWindow();
        marker6w.hideInfoWindow();
        marker7w.hideInfoWindow();
        marker8w.hideInfoWindow();
        marker9w.hideInfoWindow();
        marker10w.hideInfoWindow();
        marker11w.hideInfoWindow();
        marker12w.hideInfoWindow();
        marker13w.hideInfoWindow();
        marker14w.hideInfoWindow();
        marker15w.hideInfoWindow();
        marker16w.hideInfoWindow();
        marker17w.hideInfoWindow();
        marker18w.hideInfoWindow();
        marker19w.hideInfoWindow();
        marker20w.hideInfoWindow();
    }

    /**
     * 作者：李越
     * marker点击时跳动一下
     * 2016.12.6
     */
    public void jumpPoint(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        final LatLng markerLatlng = marker.getPosition();
        Point markerPoint = proj.toScreenLocation(markerLatlng);
        markerPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(markerPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * markerLatlng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * markerLatlng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }
    
    /**
     * 作者：李越
     * 2016.11.30
     * 在地图中添加覆盖物
     */
    private void addMarkersToMap() {
        xyAddMarkers();
        wlAddMarkers();
        ykwAddMarkers();
        liyeAddMarkers();

        //--------------------示例添加
        //标注覆盖物
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.918058, 116.397026))
                .title("故宫")
                .snippet("AAAAA级\n北京故宫是中国明清两代的皇家宫殿，旧称为紫禁城，位于北京中轴线的中心，是中国古代宫廷建筑之精华。")
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
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.908692, 116.397477))
                .title("天安门")
                .snippet("天安门广场，位于北京市中心\n天安门广场记载了中国人民不屈不挠的革命精神和大无畏的英雄气概")
                .draggable(true);
        marker1 = aMap.addMarker(markerOption1);
        //----------------正式添加
        //------------------------------石家庄
        markerOption1l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(37.722482, 114.768948))
                .title("赵州桥")
                .snippet("AAAA级景区\n又称安济桥，俗名大石桥")
                .draggable(true);
        marker1l = aMap.addMarker(markerOption1l);

        markerOption2l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(37.74921, 114.784331))
                .title("柏林禅寺")
                .snippet("中国著名佛教禅寺，北方佛教的一座重镇，赵州祖庭所在地")
                .draggable(true);
        marker2l = aMap.addMarker(markerOption2l);

        markerOption3l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.076899, 114.458284))
                .title("水上公园")
                .snippet("AAA级景区\n水上公园由三湖九岛构成，有国内外建筑艺术、园林艺术、雕塑艺术精品")
                .draggable(true);
        marker3l = aMap.addMarker(markerOption3l);

        markerOption4l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.148035, 114.579226))
                .title("荣国府与宁荣街")
                .snippet("AAAA级景区\n一座按照经典巨著《红楼梦》的描述原样建起的院落式建筑群。")
                .draggable(true);
        marker4l = aMap.addMarker(markerOption4l);

        markerOption5l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.244905, 113.760108))
                .title("天桂山风景区")
                .snippet("AAAA级景区\n天桂山风景区地处太行山中段，最高峰海拔1054米，景区面积60平方公里,素有“北方桂林”之称。")
                .draggable(true);
        marker5l = aMap.addMarker(markerOption5l);

        markerOption6l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(37.534249, 114.117199))
                .title("嶂石岩")
                .snippet("AAAA级景区\n以独特的嶂石岩地貌为主的自然风景区，景区内的山岭均为壮观的红色峭壁，竖直的岩壁和石柱十分壮观。")
                .draggable(true);
        marker6l = aMap.addMarker(markerOption6l);

        markerOption7l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.515955, 113.52495))
                .title("黑山大峡谷")
                .snippet("AAAA级景区\n一处以山川峡谷等自然景观为主的风景区，来此观赏山川景色、登山锻炼十分不错，山里空气清新环境优美，是石家庄周边游玩的好去处。")
                .draggable(true);
        marker7l = aMap.addMarker(markerOption7l);

        //文本覆盖物
        TextOptions textOptions8l = new TextOptions().position(new LatLng(38.270381, 113.748037))
                .text("佛光山").fontColor(Color.WHITE)
                .backgroundColor(Color.BLACK).fontSize(30).align(Text.ALIGN_CENTER_HORIZONTAL, Text.ALIGN_CENTER_VERTICAL)
                .zIndex(1.f).typeface(Typeface.DEFAULT_BOLD)
                ;
        aMap.addText(textOptions8l);

        markerOption8l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.270381, 113.748037))
                .title("佛光山")
                .snippet("AAAA级景区\n佛光山景区是国家级风景名胜区西柏坡—天桂山景区的一个分景区，属禅林圣地。")
                .draggable(true);
        marker8l = aMap.addMarker(markerOption8l);

        //文本覆盖物
        TextOptions textOptions9l = new TextOptions().position(new LatLng(38.147663, 114.586235))
                .text("赵云庙").fontColor(Color.WHITE)
                .backgroundColor(Color.BLACK).fontSize(30).align(Text.ALIGN_CENTER_HORIZONTAL, Text.ALIGN_CENTER_VERTICAL)
                .zIndex(1.f).typeface(Typeface.DEFAULT_BOLD)
                ;
        aMap.addText(textOptions9l);

        markerOption9l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.147663, 114.586235))
                .title("赵云庙")
                .snippet("AA级景区\n古来冲阵扶危主，只有常山赵子龙。赵云的英烈之名，让赵云庙成为了游客的必游之地。")
                .draggable(true);
        marker9l = aMap.addMarker(markerOption9l);

        markerOption10l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.145273, 114.586684))
                .title("隆兴寺")
                .snippet("AAAA级景区\n隆兴寺历史悠久，受过多位帝王尊崇，地位极高，可以前来拜佛祈福。年代久远的古建筑和众多珍贵的文物珍宝都可以在此见到。")
                .draggable(true);
        marker10l = aMap.addMarker(markerOption10l);

        markerOption11l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.088451, 114.276964))
                .title("抱犊寨")
                .snippet("AAAA级景区\n抱犊寨周围山势开阔、树木众多，是爬山观景的好去处。山顶有罗汉寺、长城、韩信祠等人文古迹，可以拜佛访古。")
                .draggable(true);
        marker11l = aMap.addMarker(markerOption11l);

        markerOption12l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.339815, 113.94112))
                .title("西柏坡中共中央旧址")
                .snippet("AAAAA级景区\n瞻仰开国领导人的生活、工作旧址，学习西柏坡时期的革命精神。参观西柏坡纪念馆，了解全国解放、共和国建立的历史进程。")
                .draggable(true);
        marker12l = aMap.addMarker(markerOption12l);

        markerOption13l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.041335, 114.610888))
                .title("天山海世界")
                .snippet("AAAA级景区\n国内最大的室内水上娱乐中心，是夏天时避暑玩水的好地方。")
                .draggable(true);
        marker13l = aMap.addMarker(markerOption13l);

        markerOption14l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.646414, 113.921309))
                .title("灵寿水泉溪自然风景区")
                .snippet("AAAA级景区\n一处以山川、峡谷、洞穴和溪水为主的自然风景区。")
                .draggable(true);
        marker14l = aMap.addMarker(markerOption14l);

        markerOption15l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.738479, 113.793177))
                .title("驼梁风景区")
                .snippet("AAAA级景区\n因为山顶形状很像驼峰而得名，是河北省内著名的山峰。")
                .draggable(true);
        marker15l = aMap.addMarker(markerOption15l);

        markerOption16l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.198163, 113.750898))
                .title("沕沕水生态风景区")
                .snippet("AAAA级景区\n景区内山势清秀，有众多的瀑布、深潭和泉水，还有茂密的树木，景色幽然清新。")
                .draggable(true);
        marker16l = aMap.addMarker(markerOption16l);

        markerOption17l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.249608, 113.981541))
                .title("白鹿温泉")
                .snippet("AAAA级景区\n来到这里可以享受温泉、体验SPA按摩等服务、游玩众多的水上娱乐项目等")
                .draggable(true);
        marker17l = aMap.addMarker(markerOption17l);

        markerOption18l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.011353, 114.55467))
                .title("空中花园")
                .snippet("AAAA级景区\n景区是将七座高楼的第六、七、八、九层用透明玻璃材质在空中联通起来，打通成为一座面积三万多平米的空中区域，离地16米左右，是一处真正意义的空中花园")
                .draggable(true);
        marker18l = aMap.addMarker(markerOption18l);

        markerOption19l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.225858, 114.155492))
                .title("东方巨龟苑")
                .snippet("AAAA级景区\n这里既有美丽的园林风光，也有众多的娱乐项目，是石家庄周边观光娱乐的好去处。")
                .draggable(true);
        marker19l = aMap.addMarker(markerOption19l);

        markerOption20l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.728075, 113.859892))
                .title("五岳寨风景区")
                .snippet("AAAA级景区\n因为景区内的五座主峰形似中国的五岳而得名")
                .draggable(true);
        marker20l = aMap.addMarker(markerOption20l);

        markerOption21l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.119413, 113.833359))
                .title("仙台山")
                .snippet("AAA级景区\n仙台山是一处以秀丽的自然风光为主，兼有人文景观的综合风景区。")
                .draggable(true);
        marker21l = aMap.addMarker(markerOption21l);

        markerOption22l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.526823, 114.090814))
                .title("藤龙山风景区")
                .snippet("AAAA级景区\n一处集地质景观、森林生态、人文历史景观于一体的高品位原生态自然风景区。")
                .draggable(true);
        marker22l = aMap.addMarker(markerOption22l);

        markerOption23l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.541582, 114.156286))
                .title("秋山风景区")
                .snippet("AAAA级景区\n是一片以山岭风光为主的自然风景区，也是石家庄市周末郊游的好出去。")
                .draggable(true);
        marker23l = aMap.addMarker(markerOption23l);

        markerOption24l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.132324, 114.571665))
                .title("广惠寺华塔")
                .snippet("AAA级景区\n广惠寺华塔，又名多宝塔。")
                .draggable(true);
        marker24l = aMap.addMarker(markerOption24l);
        //--------------------------------------石家庄景点添加完毕
        //---------------------------------------添加衡水覆盖物
        markerOption25l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(37.609552, 115.610267))
                .title("衡水湖")
                .snippet("AAAA级景区\n这里有宽广的湖水、茂密的芦苇荡，还有荷塘、湖心岛等。游客来此可以乘船游湖，拍摄荷花和芦苇，还可以漫步湖心小岛，观赏各种动物，是河北当地周末放松游玩的好去处。")
                .draggable(true);
        marker25l = aMap.addMarker(markerOption25l);

        markerOption26l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.038148, 115.980909))
                .title("武强年画博物馆")
                .snippet("AAAA级景区\n全国第一家以年画为主题的专题博物馆。这里展示了著名的武强年画的历史发展、流派和特色等，有众多的年画实物展示，是了解年画文化的好去处")
                .draggable(true);
        marker26l = aMap.addMarker(markerOption26l);

        markerOption27l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(37.717142, 115.64107))
                .title("宝云寺")
                .snippet("AA级景区\n碑石记载始建于隋，其实早在南北朝时，此处已有寺庙。相传在其鼎盛之时，寺域广占三十亩，殿堂多有近百座，“樵楼钟声惊千里，成年累月拥香客”。")
                .draggable(true);
        marker27l = aMap.addMarker(markerOption27l);

        markerOption28l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(37.559513, 115.573345))
                .title("灵秀山庄")
                .snippet("AA级景区\n整个山庄分三个景区。前部为玄门、慧目街、购物区、游船码头；中部为悬空岛、紫薇山、长城、迷宫、镇海塔；后部为北岛度假村、旅社等。 千年古刹“竹林寺”为景区主体建筑之一，为古冀州古城的四大禅寺之一。")
                .draggable(true);
        marker28l = aMap.addMarker(markerOption28l);

        markerOption29l = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(37.742214, 115.70929))
                .title("衡水二中")
                .snippet("河北省示范性学校")
                .draggable(true);
        marker29l = aMap.addMarker(markerOption29l);
        //----------------------------------------衡水覆盖物添加完毕

    }

    private void liyeAddMarkers() {
    }

    private void ykwAddMarkers() {
    }

    private void wlAddMarkers() {
        //天津覆盖物添加 -汪仑
        //天津之眼
        markerOption1w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.160042,117.193405))
                .title("天津之眼")
                .snippet("天津的标志，是世界上唯一建在桥上的摩天轮")
                .draggable(true);
        marker1w = aMap.addMarker(markerOption1w);

        //意式风情街
        markerOption2w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.135703,117.199933))
                .title("意式风情街")
                .snippet("以体现浓郁的意大利风情为宗旨，将风情区建设成为集旅游、商贸、休闲、娱乐和文博为一体的综合性多功能区")
                .draggable(true);
        marker2w = aMap.addMarker(markerOption2w);

        //古文化街
        markerOption3w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.143865,117.192214))
                .title("古文化街")
                .snippet("系商业步行街。现在属津门十景之一。")
                .draggable(true);
        marker3w = aMap.addMarker(markerOption3w);

        //盘山
        markerOption4w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(40.093249,117.279729))
                .title("盘山")
                .snippet("是自然山水与名胜古迹并著、佛教寺院与皇家园林共称的旅游胜地 ")
                .draggable(true);
        marker4w = aMap.addMarker(markerOption4w);

        //南市食品街
        markerOption5w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.132927,117.183941))
                .title("南市食品街")
                .snippet("南市食品街是一座中西合璧、南北风味突出的多元化餐饮中心 ")
                .draggable(true);
        marker5w = aMap.addMarker(markerOption5w);

        //塘沽
        markerOption6w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.04953,117.634836))
                .title("塘沽")
                .snippet("塘沽位于我国京津城市和环渤海城市带的交汇点，地处天津滨海新区的中心地带。 ")
                .draggable(true);
        marker6w = aMap.addMarker(markerOption6w);

        //世纪钟广场
        markerOption7w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.133543,117.205555))
                .title("世纪钟广场")
                .snippet("古典与现代浑然一体，寓意时空延续，时不我待。盘芯及钟指针采用花档镂空制作，显得古朴典雅。 ")
                .draggable(true);
        marker7w = aMap.addMarker(markerOption7w);

        //海河外滩公园
        markerOption8w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.015144,117.664201))
                .title("海河外滩公园")
                .snippet("古典与现代浑然一体，寓意时空延续，时不我待。盘芯及钟指针采用花档镂空制作，显得古朴典雅。 ")
                .draggable(true);
        marker8w = aMap.addMarker(markerOption8w);

        //水上公园
        markerOption9w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.088969,117.167804))
                .title("水上公园")
                .snippet("是天津市规模最大的综合性公园，津门十景之一，景名\"龙潭浮翠\" ")
                .draggable(true);
        marker9w = aMap.addMarker(markerOption9w);

        //海河
        markerOption10w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.083243,117.254757))
                .title("海河")
                .snippet("海河风景线，始于三岔口，止于大光明桥，横穿繁华的天津市区 ")
                .draggable(true);
        marker10w = aMap.addMarker(markerOption10w);


    //保定覆盖物添加
        // 白石山世界地质公园
        markerOption11w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.230279,114.682518))
                .title(" 白石山世界地质公园")
                .snippet("一处以壮观的峰林地貌为主的自然风景区，也是保定市最著名的景区之一。 ")
                .draggable(true);
        marker11w = aMap.addMarker(markerOption11w);
        // 白洋淀
        markerOption12w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.942554,115.988563))
                .title(" 白洋淀")
                .snippet("白洋淀位于河北省中部，距离北京市区和天津市区的车程均约140公里，是京津地区周末短途旅游的理想去处。 ")
                .draggable(true);
        marker12w = aMap.addMarker(markerOption12w);
        // 野三坡
        markerOption13w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.668405,115.385583))
                .title(" 野三坡")
                .snippet("野三坡景区位于河北保定涞水县，在北京城区西侧约100公里处，是京郊欣赏自然风光、避暑游玩的胜地 ")
                .draggable(true);
        marker13w = aMap.addMarker(markerOption13w);
        // 古莲花池
        markerOption14w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.857501,115.49814))
                .title(" 古莲花池")
                .snippet("古莲花池，是保定古城八景之一，称“涟漪夏艳”，为我国北方古代园林明珠。 ")
                .draggable(true);
        marker14w = aMap.addMarker(markerOption14w);
        // 狼牙山
        markerOption15w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.131318,115.178706))
                .title(" 狼牙山")
                .snippet("狼牙山景区位于保定市易县西部的太行山东麓，距县城45公里，这里山峰险峻陡峭，因为形状好像尖利的狼牙而得名。 ")
                .draggable(true);
        marker15w = aMap.addMarker(markerOption15w);
        //清西陵
        markerOption16w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.372423,115.362775))
                .title(" 清西陵")
                .snippet("清西陵位于河北保定易县，距离北京约140公里，是清朝雍正、嘉庆、道光、光绪四位皇帝及多位后妃、阿哥、王公的陵寝所在地。 ")
                .draggable(true);
        marker16w = aMap.addMarker(markerOption16w);
        //冉庄地道战遗址
        markerOption17w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.672388,115.372046))
                .title("冉庄地道战遗址")
                .snippet("冉庄地道战遗址，位于河北省保定市西南30公里处清苑县境内，是抗日战争时期中国共产党领导下的华北抗日主战场上一处极为重要的战争遗址。 ")
                .draggable(true);
        marker17w = aMap.addMarker(markerOption17w);
        //满城汉墓
        markerOption18w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.947902,115.301577))
                .title("满城汉墓")
                .snippet("满城汉墓位于保定市满城县县城西侧，是西汉时中山靖王刘胜及其妻子窦绾之墓，两座墓室规模很大 ")
                .draggable(true);
        marker18w = aMap.addMarker(markerOption18w);
        //天生桥风景区
        markerOption19w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.87376,113.879292))
                .title("天生桥风景区")
                .snippet("天生桥风景区是一个国家森林公园。位于河北省保定市阜平县东下关乡朱家营村。 ")
                .draggable(true);
        marker19w = aMap.addMarker(markerOption19w);
        // 虎山风景区
        markerOption20w = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(38.934227,114.56183))
                .title(" 虎山风景区")
                .snippet("虎山风景区位于保定市曲阳县北侧，因为山顶有一块形状像猛虎的石头而得名。 ")
                .draggable(true);
        marker20w = aMap.addMarker(markerOption20w);



    }

    private void xyAddMarkers() {
        //夏晔        添加北京景点覆盖物    2016/12/8
        markerOption1x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.91073, 116.394351))
                .title("中山公园")
                .snippet("国家AAAA级旅游景区\n占地23万平方米，是—座纪念性的古典坛庙园林。\n 它原是明清两代的社稷坛，与太庙（今劳动人民文化宫）一起沿袭周代以来“左祖右社”的礼制建造。")
                .draggable(true);
        marker1x = aMap.addMarker(markerOption1x);


               markerOption2x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.92819, 116.388717))
                .title("北海公园")
                .snippet("国家AAAA级旅游景区\n城内景山西侧，在故宫的西北面，与中海、南海合称三海。\n 是中国现存最古老、最完整、最具综合性和代表性的皇家园林之一")
                .draggable(true);
        marker2x = aMap.addMarker(markerOption2x);


        markerOption3x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(40.35619, 116.016727))
                .title("八达岭长城")
                .snippet("是中国古代伟大的防御工程万里长城的重要组成部分\n明长城的八达岭段被称作“玉关天堑”，为明代居庸关八景之一。\n 是举世闻名的旅游胜地。")
                .draggable(true);
        marker3x = aMap.addMarker(markerOption3x);

        markerOption4x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.496439, 116.334665))
                .title("北京野生动物园")
                .snippet("是经原国家林业局批准，北京市政府立项、\n北京绿野晴川有限公司投资建设的集动物保护、\n野生动物驯养繁殖及科普教育为一体的大型自然生态公园。")
                .draggable(true);
        marker4x = aMap.addMarker(markerOption4x);

        markerOption5x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(40.014992, 116.392515))
                .title("北京奥林匹克公园")
                .snippet("是包含体育赛事、会展中心、科教文化、休闲购物等多种功能在内的综合性市民公共活动中心。\n集中体现了“科技、绿色、人文”三大理念\n融合了办公、商业、酒店、文化、体育、会议、居住多种功能的新型城市区域")
                .draggable(true);
        marker5x = aMap.addMarker(markerOption5x);
        markerOption6x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.989042, 116.190236))
                .title("香山公园")
                .snippet("是一座具有山林特色的皇家园林\n香山公园有香山寺、洪光寺等著名旅游景点\n2001年被国家旅游局评为AAAA景区，2002年被评为首批北京市精品公园。")
                .draggable(true);
        marker6x = aMap.addMarker(markerOption6x);
        markerOption7x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.984671, 116.493609))
                .title("北京798艺术区")
                .snippet("因当代艺术和798生活方式闻名于世。\n原为原国营798厂等电子工业的老厂区所在地\n")
                .draggable(true);
        marker7x = aMap.addMarker(markerOption7x);
        markerOption8x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.936251, 116.416704))
                .title("南锣鼓巷")
                .snippet("南锣鼓巷是一条胡同\n它是北京最古老的街区之一，是我国唯一完整保存着元代胡同院落肌理、\n规模最大、品级最高、资源最丰富的棋盘式传统民居区，也是最赋有老北京风情的街巷。")
                .draggable(true);
        marker8x = aMap.addMarker(markerOption8x);
        markerOption9x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.866719, 116.490519))
                .title("欢乐谷")
                .snippet("北京欢乐谷是国家4A级旅游景区\n北京欢乐谷设置了120余项体验项目\n获得过\"中国文化创意产业高成长企业百强\"\"首都旅游紫禁杯先进集体\"\"首都文明旅游景区\"等荣誉。")
                .draggable(true);
        marker9x = aMap.addMarker(markerOption9x);
        markerOption10x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.866719, 116.490519))
                .title("青龙湖公园")
                .snippet("是距京城最近的“一盘清水”\n园内山清水秀，林木茂盛，湖面宽阔，生态环境十分优美\n")
                .draggable(true);
        marker10x = aMap.addMarker(markerOption10x);
        markerOption11x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(40.005205,116.305059))
                .title("圆明园")
                .snippet("圆明园又称圆明三园，是清代一座大型皇家宫苑”\n被法国作家维克多·雨果称誉为“理想与艺术的典范\n")
                .draggable(true);
        marker11x = aMap.addMarker(markerOption11x);
        markerOption12x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(40.184584, 116.364454))
                .title("中国航空博物馆")
                .snippet("中国航空博物馆是中国第一座对外开放的大型航空博物馆”\n也是亚洲规模最大、跻身世界前5位的航空博物馆\n")
                .draggable(true);
        marker12x = aMap.addMarker(markerOption12x);
        markerOption13x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(40.250648, 116.220258))
                .title("北京十三陵风景区")
                .snippet("明十三陵是中国明朝皇帝的墓葬群，坐落在北京西北郊昌平区境内的燕山山麓的天寿山。”\n十三陵被北京旅游世界之最评选委员会评为“是世界上保存完整埋葬皇帝最多的墓葬群。\n")
                .draggable(true);
        marker13x = aMap.addMarker(markerOption13x);
        markerOption14x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.775765, 116.467794))
                .title("南海子公园")
                .snippet("北京南海子郊野公园是北京四大郊野公园之一”\n也是北京市最大的湿地公园，全部建成后总面积超过11平方公里\n")
                .draggable(true);
        marker14x = aMap.addMarker(markerOption14x);
        markerOption15x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.795024,115.946287))
                .title("北京石花洞国家地质公园")
                .snippet("与闻名中外的桂林芦笛岩、福建玉华洞、杭州瑶琳洞并称中国四大岩溶洞穴。”\n\n")
                .draggable(true);
        marker15x = aMap.addMarker(markerOption15x);
        markerOption16x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.874378,116.190389))
                .title("北京园博园")
                .snippet("北京园博园为第九届中国国际园林博览会的举办地”\n与卢沟桥遥相呼应，历史文化氛围浓郁，地形多变，山水相依，颇具特色。\n")
                .draggable(true);
        marker16x = aMap.addMarker(markerOption16x);
        markerOption17x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.992844,116.27416))
                .title("颐和园")
                .snippet("颐和园，中国清朝时期皇家园林”\n2009年，颐和园入选中国世界纪录协会中国现存最大的皇家园林。\n")
                .draggable(true);
        marker17x = aMap.addMarker(markerOption17x);
        markerOption18x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.942192,116.336473))
                .title("北京动物园")
                .snippet("是中国最大的动物园之一，也是一所世界知名的动物园”\n\n")
                .draggable(true);
        marker18x = aMap.addMarker(markerOption18x);
        markerOption19x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.952852,116.414579))
                .title("地坛公园")
                .snippet("地坛公园又称方泽坛，是古都北京五坛中的第二大坛”\n1984年被评为北京市文物保护单位\n")
                .draggable(true);
        marker19x = aMap.addMarker(markerOption19x);
        markerOption20x = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(39.870953,116.356557))
                .title("大观园")
                .snippet("大观园，是《红楼梦》中贾府为元春省亲而修建的别墅”\n于北京西城区南菜园护城河畔建造大观园作为87版电视剧拍摄基地。摄制完成后，景区对外开放。\n")
                .draggable(true);
        marker20x = aMap.addMarker(markerOption20x);
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