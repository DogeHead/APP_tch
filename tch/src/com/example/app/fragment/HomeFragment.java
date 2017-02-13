package com.example.app.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.app.CityChooseActivity;
import com.example.app.CourseItemActivity;
import com.example.app.MyGoodsActivity;
import com.example.app.R;
import com.example.app.SearchMoreActivity;
import com.example.app.HomeFragment.CitySearchBoxFragment;
import com.example.app.HomeFragment.HomeVPFirstFragment;
import com.example.app.HomeFragment.SwipeRefreshFragment;
import com.example.app.HomeFragment.VpWithRpFragment;
import com.example.app.MyClass.ListViewForScrollView;
import com.example.app.MyClass.MyScrollView;
import com.example.app.MyInterface.OnLoadInterface;
import com.example.app.Utils.Urls;
import com.example.app.http.SkillSale;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment{
	private Resources resources;
	private FragmentManager fragmentManager;
	private View theView;

	private final int MSG_ADD_SKILL=1;
	private final int MSG_INIT=2;
	private final int MSG_SET_IMAGE=3;
	private final int MSG_LOAD_IMAGE=4;
	private final int MSG_STOP_LOADTHREAD=5;
	private final int MSG_IMAGEVIEW_START=1000;
	private ArrayList<Fragment> vpWithRpList;

	private VpWithRpFragment vpWithRpFragment;

	Handler handler;
	private ArrayList<String> skill_names=new ArrayList<String>();
	private ArrayList<String> skill_intros=new ArrayList<String>();
	private ArrayList<String> skill_image_list=new ArrayList<String>();
	private ArrayList<ImageView> skill_imageview_list=new ArrayList<ImageView>();
	private boolean flag_isLoading=false;
	private int maxID=0;//当前最末尾特长的ID+1
	private int pic_id=0;//当前已有的图片id
	private int interval=600;
	private boolean flag_download=false;
	private Runnable load_image_thread=new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(!flag_download&&pic_id<maxID)
			{
				flag_download=true;
				Log.i("load_image","pic_id<maxID");
				if(SkillSale.getSkillImage(skill_names.get(pic_id)+".jpg"))
				{
					Log.i("load_image_thread","pic_id="+pic_id);
					skill_image_list.add(Urls.IMAGE_SAVE_PATH+skill_names.get(pic_id)+".jpg");
					handler.sendEmptyMessage(MSG_IMAGEVIEW_START+pic_id);
					++pic_id;
				}
				flag_download=false;
			}
		}
		
	};
	private boolean flag_more=true;//还有更多的特长
	/*
	 * 滚动视图
	 */
	private MyScrollView scroll;
	/*
	 * 猜你喜欢列表
	 */
	private LinearLayout ll_show;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//threadFlag=true;
		//newThread();
	}
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_home,container,false);
		Log.i("HomeFragment","HomeFragment.onCreateView");
		InitControl();
		return theView;
		}
	@Override
	public void onStart(){
		super.onStart();
		Log.i("onStart","HomeFragment.onStart");
		handler.sendEmptyMessage(MSG_LOAD_IMAGE);
		
	}
	@Override
	public void onPause(){
		super.onPause();
		//handler.sendEmptyMessage(MSG_STOP_LOADTHREAD);
		//handler.removeCallbacks(load_image_thread);
	}
	private void InitControl(){
		//辅助变量
		resources=getResources();
		fragmentManager=this.getFragmentManager();
		//控件
		ll_show=(LinearLayout)theView.findViewById(R.id.ll_show_home);
		scroll=(MyScrollView)theView.findViewById(R.id.myScrollView_home);
		
		
		FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
		InitVpWithRpFragment(fragmentTransaction);
		fragmentTransaction.commit();
		InitHandler();
		scroll.setOnLoad(new OnLoadInterface(){

			@Override
			public void onLoad() {
				// TODO Auto-generated method stub
//				Log.i("onLoad","到达底部");
				if(flag_isLoading==false&&flag_more)
				{
					getNewSpecial();
					flag_isLoading=true;
				}
			}
			
		});
		InitShowList();
	}
	/*
	 * 初始化VpWithRpFragment
	 */
	private void InitVpWithRpFragment(FragmentTransaction fragmentTransaction){
		
		vpWithRpList=new ArrayList<Fragment>();
		Log.i("HomeFragment","InitVpWithRpFragment");
		vpWithRpList.add(new HomeVPFirstFragment());
		//vpWithRpList.add(new ForthFragment());
		//添加VpWithRpFragment
		vpWithRpFragment=new VpWithRpFragment(vpWithRpList);
		
		fragmentTransaction.replace(R.id.frameLayout_home_container_vpWithRp,vpWithRpFragment);

	}
	
	private void InitHandler()
	{
		handler=new Handler()
		{
			@Override
			public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	            
	            switch (msg.what) {
				case MSG_ADD_SKILL:
					Log.i("1","add skill");
					AddNewSkill();
					flag_isLoading=false;
					break;
				case MSG_INIT:
					getNewSpecial();
					break;
				case MSG_STOP_LOADTHREAD:
					break;
				case MSG_LOAD_IMAGE:
					new Thread(load_image_thread).start();
					handler.sendEmptyMessageDelayed(MSG_LOAD_IMAGE, interval);
					break;
				default:
					setSkillImage(msg.what-MSG_IMAGEVIEW_START);
					break;
	            }}

		};
	}
	
	private void InitShowList()
	{
		handler.sendEmptyMessage(MSG_INIT);
		handler.sendEmptyMessageDelayed(MSG_INIT, 350);
		handler.sendEmptyMessageDelayed(MSG_INIT,700);
	}
	private void AddNewSkill()
	{
		LayoutInflater inflater=LayoutInflater.from(HomeFragment.this.getActivity());
		final LinearLayout view1=(LinearLayout)inflater.inflate(R.layout.special_item, null),
				 view2=(LinearLayout)inflater.inflate(R.layout.special_item, null);
			view1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1.0f));
			view2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1.0f));
			view1.setBackgroundDrawable(resources.getDrawable(R.drawable.button_white));
			view2.setBackgroundDrawable(resources.getDrawable(R.drawable.button_white));
			
			LinearLayout temp=new LinearLayout(HomeFragment.this.getContext());
			temp.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			temp.addView(view1);
			temp.addView(view2);
			ll_show.addView(temp);
			//获得该项的名称与简介
			final TextView tv1=(TextView)view1.findViewById(R.id.tv_special_item);
			final TextView tv_intro1=(TextView)view1.findViewById(R.id.tv_introduction_item);
			final ImageView iv1=(ImageView)view1.findViewById(R.id.iv_special_item);
			tv1.setText(skill_names.get(maxID-2));
			tv_intro1.setText(skill_intros.get(maxID-2));
			skill_imageview_list.add(iv1);
			
			final TextView tv2=(TextView)view2.findViewById(R.id.tv_special_item);
			final TextView tv_intro2=(TextView)view2.findViewById(R.id.tv_introduction_item);
			final ImageView iv2=(ImageView)view2.findViewById(R.id.iv_special_item);
			tv2.setText(skill_names.get(maxID-1));
			tv_intro2.setText(skill_intros.get(maxID-1));
			skill_imageview_list.add(iv2);
			
			
			view1.setOnClickListener(new OnClickListener(){
				final int id=maxID-2;
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent();
					intent.putExtra(CourseItemActivity.KEY_COURSENAME,tv1.getText().toString());
					intent.putExtra(CourseItemActivity.KEY_IMAGE_PATH,skill_image_list.get(id));
					intent.setClass(HomeFragment.this.getContext(), CourseItemActivity.class);
					startActivity(intent);
				}
				
			});
			if(!skill_names.get(maxID-1).equals("暂无"))
			view2.setOnClickListener(new OnClickListener(){
				final int id=maxID-1;
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Intent intent=new Intent();
					intent.putExtra(Urls.EXTRA_COURSENAME,tv2.getText().toString());
					intent.putExtra(CourseItemActivity.KEY_IMAGE_PATH,skill_image_list.get(id));
					intent.setClass(HomeFragment.this.getContext(), CourseItemActivity.class);
					startActivity(intent);
				}
				
			});
			
	}
	private void setSkillImage(int index)
	{
		skill_imageview_list.get(index).setImageBitmap(BitmapFactory.decodeFile(skill_image_list.get(index)));
	}
	private void getNewSpecial()
	{
		new Thread(new Runnable()
				{
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.i("MAXID","maxID="+maxID);
						String result=SkillSale.getTwoSkill(maxID);
						if(result==Urls.FAIL)
						{
							Log.i("getSkill","网络异常");
							return;
						}
						if(result==SkillSale.flag_unknownError)
						{
							Log.i("getSkill","未知错误");
							return;
						}
						
						Log.i("result",result);
						//jsonObj.toJSONArray(names)
						try {
							JSONArray jsonArray=new JSONArray(result);
							int len=jsonArray.length();
							if(len==0&&flag_more){
								flag_more=false;
								return;
							}
							Log.i("JSON","len="+len);
							
							for(int i=0;i<len;++i)
							{
								JSONObject jsonObj=(JSONObject)jsonArray.get(i);
								skill_names.add(jsonObj.optString(SkillSale.KEY_NAME));
								skill_intros.add(jsonObj.optString(SkillSale.KEY_INTRO));
								Log.i("name",(String)skill_names.get(maxID+i));
							}
							if(len==1)
							{
								skill_names.add("暂无");
								skill_intros.add("暂无");
							}
							maxID+=2;
							handler.sendEmptyMessage(MSG_ADD_SKILL);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			
				}).start();
	}
//	private void InitSkills()
//	{
//		for(int i=0;i<skill_names.length;++i)
//		{
//			skill_names[i]=skill_intros[i]="暂无";
//		}
//	}
}
