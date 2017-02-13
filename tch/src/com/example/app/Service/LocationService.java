package com.example.app.Service;

import com.example.app.R;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
public class LocationService extends Service implements LocationListener{

    public static final String LOCATION = "location";  
    public static final String LOCATION_ACTION = "locationAction";  
    
	private LocationManager locationManager;
	
	@Override
	public void onCreate()
	{
		locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
	}
	@Override
	public void onStart(Intent intent,int startId)
	{
		 if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) 
			 locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,this);  
		 else if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) 
			 locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,this);  
		 else Toast.makeText(this, "�޷���λ", Toast.LENGTH_SHORT).show(); 
	}
	@Override
	public boolean stopService(Intent name)
	{
		return super.stopService(name);
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.d("LocationService", "Get the current position \n" + location);
		
		//֪ͨActivity
		Intent intent=new Intent();
		intent.setAction(LOCATION_ACTION);
		intent.putExtra(LOCATION,location.toString());
		sendBroadcast(intent);
		
		 // ���ֻ����Ҫ��λһ�Σ�������Ƴ�������ͣ���������Ҫ����ʵʱ��λ���������˳�Ӧ�û�������ʱ��ͣ����λ����  
        locationManager.removeUpdates(this);  
        stopSelf();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
