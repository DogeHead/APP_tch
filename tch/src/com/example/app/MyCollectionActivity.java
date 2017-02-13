package com.example.app;

import java.util.ArrayList;

import com.example.app.MyCollectionFragment.*;
import com.example.app.MyGoodsFragment.MyGoodsAllFragment;
import com.example.app.Utils.Urls;
import com.example.app.fragment.TopBarFragment;
import com.example.app.fragment.VpTopFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;

public class MyCollectionActivity extends FragmentActivity{

	private FragmentManager fragmentManager;
	private String topBarText="我的收藏";
	private ArrayList<String> tabsText=new ArrayList<String>();
	private ArrayList<Fragment> fmList=new ArrayList<Fragment>();
	private VpTopFragment vpTop;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mycollection);
		Log.i("MyCollectionActivity","onCreate");

		InitControl();
	}
	private void InitControl()
	{
		fragmentManager=this.getSupportFragmentManager();
		
		InitFragment();
	}
	private void InitFragment()
	{
		tabsText.add("课程");
		tabsText.add("卖家");
		
		fmList.add(new MyCourseFragment());
		fmList.add(new MyTeacherFragment());
		
		FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
		
		TopBarFragment topbar=new TopBarFragment(topBarText,this);
		fragmentTransaction.replace(R.id.container_topBar_mycollection, topbar);
		
		int[] colorList=new int[]{R.color.TabHost_NoSelect,R.color.TabHost_Select};
		vpTop=new VpTopFragment(fmList, tabsText,colorList,R.color.WHITE);
		fragmentTransaction.replace(R.id.container_vp_mycollection, vpTop);
		
		fragmentTransaction.commit();
	}
}
