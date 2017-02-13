package com.example.app.HomeFragment;

import java.util.ArrayList;

import com.example.app.R;
import com.example.app.fragment.ForthFragment;
import com.example.app.fragment.FragmentPageAdapter;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class VpWithRpFragment extends Fragment{
	private View theView;
	private ViewPager viewPager;
	//private LinearLayout ll_roundPointGroup;
	//圆点的list
	private ArrayList<ImageView> iv_roundPointList;
	private Resources resources;
	private FragmentManager fragmentManager;
	private ArrayList<Fragment> fragmentList;
	private int curIndex;
	public VpWithRpFragment(ArrayList<Fragment> fmList){
		fragmentList=fmList;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_viewpager_with_roundpoint,container,false);
		Log.i("VpWithRpFragment","VpWithRpFragment.onCreateView");
		
		InitControl();
		return theView;
		}
	@Override
	public void onResume(){
		super.onResume();
	}
	@Override 
    public void onPause() {  
        super.onPause();  
    }  
	@Override 
    public void onDestroyView() {  
        super.onDestroyView();  
    }  
	private void InitControl(){
		resources=getResources();
		fragmentManager=this.getFragmentManager();
		iv_roundPointList=new ArrayList<ImageView>();

		curIndex=0;
		
		viewPager=(ViewPager)theView.findViewById(R.id.viewPager_viewPagerWithPoint_show);
		//ll_roundPointGroup=(LinearLayout)theView.findViewById(R.id.ll_viewPagerWithPoint_roundPointGroup);
		
		
		
		InitViewPager();
		
		InitRoundPointGroup();
	}
	/*
	 * 初始化viewPager
	 */
	private void InitViewPager(){
		Log.i("VpWithRpFragment","VpWithRpFragment.InitViewPager");
		//给viewPager设置适配器
		
		Log.i("VpWithRpFragment"," fmList.size()="+fragmentList.size());
		viewPager.setAdapter(new FragmentPageAdapter(this.getChildFragmentManager(),fragmentList));
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setCurrentItem(0);
	}
	private void InitRoundPointGroup(){
		Log.i("VpWithRpFragment","VpWithRpFragment.InitRoundPointGroup");
		for(int i=0;i<fragmentList.size();i++){
			ImageView iv=new ImageView(getContext());
			iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			iv.setScaleType(ScaleType.FIT_XY);
			if(i==0)
				{
				iv.setImageResource(R.drawable.point_adver_sel);
				}
			else{
				iv.setImageResource(R.drawable.point_adver_nor);
			}
			iv_roundPointList.add(iv);
			//ll_roundPointGroup.addView(iv);
			}
		
	}
	/*
	 * 显示新的小圆点
	 */
	private void setNewRoundPoint(int newIndex){
		iv_roundPointList.get(curIndex).setImageResource(R.drawable.point_adver_nor);
		iv_roundPointList.get(newIndex).setImageResource(R.drawable.point_adver_sel);
		curIndex=newIndex;
	}
	/*
	 * viewPager监听器
	 */
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
				Log.i("VpWithRpFragment","VpWithRpFragment.onPageSelected");
				setNewRoundPoint(arg0);
			}
		}
	}
	/*
	 * 以下public部分
	 */
	/*
	 * 设置要显示的Fragment的List
	 */
	public void setFragmentList(ArrayList<Fragment> fmList){
		fragmentList=fmList;
	}
}
