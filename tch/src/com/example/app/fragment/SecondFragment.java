package com.example.app.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.app.CourseItemActivity;
import com.example.app.R;
import com.example.app.SearchMoreActivity;
import com.example.app.MyClass.MyScrollView;
import com.example.app.MyInterface.OnLoadInterface;
import com.example.app.Utils.Urls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class SecondFragment extends Fragment implements OnClickListener{
	private View theView;
	private Resources resources;
	private ImageView iv_search;

	FragmentManager fragmentManager;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_second,container,false);
		Log.i("SecondFragment","SecondFragment.onCreateView");
		InitControl();
		return theView;
		}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.iv_search_second:
			Intent intent=new Intent();
			intent.setClass(this.getContext(), SearchMoreActivity.class);
			startActivity(intent);
			break;
		}
	}
	private void InitControl()
	{
		iv_search=(ImageView)theView.findViewById(R.id.iv_search_second);
		
		resources=this.getResources();
		fragmentManager=this.getChildFragmentManager();
		FragmentTransaction ft=fragmentManager.beginTransaction();
		
		ft.replace(R.id.frameLayout_second_container_specialList,
				new SpecialListFragment("È«²¿"));
		ft.commit();
		
		iv_search.setOnClickListener(this);
	}

}