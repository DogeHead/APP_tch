package com.example.app.fragment;

import java.util.ArrayList;

import com.example.app.R;
import com.example.app.HomeFragment.VpWithRpFragment.MyOnPageChangeListener;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VpTopFragment extends Fragment{
	public static final int ALL_PAGE=0,
			WAITPAY_PAGE=1,
			WAITLEARN_PAGE=2,
			WAITEVALUATE_PAGE=3;
	private Resources resources;
	private ArrayList<Fragment> fmList;
	private ArrayList<String> fmText;
	private ArrayList<TextView> fmTv=new ArrayList<TextView>();
	private ArrayList<LinearLayout> fmLl=new ArrayList<LinearLayout>();
	//选中与未选中颜色
	private int[] colorList;
	private int bg_color;
	private View theView;
	private ViewPager vp;
	private LinearLayout ll;
	private String[] tags={
			"一","二","三","四"
	};
	//当前页
	private int curIndex=0;
	public VpTopFragment(ArrayList<Fragment> fmList,ArrayList<String> fmText,int[] colorList,int bg_color)
	{
		this.fmList=fmList;
		this.fmText=fmText;
		this.colorList=colorList;
		this.bg_color=bg_color;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_vp_top,container,false);
		Log.i("VpTopFragment", "onCreateView");
		InitControl();
		
		return theView;
		}
	//public方法
	public void setVpPage(int index)
	{
		Log.i("setVpPage",""+index);
		if(0<=index&&index<fmList.size())
		{
		if(null!=vp)
		vp.setCurrentItem(index);
		}
	}
	private void InitControl()
	{
		resources=this.getResources();
		vp=(ViewPager)theView.findViewById(R.id.viewPager_vptopFragment);
		ll=(LinearLayout)theView.findViewById(R.id.ll_vptopFragment);
		
		
		InitViewPager();
		InitLinearLayout();
		
	}
	
	private void InitViewPager()
	{
		vp.setAdapter(new FragmentPageAdapter(this.getChildFragmentManager(),fmList));
		vp.setOffscreenPageLimit(3);
//		vp.setOnPageChangeListener(new MyOnPageChangeListener());
		vp.addOnPageChangeListener(new MyOnPageChangeListener());
		vp.setCurrentItem(0);
	}
	private void InitLinearLayout()
	{
		for(int i=0;i<fmList.size();++i)
		{
			//创建一个容纳TextView与容纳LinearLayout的LinearLayout
			LinearLayout ll_temp=new LinearLayout(this.getContext());
			ll_temp.setOrientation(LinearLayout.VERTICAL);
			ll_temp.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT,1.0f));
			ll_temp.setTag(tags[i]);
			ll_temp.setOnClickListener(new MyOnClickListener());
			
			//创建一个TextView
			TextView tv_temp=new TextView(this.getContext());
			tv_temp.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT,1.0f));
			tv_temp.setPadding(10, 30, 10, 30);
			tv_temp.setTextSize(15);
			tv_temp.setTextColor(resources.getColor(colorList[0]));
			tv_temp.setGravity(Gravity.CENTER);
			tv_temp.setText(fmText.get(i));
			
			//创建一个表示下方颜色的LinearLayout
			LinearLayout subll_temp=new LinearLayout(this.getContext());
			subll_temp.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					5,0.0f));
			subll_temp.setBackgroundColor(resources.getColor(bg_color));
			
			fmTv.add(tv_temp);
			fmLl.add(subll_temp);
			ll_temp.addView(tv_temp);
			ll_temp.addView(subll_temp);
			
			ll.addView(ll_temp);
		}
		setTabNewState(0);
	}
	private void setTabNewState(int newIndex)
	{

		fmTv.get(curIndex).setTextColor(resources.getColor(colorList[0]));
		fmLl.get(curIndex).setBackgroundColor(resources.getColor(bg_color));
		
		fmTv.get(newIndex).setTextColor(resources.getColor(colorList[1]));
		fmLl.get(newIndex).setBackgroundColor(resources.getColor(colorList[1]));
		curIndex=newIndex;
	}
	public class MyOnPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			//Log.i("TAG","Main.onPageScrollStateChanged");
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			//Log.i("TAG","Main.onPageScrolled");
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			//Log.i("TAG","Main.onPageSelected");
			if(arg0!=curIndex)
			{
				Log.i("VptopFragment","onPageSelected");
				setTabNewState(arg0);
			}
		}
	}
	public class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			for(int i=0;i<fmList.size();++i)
				if(v.getTag().toString().equals(tags[i]))
				{
					vp.setCurrentItem(i);
					break;
				}
			
		}
		
	}
}
