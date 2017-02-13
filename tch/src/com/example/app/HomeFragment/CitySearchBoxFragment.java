package com.example.app.HomeFragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.app.R;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class CitySearchBoxFragment extends Fragment{
	private static final int ACTIVITY_REQUEST_1=1; 
	private View theView;
	private Button chooseCityBtn;
	//private EditText et_searchBox;
	
	//新增
	private AMapLocationClient mLocationClient=null;
	private AMapLocationClientOption mLocationOption = null;
	//
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_citysearchbox,container,false);
		Log.i("CitySearchBoxFragment","CitySearchBoxFragment.onCreateView");
		InitControl();
		return theView;
		}
	@Override
	public void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		switch(requestCode)
		{
		case ACTIVITY_REQUEST_1:
		{
			
			break;
		}
		}
	}
	private void InitControl()
	{
		chooseCityBtn=(Button)theView.findViewById(R.id.btn_CitySearchBoxFragment_choose);
		//et_searchBox=(EditText)theView.findViewById(R.id.et_CitySearchBoxFragment_search)
		
		//新增
		mLocationClient=new AMapLocationClient(this.getContext());
		//设置监听类
		mLocationClient.setLocationListener(new MyLocationListener());
		
		setAndStartLocationOption();
		mLocationClient.startLocation();
		
	}


	//新增
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
		        	chooseCityBtn.setText(amapLocation.getCity());
		        	Log.i("Location","城市:"+amapLocation.getCity()+
		        			",经纬度:"+amapLocation.getLatitude()+","+amapLocation.getLongitude());
		        	
		        	mLocationClient.stopLocation();
		    } else {
		              //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
		        Log.i("AmapError","location Error, ErrCode:"
		            + amapLocation.getErrorCode() + ", errInfo:"
		            + amapLocation.getErrorInfo());
		        //bt_location.setText("重新定位");
		        }
		    }
		}
		
	}
}
