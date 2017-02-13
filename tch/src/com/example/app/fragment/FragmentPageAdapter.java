package com.example.app.fragment;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentPageAdapter extends FragmentPagerAdapter{

	ArrayList<Fragment> fmList;
	public FragmentPageAdapter(FragmentManager fm,ArrayList<Fragment> list) {
		super(fm);
		// TODO Auto-generated constructor stub
		fmList=list;
	}
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fmList.get(arg0);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fmList.size();
	}

}
