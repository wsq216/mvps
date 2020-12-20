package com.example.mvps.ui.map;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.animation.AlphaAnimation;
import com.baidu.mapapi.animation.Animation;
import com.baidu.mapapi.animation.Transformation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.mvps.R;
import com.example.mvps.base.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mapapi.clusterutil.clustering.ClusterItem;
import mapapi.clusterutil.clustering.ClusterManager;
import mapapi.overlayutil.WalkingRouteOverlay;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    //百度地图定位的类
    LocationClient locationClient;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.input)
    EditText input;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.input_start)
    EditText inputStart;
    @BindView(R.id.input_end)
    EditText inputEnd;
    @BindView(R.id.layout_edit)
    LinearLayout layoutEdit;
    @BindView(R.id.btn_routePlan)
    Button btnRoutePlan;
    @BindView(R.id.recyclerViews)
    RecyclerView recyclerViews;
    @BindView(R.id.input_district)
    EditText inputDistrict;
    @BindView(R.id.btn_district)
    Button btnDistrict;

    private BaiduMap baiduMap;

    private static final String TAG = "MapActivity";
    int r = 100;
    PoiSearch poiSearch;

    /********************检索********************/
    SearchItemAdapter searchItemAdapter;
    List<PoiInfo> poiList;
    private InfoWindow mInfoWindow;

    /********************检索***********************/
    /********************路径规划***********************/
    private RoutePlanSearch mSearch;

    /********************路径规划***********************/
    /********************行政区***********************/
    private DistrictSearch mDistrictSearch;

    /********************行政区***********************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        initView();
        initMap();
        initLocation();
        initPoi();
        initSearch();
        initDistrict();
    }


    private void initView() {

        btnSearch.setOnClickListener(this::onClick);
        btnRoutePlan.setOnClickListener(this::onClick);
        btnDistrict.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                String s = input.getText().toString();
                search(s);
                break;
            case R.id.btn_routePlan:
                searchWalk();
                break;
            case R.id.btn_district:
                district();
                break;
        }
    }


    private void initLocation() {
        //定位初始化
        locationClient = new LocationClient(this);

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);

        //设置locationClientOption
        locationClient.setLocOption(option);

        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        locationClient.start();
    }




    /**
     * 地图定位的监听
     */
    private class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //mapView 销毁后不在处理新接收的位置
            if (bdLocation == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(bdLocation.getDirection()).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
        }
    }

    private void initMap() {
        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }


    /********************行政区***********************/


    private void initDistrict() {

        mDistrictSearch = DistrictSearch.newInstance();
        mDistrictSearch.setOnDistrictSearchListener(listenerD);

    }
    OnGetDistricSearchResultListener listenerD = new OnGetDistricSearchResultListener() {
        @Override
        public void onGetDistrictResult(DistrictResult districtResult) {
            baiduMap.clear();
            if (null != districtResult && districtResult.getPolylines()!=null) {
                //对检索所得行政区划边界数据进行处理
                if (districtResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    List<List<LatLng>> polyLines = districtResult.getPolylines();
                    if (polyLines == null) {
                        return;
                    }
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (List<LatLng> polyline : polyLines) {
                        OverlayOptions ooPolyline11 = new PolylineOptions().width(10)
                                .points(polyline).dottedLine(true).color(Color.BLUE);
                        baiduMap.addOverlay(ooPolyline11);
                        OverlayOptions ooPolygon = new PolygonOptions().points(polyline)
                                .stroke(new Stroke(5, 0xAA00FF88)).fillColor(0xAAFFFF00);
                        baiduMap.addOverlay(ooPolygon);
                        for (LatLng latLng : polyline) {
                            builder.include(latLng);
                        }
                    }
                    baiduMap.setMapStatus(MapStatusUpdateFactory
                            .newLatLngBounds(builder.build()));

                }
            }
        }
    };


    private void district() {
        String s = inputDistrict.getText().toString();
        if (!TextUtils.isEmpty(s)){
        mDistrictSearch.searchDistrict(new DistrictSearchOption()
                .cityName("北京市")
                .districtName(s));
        }else{
            Toast.makeText(this, "空", Toast.LENGTH_SHORT).show();
        }
    }

    /********************行政区***********************/


    /********************路径规划***********************/

    private PlanNode startNode, endNode; //开始和结束的坐标点
    SuggestionSearch suggestionSearch; //地点检索的类
    SuggestAdapter suggestAdapter; //路径规划搜索出来的列表
    List<SuggestionResult.SuggestionInfo> suggestList; //地点检索的结果
    boolean isStart = true; //当前处理的是否是起点
    LatLng startLatLng; //起点的经纬度

    private void initSearch() {
        suggestionSearch = SuggestionSearch.newInstance();

        suggestList = new ArrayList<>();
        suggestAdapter = new SuggestAdapter(suggestList, this);
        recyclerViews.setLayoutManager(new LinearLayoutManager(this));
        recyclerViews.setAdapter(suggestAdapter);

        //设置监听地点检索
        suggestionSearch.setOnGetSuggestionResultListener(suggestionResultListener);

        inputStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isStart = true;
                }
            }
        });
        //监听起点输入框的变化
        inputStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                recyclerViews.setVisibility(View.VISIBLE);
                String s1 = s.toString();
                if (!TextUtils.isEmpty(s1)) {
                    //起点输入改变以后获取对应的起点数据
                    SuggestionSearchOption option = new SuggestionSearchOption();
                    option.city("北京");
                    option.citylimit(true);
                    option.keyword(s.toString());
                    suggestionSearch.requestSuggestion(option);
                } else {
                    Toast.makeText(MapActivity.this, "文本不能为恐慌", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //监听终点输入框的光标
        inputEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isStart = false;
                    recyclerViews.setVisibility(View.VISIBLE);
                }
            }
        });
        //监听终点输入框的输入
        inputEnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                recyclerViews.setVisibility(View.VISIBLE);
                String s1 = s.toString();
                if (!TextUtils.isEmpty(s1)) {
                    //获取终点框对应的数据
                    SuggestionSearchOption option = new SuggestionSearchOption();
                    option.city("北京");
                    option.citylimit(true);
                    option.keyword(s.toString());
                    suggestionSearch.requestSuggestion(option);
                } else {
                    Toast.makeText(MapActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(listeners);

        suggestAdapter.setiListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                SuggestionResult.SuggestionInfo suggestionInfo = suggestList.get(pos);
                if (isStart) {
                    inputStart.setText(suggestionInfo.getKey());
                    startLatLng = suggestionInfo.getPt();
                } else {
                    inputEnd.setText(suggestionInfo.getKey());
                }
                recyclerViews.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 地点检索监听
     */
    OnGetSuggestionResultListener suggestionResultListener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            if (suggestionResult.getAllSuggestions() != null) {
                //地点检索结果
                suggestList.clear();
                suggestList.addAll(suggestionResult.getAllSuggestions());
                suggestAdapter.notifyDataSetChanged();
            }
        }
    };

    OnGetRoutePlanResultListener listeners = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
            Log.d(TAG, "onGetWalkingRouteResult: ");
            //创建WalkingRouteOverlay实例
