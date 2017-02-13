package com.example.app;

import com.example.app.http.SkillSale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

public class SplashActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		PackageManager pm = getPackageManager();
		try {
		    PackageInfo pi = pm.getPackageInfo("com.lyt.android", 0);
		    TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
		    versionNumber.setText("Version " + pi.versionName);
		} catch (NameNotFoundException e) {
		    e.printStackTrace();
		}

		new Handler().postDelayed(new Runnable(){

		@Override
		public void run() {
		    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
		    startActivity(intent);
		    SplashActivity.this.finish();
		}

		}, 0);
		
		//下载所需资源线程
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SkillSale.getSkillImage("error.jpg");
			}
			
		}).start();
	}
}
