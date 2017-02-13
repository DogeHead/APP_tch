package com.example.app.fragment;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.app.R;
/*
 * 由于布局文件限制为4个fragment
 * 如需要更多或更少需要去xml文件中增减
 * 并在该代码中增减radioButton数目
 */
public class VpBottomFragment extends Fragment{
	private int currentIndex = 0;//当前页卡编号
	private FragmentManager fragmentManager;
	private ViewPager viewPager;
	private Resources resources;
	private ArrayList<RadioButton> radioList_tabHost;
	private ArrayList<Fragment> fmList;
	private ArrayList<String> fmText;
	private View theView;

	/*
	 * 字体不同状态的颜色
	 */
	private int textColor[];
	//tabHost不同状态的图标
	private int drawTabList_nor[];
	private int drawTabList_sel[];
	//tabHost的4个radioButton
	private RadioButton first_tabHost,second_tabHost,third_tabHost,forth_tabHost;

	/*
	 * @param
	 * fmList viewPager的Fragment
	 * fmText 每一页的名字
	 * drawTabList_nor 未选中时图标
	 * drawTabList_sel 选中时图标
	 * textColor 选中与未选中时字体颜色 textColor[0]未选中时  textColor[1]选中时
	 */
	public VpBottomFragment(ArrayList<Fragment> fmList,ArrayList<String> fmText,
			int[] drawTabList_nor,int[] drawTabList_sel,int[] textColor)
	{
		this.fmList=fmList;
		this.fmText=fmText;
		this.drawTabList_nor=drawTabList_nor;
		this.drawTabList_sel=drawTabList_sel;
		this.textColor=textColor;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_vp_bottom,container,false);
		
		InitControl();
		
		return theView;
		}
	private void InitControl(){
		
		radioList_tabHost=new ArrayList<RadioButton>();
		fragmentManager=getFragmentManager();
		resources=getResources();
		
		//获取各控件
		viewPager=(ViewPager)theView.findViewById(R.id.viewPager_mainAty_showFragment);
		
		first_tabHost=(RadioButton)theView.findViewById(R.id.radioButton_mainAty_showHome);
		second_tabHost=(RadioButton)theView.findViewById(R.id.radioButton_mainAty_showSecond);
		third_tabHost=(RadioButton)theView.findViewById(R.id.radioButton_mainAty_showThird);
		forth_tabHost=(RadioButton)theView.findViewById(R.id.radioButton_mainAty_showForth);
		radioList_tabHost.add(first_tabHost);
		radioList_tabHost.add(second_tabHost);
		radioList_tabHost.add(third_tabHost);
		radioList_tabHost.add(forth_tabHost);

		

		
		InitTabHost();
		InitViewPager();
		
		InitData();
	}
	private void InitData(){
		setNewTabHost(currentIndex);
	}
	private void InitViewPager(){
		//设置缓存页数
		viewPager.setOffscreenPageLimit(fmList.size()-1);
		//给ViewPager设置适配器
		viewPager.setAdapter(new FragmentPageAdapter(fragmentManager,fmList));
		viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setCurrentItem(0);
	}
	private void InitTabHost(){
		for(int i=0;i<radioList_tabHost.size();++i)
		{
		radioList_tabHost.get(i).setTextColor(resources.getColor(textColor[0]));
		radioList_tabHost.get(i).setText(fmText.get(i));
		
		Drawable img=resources.getDrawable(drawTabList_nor[i]);
		//没这句话的话图标无法显示
		img.setBounds(0,0,img.getMinimumWidth(),img.getMinimumHeight());
		//设置上方图标
		radioList_tabHost.get(i).setCompoundDrawables(null, img, null, null);
		
		}
		//设置初始页面	
		setNewTabHost(0);
		for(int i=0;i<radioList_tabHost.size();i++){
			radioList_tabHost.get(i).setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					switch(v.getId()){
						case R.id.radioButton_mainAty_showHome:
							viewPager.setCurrentItem(0);
							setNewTabHost(0);
							break;
						case R.id.radioButton_mainAty_showSecond:
							viewPager.setCurrentItem(1);
							setNewTabHost(1);
							break;
						case R.id.radioButton_mainAty_showThird:
							viewPager.setCurrentItem(2);
							setNewTabHost(2);
							break;
						case R.id.radioButton_mainAty_showForth:
							viewPager.setCurrentItem(3);
							setNewTabHost(3);
							break;
					}
				}});
		}
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
			if(arg0!=currentIndex)
			{
				setNewTabHost(arg0);
			}
		}
	}
	private void clearBottomBar(int curIndex){
		Drawable img=resources.getDrawable(drawTabList_nor[curIndex]);
		//没这句话的话图标无法显示
		img.setBounds(0,0,img.getMinimumWidth(),img.getMinimumHeight());
		//设置上方图标
		radioList_tabHost.get(curIndex).setCompoundDrawables(null, img, null, null);
		radioList_tabHost.get(curIndex).setTextColor(resources.getColor(textColor[0]));
	}
	//改变导航栏激活项，但并没有改变fragment
	private void setNewTabHost(int newIndex){
		clearBottomBar(currentIndex);
		currentIndex=newIndex;
		Drawable img=resources.getDrawable(drawTabList_sel[newIndex]);
		//没这句话的话图标无法显示
		img.setBounds(0,0,img.getMinimumWidth(),img.getMinimumHeight());
		//设置上方图标
		radioList_tabHost.get(newIndex).setCompoundDrawables(null, img, null, null);
		radioList_tabHost.get(newIndex).setTextColor(resources.getColor(textColor[1]));
	}

}