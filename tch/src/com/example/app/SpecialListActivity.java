package com.example.app;

import com.example.app.fragment.SpecialListFragment;
import com.example.app.fragment.TopBarFragment;
import com.example.app.fragment.VpBottomFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;

public class SpecialListActivity extends FragmentActivity{

	public final static String EX_TYPE="selectType";
	private FragmentManager fragmentManager;
	private String selectType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_special_list);
		Log.i("Main","Main.onCreate");
		Intent intent=this.getIntent();
		selectType=intent.getStringExtra(EX_TYPE);
		//获得各控件句柄
		InitControl();
		
	}

	private void InitControl()
	{
		fragmentManager=this.getSupportFragmentManager();
		
		
		
		FragmentTransaction ft=fragmentManager.beginTransaction();
		
		ft.replace(R.id.container_topBar_specialList,
				new TopBarFragment("特长",this));
		ft.replace(R.id.container_specialList_specialList,
				new SpecialListFragment(selectType));
		ft.commit();
	}
	
}
