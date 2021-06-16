package com.example.schoolnavigation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.example.schoolnavigation.ui.login.LoginActivity;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

    double x;
    double y;
    String name;

    //AMap是地图对象
    private AMap aMap;
    private MapView mapView;
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    //自定义精确度圈
    MyLocationStyle myLocationStyle;

    private List<LatLng> list;

    private String[] mStrs = {"我的位置", "大门", "主楼", "联通广场",
            "第一图书馆", "1号教学楼", "校医院", "实验楼",
            "体育场", "汇文楼", "综合楼", "3号教学楼",
            "4号教学楼", "C区游泳馆", "艺术楼", "B区超市",
            "排球场", "B食堂", "化学楼", "生命楼",
            "农学楼", "A食堂", "新图书馆", "博物馆",
            "网球场", "体育馆", "第一游泳馆", "二号教学楼"};
    private SearchView mSearchView;
    private ListView lListView;
    public Graph gra=new Graph();
    public LatLng latLng;//=new LatLng(0,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Intent data = getIntent();
        name = data.getExtras().getString("name");
        System.out.println(name+"***********************************************************");
        gra.GraphBuild();

        list = (ArrayList<LatLng>) getIntent().getSerializableExtra("points");

        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 设置圆形的边框颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 255, 255));
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 26, 141, 31));// 设置圆形的填充颜色

        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            //设置显示定位按钮 并且可以点击
            UiSettings settings = aMap.getUiSettings();
            aMap.setLocationSource(this);//设置了定位的监听
            //aMap.setMapType(AMap.MAP_TYPE_NORMAL);
            aMap.setMyLocationStyle(myLocationStyle);

            // 是否显示定位按钮
            settings.setMyLocationButtonEnabled(false);
            aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase


        }
        //开始定位
        location();


//        Button button = (Button)findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(MainActivity.this, Main2Activity.class);
////                intent.putExtra("x",x);
////                intent.putExtra("y",y);
//                startActivity(intent);
////                finish();
//            }
//        });
        mSearchView = (SearchView) findViewById(R.id.searchView);
        lListView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrs);
        mSearchView.setSubmitButtonEnabled(true);
        lListView.setAdapter(arrayAdapter);
        lListView.setTextFilterEnabled(false);
        lListView.setVisibility(View.INVISIBLE);
        // 设置搜索文本监听
        lListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str="";
                //Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Intent intent=new Intent(Main2Activity.this,MainActivity.class);
//                startActivity(intent);
//                Vertex v=gra.getStartVertex(query);
                list=gra.UDG(45.707226,126.624578,getStartVertex(query).getx(),getStartVertex(query).gety());//45.706784,126.624616(126.624578,45.707226)
                setUpMap();
                //Toast.makeText(MainActivity.this,query,Toast.LENGTH_SHORT).show();
                return true;
            }
            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                lListView.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(newText)){
                    android.widget.Filter filter = arrayAdapter.getFilter();
                    filter.filter(newText);//替换 listview.setFilterText();
//                    lListView.setFilterText(newText);
                }else{
                    lListView.clearTextFilter();
                }
                return false;
            }
        });

        Button button1 = (Button)findViewById(R.id.tst);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MainActivity.this, InformationActivity.class);
                intent.putExtra("x",x);
                intent.putExtra("y",y);
                intent.putExtra("name", name);
                startActivity(intent);
                finish();
            }
        });
        if(list != null && !list.isEmpty()){
            setUpMap();
        }
//        AMapLocationListener mAMapLocationListener = new AMapLocationListener(){
//            @Override
//            public void onLocationChanged(AMapLocation amapLocation) {
//                if (amapLocation != null) {
//                    if (amapLocation.getErrorCode() == 0) {
//                            //可在其中解析amapLocation获取相应内容。
//                        latLng=new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
//                        Log.i("111",amapLocation.getLatitude()+""+amapLocation.getLongitude());
//                        //list.add(0,latLng);
//                        setUpMap();
//                    }else {
//                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
//                        Log.e("AmapError","location Error, ErrCode:"
//                                + amapLocation.getErrorCode() + ", errInfo:"
//                                + amapLocation.getErrorInfo());
//                    }
//                }
//            }
//        };
    }



    private void location() {

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void setUpMap() {
        // list = showListLat();
//        list = (ArrayList<LatLng>) getIntent().getSerializableExtra("points");
        //起点位置和  地图界面大小控制
        aMap.clear();
        location();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list.get(0), 17));
        aMap.setMapTextZIndex(2);
        // 绘制一条带有纹理的直线
        aMap.addPolyline((new PolylineOptions())
                //集合数据
                .addAll(list)
                //线的宽度
                .width(20).setDottedLine(true).geodesic(true)
                //颜色
                .color(Color.argb(255,35,25,220)));


        //起点坐标
        final LatLng start = list.get(0);//new LatLng(45.70736,126.624551);
        //终点坐标
        LatLng end = list.get(list.size()-1);//new LatLng(45.706596,126.624637);
        //起点锚点
        final Marker marker = aMap.addMarker(new MarkerOptions().position(start).title("A食堂").snippet("点击查看详情")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.amap_start)));
        //终点锚点
        final Marker marker1 = aMap.addMarker(new MarkerOptions().position(end).title("B食堂").snippet("点击查看详情")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.amap_end)));

        // 定义 Marker 点击事件监听
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Log.i("lgq","dianjiddd===="+marker.getPeriod());//获取markerID
                //Toast.makeText(getApplicationContext(), marker.getPeriod(), Toast.LENGTH_LONG).show();

                if(name.equals("20190001")){
                    Toast.makeText(getApplicationContext(), "第一个人", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "第二个人", Toast.LENGTH_LONG).show();
                }

//                Intent intent =new Intent(MainActivity.this, InformationActivity.class);
//                startActivity(intent);
                return false;
            }
        };
// 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);

    }

    private List<LatLng> showListLat(){
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(new LatLng(45.70736, 126.624551));
        points.add(new LatLng(45.706596, 126.624637));
        return points;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端。
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度

                x = aMapLocation.getLatitude();
                y = aMapLocation.getLongitude();

                // Log.v("asd", String.valueOf(aMapLocation.getLatitude()));
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(aMapLocation);
                    //添加图钉
                    //  aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(aMapLocation.getCountry() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getCity() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getDistrict() + ""
                            + aMapLocation.getStreet() + ""
                            + aMapLocation.getStreetNum());
                    // Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
            }
        }
    }



    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }
    public Vertex getStartVertex(String s){
        int i;
        Log.i("123",s);
        for(i=0;i<gra.getVexsNum();i++){
            if (gra.Vexs[i].getName().equals(s)) {
                Log.i("123",(gra.Vexs[i].getName().equals(s))+"");
                break;
            }
        }
        Log.i("123",i+"");
        return gra.Vexs[i];
    }
}


