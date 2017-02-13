package com.example.app;

import java.util.ArrayList;

import com.example.app.MyGoodsFragment.MyGoodsAllFragment;
import com.example.app.Utils.Urls;
import com.example.app.fragment.SecondFragment;
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

public class MyGoodsActivity extends FragmentActivity{

	private FragmentManager fragmentManager;
	private String topBarText="我的订单";
	private ArrayList<String> tabsText=new ArrayList<String>();
	private ArrayList<Fragment> fmList=new ArrayList<Fragment>();
	private VpTopFragment vpTop;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mygoods);
		Log.i("MyGoodsActivity","onCreate");

		InitControl();
	}
	//执行于Fragment们onCreateView之后
	@Override
	public void onStart()
	{
		super.onStart();
		Log.i("MyGoodsActivity","onStart");
		SetCurPage();
	}
	private void InitControl(){
		fragmentManager=this.getSupportFragmentManager();
		
		InitFragment();
	}
	private void SetCurPage()
	{
		Intent intent=getIntent();
		switch(intent.getIntExtra(Urls.EXTRA_VIEW, -1))
		{
		case R.id.btn_myorder_third:
		default:
			break;
		case R.id.rbt_waitPay_third:
			vpTop.setVpPage(VpTopFragment.WAITPAY_PAGE);
			break;
		case R.id.rbt_waitLearn_third:
			vpTop.setVpPage(VpTopFragment.WAITLEARN_PAGE);
			break;
		case R.id.rbt_waitEvaluate_third:
			vpTop.setVpPage(VpTopFragment.WAITEVALUATE_PAGE);
			break;
		}
	}
	private void InitFragment()
	{
		tabsText.add("全部");
		tabsText.add("待付款");
		tabsText.add("待上课");
		tabsText.add("待评价");
		
		fmList.add(new MyGoodsAllFragment());
		fmList.add(new MyGoodsAllFragment());
		fmList.add(new MyGoodsAllFragment());
		fmList.add(new MyGoodsAllFragment());
		
		FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
		
		TopBarFragment topbar=new TopBarFragment(topBarText,this);
		fragmentTransaction.replace(R.id.container_topBar_mygoods, topbar);
		
		int[] colorList=new int[]{R.color.TabHost_NoSelect,R.color.TabHost_Select};
		vpTop=new VpTopFragment(fmList, tabsText,colorList,R.color.WHITE);
		fragmentTransaction.replace(R.id.container_vp_mygoods, vpTop);
		
		fragmentTransaction.commit();
	}
}
