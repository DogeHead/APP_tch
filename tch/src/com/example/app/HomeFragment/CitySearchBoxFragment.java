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
	
	//����
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
		
		//����
		mLocationClient=new AMapLocationClient(this.getContext());
		//���ü�����
		mLocationClient.setLocationListener(new MyLocationListener());
		
		setAndStartLocationOption();
		mLocationClient.startLocation();
		
	}


	//����
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
		        	chooseCityBtn.setText(amapLocation.getCity());
		        	Log.i("Location","����:"+amapLocation.getCity()+
		        			",��γ��:"+amapLocation.getLatitude()+","+amapLocation.getLongitude());
		        	
		        	mLocationClient.stopLocation();
		    } else {
		              //��ʾ������ϢErrCode�Ǵ����룬errInfo�Ǵ�����Ϣ������������
		        Log.i("AmapError","location Error, ErrCode:"
		            + amapLocation.getErrorCode() + ", errInfo:"
		            + amapLocation.getErrorInfo());
		        //bt_location.setText("���¶�λ");
		        }
		    }
		}
		
	}
}
