package com.example.app.MyCollectionFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app.CourseItemActivity;
import com.example.app.R;
import com.example.app.Utils.Urls;

public class MyCourseFragment extends Fragment{
	private View theView;
	private LinearLayout ll_list;
	private String courses[]={};
	private String times[]={};
	private String prices[]={
	};
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_courses_mycollection,container,false);
		
		InitControl();
		return theView;
		}
	
	private void InitControl()
	{
		ll_list=(LinearLayout)theView.findViewById(R.id.ll_list_mycollection);
		
		LayoutInflater inflater=LayoutInflater.from(this.getActivity());
		for(int i=0;i<courses.length;++i){
		//¼ä¸ô
		LinearLayout ll_division=new LinearLayout(this.getContext());
		ll_division.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,Urls.DIVISION_BIG));
		ll_list.addView(ll_division);	
		
		final LinearLayout view1=(LinearLayout)inflater.inflate(R.layout.mycollection_item, null);
		final TextView tvCourseName=(TextView)view1.findViewById(R.id.tv_courseName_courseInfo),
				tvTime=(TextView)view1.findViewById(R.id.tv_time_courseInfo),
				tvPrice=(TextView)view1.findViewById(R.id.tv_price_courseInfo);

		tvCourseName.setText(courses[i]);
		tvTime.setText(times[i]);
		tvPrice.setText(prices[i]);
		view1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra(Urls.EXTRA_COURSENAME, tvCourseName.getText().toString());
				intent.setClass(MyCourseFragment.this.getContext(),CourseItemActivity.class);
				startActivity(intent);
			}	
		});
		ll_list.addView(view1);
		}
		
	}
}
