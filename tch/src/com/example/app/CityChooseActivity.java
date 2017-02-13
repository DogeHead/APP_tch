package com.example.app;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.app.Service.LocationService;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CityChooseActivity extends FragmentActivity{
	private TextView tv_location;
	private Button bt_location;
	
	//private LocationClient locationClient;
	private String[] strLocation=new String[]{
			"定位","停止定位"
	};
	private AMapLocationClient mLocationClient=null;
	//声明mLocationOption对象
	private AMapLocationClientOption mLocationOption = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_city_choose);
		Log.i("CityChooseActivity","CityChooseActivity.onCreate");
		InitControl();
	}
	@Override
	public void onStop()
	{
		mLocationClient.stopLocation();
		super.onStop();
	}
	private void InitControl()
	{
		tv_location=(TextView)findViewById(R.id.tv_CityChooseActivity_Location);
		bt_location=(Button)findViewById(R.id.bt_CityChooseActivity_Location);
		
		mLocationClient=new AMapLocationClient(getApplicationContext());
		//设置监听类
		mLocationClient.setLocationListener(new MyLocationListener());
		
		setAndStartLocationOption();
		
		InitButton();
		
		
		
		
		
		mLocationClient.startLocation();
	}
	
	private void InitButton()
	{
		bt_location.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.i("onClick","isStarted()"+mLocationClient.isStarted());
						if(mLocationClient!=null&&mLocationClient.isStarted())
						{
							mLocationClient.stopLocation();
							bt_location.setText("定位");
						}
						else{
							mLocationClient.startLocation();
							bt_location.setText("取消定位");
						}

					}
			
				});
	}
	private void setAndStartLocationOption()
	{
		//初始化定位参数
				mLocationOption = new AMapLocationClientOption();
				//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
				mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
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
	
	private class MyLocationListener implements AMapLocationListener{

		@Override
		public void onLocationChanged(AMapLocation amapLocation) {
			// TODO Auto-generated method stub
			if (amapLocation != null) {
		        if (amapLocation.getErrorCode() == 0) {
		        	/*
		        //定位成功回调信息，设置相关消息
		        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
		        amapLocation.getLatitude();//获取纬度
		        amapLocation.getLongitude();//获取经度
		        amapLocation.getAccuracy();//获取精度信息
		        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        Date date = new Date(amapLocation.getTime());
		        df.format(date);//定位时间
		        amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
		        amapLocation.getCountry();//国家信息
		        amapLocation.getProvince();//省信息
		        amapLocation.getCity();//城市信息
		        amapLocation.getDistrict();//城区信息
		        amapLocation.getStreet();//街道信息
		        amapLocation.getStreetNum();//街道门牌号信息
		        amapLocation.getCityCode();//城市编码
		        amapLocation.getAdCode();//地区编码
		        amapLocation.getAoiName();//获取当前定位点的AOI信息
		                */
		        	tv_location.setText("city:"+amapLocation.getCity());
		        	Log.i("Location","城市:"+amapLocation.getCity()+
		        			",经纬度:"+amapLocation.getLatitude()+","+amapLocation.getLongitude());
		        	bt_location.setText("定位");
		        	mLocationClient.stopLocation();
		    } else {
		              //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
		        Log.i("AmapError","location Error, ErrCode:"
		            + amapLocation.getErrorCode() + ", errInfo:"
		            + amapLocation.getErrorInfo());
		        bt_location.setText("重新定位");
		        }
		    }
		}
		
	}
}
