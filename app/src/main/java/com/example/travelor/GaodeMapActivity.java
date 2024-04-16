package com.example.travelor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.MapsInitializer;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.ServiceSettings;
import com.example.travelor.bean.Attractions;


public class GaodeMapActivity extends AppCompatActivity {

    private MapView mMapView;
    private AMapLocationClient mLocationClient;
    private Button back;
    private Attractions attractions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaode_map_layout);//设置对应的XML布局文件

        initData();

        //定位隐私政策同意
        AMapLocationClient.updatePrivacyShow(this,true,true);
        AMapLocationClient.updatePrivacyAgree(this,true);

        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mMapView.getMap();
        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式

        // 返回
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GaodeMapActivity.this, DetailsPageActivity.class);
                intent.putExtra("attraction", attractions);
                startActivity(intent);
                finish();
            }
        });

        // 初始化定位客户端
//        try {
//            mLocationClient = new AMapLocationClient(getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        AMapLocationClientOption option = new AMapLocationClientOption();
//        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        mLocationClient.setLocationOption(option);
//        mLocationClient.setLocationListener(new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation aMapLocation) {
//                // 处理定位结果
//                if (aMapLocation != null) {
//                    double latitude = aMapLocation.getLatitude();
//                    double longitude = aMapLocation.getLongitude();
//                    // 在地图上显示当前位置
//                    LatLng latLng = new LatLng(latitude, longitude);
//                    MarkerOptions markerOptions = new MarkerOptions().position(latLng);
//                    AMap map = mMapView.getMap();
//                    map.addMarker(markerOptions);
//                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
//                }
//            }
//        });
//
//        mLocationClient.startLocation();
    }

    private void initData() {
        Intent intent = getIntent();
        attractions = (Attractions) intent.getSerializableExtra("attraction");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}