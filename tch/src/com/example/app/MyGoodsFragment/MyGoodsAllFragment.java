package com.example.app.MyGoodsFragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.app.CourseItemActivity;
import com.example.app.R;
import com.example.app.MyCollectionFragment.MyCourseFragment;
import com.example.app.Utils.Urls;
import com.example.app.http.SkillSale;

public class MyGoodsAllFragment extends Fragment{
	private final int MSG_IMAGEVIEW_START=1000;
	private View theView;
	private LinearLayout ll_list;
	private ArrayList<ImageView> skill_imageview_list=new ArrayList<ImageView>();
	private ArrayList<String> skill_image_list=new ArrayList<String>();
	private String names[]={
			"赵老师","钱老师","孙老师","李老师"
	};
	private String courses[]={
			"足球课程","二胡课程",
			"绘画课程","篮球课程"
	};
	private String States[]={
			"待上课","待评价","待评价","待上课"
	};
	private Handler handler=new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
            super.handleMessage(msg);
            
            switch (msg.what) {
			default:
				setSkillImage(msg.what-MSG_IMAGEVIEW_START);
				break;
            }}
	};
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_mygoodsall,container,false);
		
		InitControl();
		return theView;
		}
	
	private void InitControl()
	{
		ll_list=(LinearLayout)theView.findViewById(R.id.ll_list_mygoodsall);

		LayoutInflater inflater=LayoutInflater.from(this.getActivity());
		
		for(int i=0;i<names.length;++i)
		{
		final LinearLayout view1=(LinearLayout)inflater.inflate(R.layout.mygoods_item, null);
		final TextView tvAuthorName=(TextView)view1.findViewById(R.id.tv_authorName_goodsItem),
				tvState=(TextView)view1.findViewById(R.id.tv_state_goodsItem),
				tvCourseName=(TextView)view1.findViewById(R.id.tv_courseName_courseInfo);
		final ImageView tempiv=(ImageView)view1.findViewById(R.id.iv_courseInfo);
		
		skill_imageview_list.add(tempiv);
		skill_image_list.add(Urls.IMAGE_SAVE_PATH+courses[i]+".jpg");
		tvAuthorName.setText(names[i]);
		tvState.setText(States[i]);
		tvCourseName.setText(courses[i]);
		view1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra(CourseItemActivity.KEY_COURSENAME, tvCourseName.getText().toString());
				intent.putExtra(CourseItemActivity.KEY_IMAGE_PATH, 
						Urls.IMAGE_SAVE_PATH+tvCourseName.getText().toString()+".jpg");
				intent.setClass(MyGoodsAllFragment.this.getContext(),CourseItemActivity.class);
				startActivity(intent);
			}	
		});
		ll_list.addView(view1);
		}
		setImageThread();
	}
	private void setSkillImage(int index)
	{
		skill_imageview_list.get(index).setImageBitmap(BitmapFactory.decodeFile(skill_image_list.get(index)));
	}
	private void setImageThread()
	{
		new Thread(new Runnable()
				{
					@Override
					public void run() {
						// TODO Auto-generated method stub
						for(int i=0;i<courses.length;++i)
						{
							if(SkillSale.getSkillImage(courses[i]+".jpg"))
							{
								handler.sendEmptyMessage(MSG_IMAGEVIEW_START+i);								}
							}
						}
				}).start();
	}
}