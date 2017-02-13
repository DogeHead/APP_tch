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
 * ���ڲ����ļ�����Ϊ4��fragment
 * ����Ҫ����������Ҫȥxml�ļ�������
 * ���ڸô���������radioButton��Ŀ
 */
public class VpBottomFragment extends Fragment{
	private int currentIndex = 0;//��ǰҳ�����
	private FragmentManager fragmentManager;
	private ViewPager viewPager;
	private Resources resources;
	private ArrayList<RadioButton> radioList_tabHost;
	private ArrayList<Fragment> fmList;
	private ArrayList<String> fmText;
	private View theView;

	/*
	 * ���岻ͬ״̬����ɫ
	 */
	private int textColor[];
	//tabHost��ͬ״̬��ͼ��
	private int drawTabList_nor[];
	private int drawTabList_sel[];
	//tabHost��4��radioButton
	private RadioButton first_tabHost,second_tabHost,third_tabHost,forth_tabHost;

	/*
	 * @param
	 * fmList viewPager��Fragment
	 * fmText ÿһҳ������
	 * drawTabList_nor δѡ��ʱͼ��
	 * drawTabList_sel ѡ��ʱͼ��
	 * textColor ѡ����δѡ��ʱ������ɫ textColor[0]δѡ��ʱ  textColor[1]ѡ��ʱ
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
		
		//��ȡ���ؼ�
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
		//���û���ҳ��
		viewPager.setOffscreenPageLimit(fmList.size()-1);
		//��ViewPager����������
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
		//û��仰�Ļ�ͼ���޷���ʾ
		img.setBounds(0,0,img.getMinimumWidth(),img.getMinimumHeight());
		//�����Ϸ�ͼ��
		radioList_tabHost.get(i).setCompoundDrawables(null, img, null, null);
		
		}
		//���ó�ʼҳ��	
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
		//û��仰�Ļ�ͼ���޷���ʾ
		img.setBounds(0,0,img.getMinimumWidth(),img.getMinimumHeight());
		//�����Ϸ�ͼ��
		radioList_tabHost.get(curIndex).setCompoundDrawables(null, img, null, null);
		radioList_tabHost.get(curIndex).setTextColor(resources.getColor(textColor[0]));
	}
	//�ı䵼�������������û�иı�fragment
	private void setNewTabHost(int newIndex){
		clearBottomBar(currentIndex);
		currentIndex=newIndex;
		Drawable img=resources.getDrawable(drawTabList_sel[newIndex]);
		//û��仰�Ļ�ͼ���޷���ʾ
		img.setBounds(0,0,img.getMinimumWidth(),img.getMinimumHeight());
		//�����Ϸ�ͼ��
		radioList_tabHost.get(newIndex).setCompoundDrawables(null, img, null, null);
		radioList_tabHost.get(newIndex).setTextColor(resources.getColor(textColor[1]));
	}

}