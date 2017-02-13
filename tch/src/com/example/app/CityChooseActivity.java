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
			"��λ","ֹͣ��λ"
	};
	private AMapLocationClient mLocationClient=null;
	//����mLocationOption����
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
		//���ü�����
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
							bt_location.setText("��λ");
						}
						else{
							mLocationClient.startLocation();
							bt_location.setText("ȡ����λ");
						}

					}
			
				});
	}
	private void setAndStartLocationOption()
	{
		//��ʼ����λ����
				mLocationOption = new AMapLocationClientOption();
				//���ö�λģʽΪ�߾���ģʽ��Battery_SavingΪ�͹���ģʽ��Device_Sensors�ǽ��豸ģʽ
				mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
				//�����Ƿ񷵻ص�ַ��Ϣ��Ĭ�Ϸ��ص�ַ��Ϣ��
				mLocationOption.setNeedAddress(true);
				//�����Ƿ�ֻ��λһ��,Ĭ��Ϊfalse
				mLocationOption.setOnceLocation(false);
				//�����Ƿ�ǿ��ˢ��WIFI��Ĭ��Ϊǿ��ˢ��
				mLocationOption.setWifiActiveScan(true);
				//�����Ƿ�����ģ��λ��,Ĭ��Ϊfalse��������ģ��λ��
				mLocationOption.setMockEnable(false);
				//���ö�λ���,��λ����,Ĭ��Ϊ2000ms
				mLocationOption.setInterval(2000);
				//����λ�ͻ��˶������ö�λ����
				mLocationClient.setLocationOption(mLocationOption);
				//������λ
				mLocationClient.startLocation();
	}
	
	private class MyLocationListener implements AMapLocationListener{

		@Override
		public void onLocationChanged(AMapLocation amapLocation) {
			// TODO Auto-generated method stub
			if (amapLocation != null) {
		        if (amapLocation.getErrorCode() == 0) {
		        	/*
		        //��λ�ɹ��ص���Ϣ�����������Ϣ
		        amapLocation.getLocationType();//��ȡ��ǰ��λ�����Դ�������綨λ����������λ���ͱ�
		        amapLocation.getLatitude();//��ȡγ��
		        amapLocation.getLongitude();//��ȡ����
		        amapLocation.getAccuracy();//��ȡ������Ϣ
		        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        Date date = new Date(amapLocation.getTime());
		        df.format(date);//��λʱ��
		        amapLocation.getAddress();//��ַ�����option������isNeedAddressΪfalse����û�д˽�������綨λ����л��е�ַ��Ϣ��GPS��λ�����ص�ַ��Ϣ��
		        amapLocation.getCountry();//������Ϣ
		        amapLocation.getProvince();//ʡ��Ϣ
		        amapLocation.getCity();//������Ϣ
		        amapLocation.getDistrict();//������Ϣ
		        amapLocation.getStreet();//�ֵ���Ϣ
		        amapLocation.getStreetNum();//�ֵ����ƺ���Ϣ
		        amapLocation.getCityCode();//���б���
		        amapLocation.getAdCode();//��������
		        amapLocation.getAoiName();//��ȡ��ǰ��λ���AOI��Ϣ
		                */
		        	tv_location.setText("city:"+amapLocation.getCity());
		        	Log.i("Location","����:"+amapLocation.getCity()+
		        			",��γ��:"+amapLocation.getLatitude()+","+amapLocation.getLongitude());
		        	bt_location.setText("��λ");
		        	mLocationClient.stopLocation();
		    } else {
		              //��ʾ������ϢErrCode�Ǵ����룬errInfo�Ǵ�����Ϣ������������
		        Log.i("AmapError","location Error, ErrCode:"
		            + amapLocation.getErrorCode() + ", errInfo:"
		            + amapLocation.getErrorInfo());
		        bt_location.setText("���¶�λ");
		        }
		    }
		}
		
	}
}
