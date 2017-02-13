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
		 else Toast.makeText(this, "无法定位", Toast.LENGTH_SHORT).show(); 
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
		
		//通知Activity
		Intent intent=new Intent();
		intent.setAction(LOCATION_ACTION);
		intent.putExtra(LOCATION,location.toString());
		sendBroadcast(intent);
		
		 // 如果只是需要定位一次，这里就移除监听，停掉服务。如果要进行实时定位，可以在退出应用或者其他时刻停掉定位服务。  
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