//            //创建一个路径规划的类
            WalkingRouteOverlay walkingRouteOverlay = new WalkingRouteOverlay(baiduMap);
            //判断当前查找出来的路线
            if (walkingRouteResult.getRouteLines() != null && walkingRouteResult.getRouteLines().size() > 0) {
                walkingRouteOverlay.setData(walkingRouteResult.getRouteLines().get(0));
                walkingRouteOverlay.addToMap();
                //当前的定位移动到开始点并放大地图
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLngZoom(startLatLng, 16);
                baiduMap.setMapStatus(status);
            }
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
            Log.d(TAG, "onGetTransitRouteResult: ");

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {
            Log.d(TAG, "onGetMassTransitRouteResult: ");
        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            Log.d(TAG, "onGetDrivingRouteResult: ");
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {
            Log.d(TAG, "onGetIndoorRouteResult: ");
        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
            Log.d(TAG, "onGetBikingRouteResult: ");
        }
    };


    private void searchWalk() {

        String start = inputStart.getText().toString();
        String end = inputEnd.getText().toString();
        if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
            PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", start);
            PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", end);

            mSearch.walkingSearch((new WalkingRoutePlanOption())
                    .from(stNode)
                    .to(enNode));
        } else {
            Toast.makeText(this, "没有此路径", Toast.LENGTH_SHORT).show();
        }
    }

    /********************路径规划***********************/

    /********************检索********************/

    private void search(String s) {
        /**
         *  PoiCiySearchOption 设置检索属性
         *  city 检索城市
         *  keyword 检索内容关键字
         *  pageNum 分页页码
         */
        if (!TextUtils.isEmpty(s)) {
            poiSearch.searchInCity(new PoiCitySearchOption()
                    .city("北京") //必填
                    .keyword(s) //必填
                    .pageNum(10));
        }

    }


    public void initPoi() {

        poiList = new ArrayList<>();
        searchItemAdapter = new SearchItemAdapter(poiList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchItemAdapter);

        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(listener);


        searchItemAdapter.setiListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                //点击条目进行定位
                PoiInfo poiInfo = poiList.get(pos);
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(poiInfo.location);
                baiduMap.setMapStatus(status);

                addButton(poiInfo.location, poiInfo.getName());

            }
        });

    }


    private void addButton(LatLng location, String name) {
        //用来构造InfoWindow的Button
        Button button = new Button(getApplicationContext());
        button.setBackgroundResource(R.drawable.popup);
        button.setText(name);
        button.setHeight(5);
        button.setTextSize(10f);

        //构造InfoWindow
        //point 描述的位置点
        //-100 InfoWindow相对于point在y轴的偏移量
        mInfoWindow = new InfoWindow(button, location, -50);

        //使InfoWindow生效
        baiduMap.showInfoWindow(mInfoWindow);

    }

    private void addText(double latitude, double longitude, String name) {
        //文字覆盖物位置坐标
        LatLng llText = new LatLng(latitude, longitude);

        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text(name) //文字内容
                .bgColor(0xAAFFFF00) //背景色
                .fontSize(24) //字号
                .fontColor(0xFFFF00FF) //文字颜色
                .rotate(-30) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        Overlay mText = baiduMap.addOverlay(mTextOptions);
    }

    //搜说监听

    OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            Log.d(TAG, "onGetPoiResult: ");
            poiList.clear();
            if (poiResult.getAllPoi() != null && poiResult.getAllPoi().size() > 0) {
                List<PoiInfo> allPoi = poiResult.getAllPoi();
                poiList.addAll(allPoi);
                for (int i = 0; i < allPoi.size(); i++) {
                    PoiInfo poiInfo = allPoi.get(i);
                    drawCircle(poiInfo.location.latitude, poiInfo.location.longitude);
                    addMark(poiInfo.location);
//                    addText(poiInfo.location.latitude, poiInfo.location.longitude, poiInfo.getName());
                    addButton(poiInfo.location, poiInfo.getName());
                }
                searchItemAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            Log.d(TAG, "onGetPoiDetailResult: ");
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            Log.d(TAG, "onGetPoiIndoorResult: ");
        }

        //废弃
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            Log.d(TAG, "onGetPoiDetailResult: ");
        }
    };

    /**
     * 以当前的经纬度为圆心绘制一个圆
     *
     * @param lat
     * @param gt
     */
    private void drawCircle(double lat, double gt) {
        //设置圆心位置
        LatLng center = new LatLng(lat, gt);
        //设置圆对象
        CircleOptions circleOptions = new CircleOptions().center(center)
                .radius(2000)
                .fillColor(0xAA000000)
                .stroke(new Stroke(10, 0xAA00ff00)); //设置边框的宽度和颜色
        //在地图上添加显示圆
        baiduMap.addOverlay(circleOptions);
    }

    private void addMark(LatLng location) {


        //定义Maker坐标点
        LatLng point = new LatLng(location.latitude, location.longitude);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_mark);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //通过LatLng列表构造Transformation对象

        LatLng points = new LatLng(location.latitude + 0.001, location.longitude);



        Transformation mTransforma = new Transformation(location,points,location);
        //透明动画
//        AlphaAnimation mTransforma = new AlphaAnimation(1,0,1);

        //动画执行时间
        mTransforma.setDuration(1000);

//        //动画重复模式
        mTransforma.setRepeatMode(Animation.RepeatMode.RESTART);
//
//        //动画重复次数
        mTransforma.setRepeatCount(-1);

        //在地图上添加Marker，并显示
        Marker marker = (Marker) baiduMap.addOverlay(option);
        //设置动画
        marker.setAnimation(mTransforma);

        //开启动画
        marker.startAnimation();

    }

    /********************检索********************/


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        poiSearch.destroy();
    }
}