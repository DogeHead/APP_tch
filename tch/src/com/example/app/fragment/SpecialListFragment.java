package com.example.app.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.app.CourseItemActivity;
import com.example.app.R;
import com.example.app.SearchMoreActivity;
import com.example.app.MyClass.MyScrollView;
import com.example.app.MyInterface.OnLoadInterface;
import com.example.app.Utils.Urls;
import com.example.app.http.SkillSale;
import com.example.app.model.SkillInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;

public class SpecialListFragment extends Fragment implements OnClickListener{
	private final int MSG_UPDATE_ALLKIND=1;
	private final int MSG_ADD_SKILL=2;
	private final int MSG_IMAGEVIEW_START=1000;
	private View theView;
	private Resources resources;
	private MyScrollView myScrollView;
	private int index_helper;
	private LinearLayout ll_show,ll_allKind;
	private TextView tv_allKind;
	private List<String> groupArray;  
	private List<List<String>> childArray;
	private PopupWindow popupWindow;
	private String[] specialType=new String[]{"摄影","游戏","美工","电脑","学习","乐器","运动"};
	private String selectType;
	private ArrayList<SkillInfo> skillInfo_list=new ArrayList<SkillInfo>();
	private ArrayList<String> skill_image_list=new ArrayList<String>();
	private ArrayList<ImageView> skill_imageview_list=new ArrayList<ImageView>();
	
	public SpecialListFragment()
	{
		selectType="全部";
	}

	public SpecialListFragment(String type)
	{
		selectType=type;
	}

	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
            super.handleMessage(msg);
            
            switch (msg.what) {
			case MSG_UPDATE_ALLKIND:
				tv_allKind.setText(selectType);
				getSelSkillThread();
				break;
			case MSG_ADD_SKILL:
				InitLLShow();
				break;
			default:
				setSkillImage(msg.what-MSG_IMAGEVIEW_START);
				break;
            }}
	};

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		theView=inflater.inflate(R.layout.fragment_special_list,container,false);
		Log.i("SpecialListFragment","SpecialListFragment.onCreateView");
		InitControl();
		return theView;
		}
	private void InitControl()
	{
		resources=this.getResources();
		
		myScrollView=(MyScrollView)theView.findViewById(R.id.myScrollView_second);
		ll_show=(LinearLayout)theView.findViewById(R.id.ll_show_second);
		ll_allKind=(LinearLayout)theView.findViewById(R.id.ll_allKind_select);
		tv_allKind=(TextView)theView.findViewById(R.id.tv_selBar_allKind);

		ll_allKind.setOnClickListener(this);
		handler.sendEmptyMessage(MSG_UPDATE_ALLKIND);
		
		
		
		//popupWindow
		groupArray = new  ArrayList<String>();  
		childArray = new  ArrayList<List<String>>();  
		  
		groupArray.add("全部" );  
		  
		List<String> tempArray = new  ArrayList<String>();  
		for(int i=0;i<specialType.length;++i)
		{
			tempArray.add(specialType[i]);
		}
		childArray.add(tempArray);
		
		//scrollView
		InitLLShow();

		myScrollView.setOnLoad(new OnLoadInterface(){
			
			@Override
			public void onLoad() {}
});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.ll_allKind_select:
			showPopWindow(theView,this.getContext(),Gravity.CENTER,R.layout.popup_moreinfo,0,0);
			break;
		case R.id.ll_next_moreInfo:
			popupWindow.dismiss();
			break;
		}

	}

	private void InitLLShow()
	{
		ll_show.removeAllViews();
		
		LayoutInflater inflater=LayoutInflater.from(SpecialListFragment.this.getActivity());

		for(int i=0;i<skillInfo_list.size();++i)
		{
		index_helper=i;
		//间隔
		LinearLayout ll_division=new LinearLayout(SpecialListFragment.this.getContext());
		ll_division.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,Urls.DIVISION_BIG));
		ll_show.addView(ll_division);	
		
		final LinearLayout view1=(LinearLayout)inflater.inflate(R.layout.courseinfo_body, null);
		final TextView tvCourseName=(TextView)view1.findViewById(R.id.tv_courseName_courseInfo),
				tvTime=(TextView)view1.findViewById(R.id.tv_time_courseInfo),
				tvPrice=(TextView)view1.findViewById(R.id.tv_price_courseInfo);
		final ImageView iv=(ImageView)view1.findViewById(R.id.iv_courseInfo);
		
		skill_imageview_list.add(iv);
		tvCourseName.setText(skillInfo_list.get(i).getName());
		tvTime.setText(""+skillInfo_list.get(i).getTime()+"学时");
		tvPrice.setText("￥"+skillInfo_list.get(i).getPrice());
		iv.setImageBitmap(BitmapFactory.decodeFile(skill_image_list.get(i)));
		view1.setOnClickListener(new OnClickListener(){
			final int id=index_helper;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1=new Intent();
				intent1.putExtra(CourseItemActivity.KEY_COURSENAME,tvCourseName.getText().toString());
				intent1.putExtra(CourseItemActivity.KEY_IMAGE_PATH, skill_image_list.get(id));
				intent1.setClass(SpecialListFragment.this.getContext(), CourseItemActivity.class);
				startActivity(intent1);
			}
			
		});
		ll_show.addView(view1);
		}
		getAndSetImageThread();
	}

	private void setSkillImage(int index)
	{
		skill_imageview_list.get(index).setImageBitmap(BitmapFactory.decodeFile(skill_image_list.get(index)));
	}
	
	private void getSelSkillThread()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result=SkillSale.getSelectSkill(tv_allKind.getText().toString());
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
					Log.i("JSON","len="+len);
					skillInfo_list.clear();
					for(int i=0;i<jsonArray.length();++i)
					{
						JSONObject jsonObj=(JSONObject)jsonArray.get(i);
						SkillInfo temp=new SkillInfo();
						temp.setName(jsonObj.optString(SkillSale.KEY_NAME));
						temp.setTime(jsonObj.optInt(SkillSale.KEY_TIME));
						temp.setPrice((float)jsonObj.optDouble(SkillSale.KEY_PRICE));
						
						skillInfo_list.add(temp);
						skill_image_list.add(Urls.IMAGE_SAVE_PATH+temp.getName()+".jpg");
						
						
						//Log.i("name",temp.getName());
					}
					handler.sendEmptyMessage(MSG_ADD_SKILL);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
		}).start();

	}
	
	private void getAndSetImageThread()
	{
		new Thread(new Runnable()
				{
						@Override
						public void run() {
							// TODO Auto-generated method stub
							for(int i=0;i<skillInfo_list.size();++i)
							{
								if(SkillSale.getSkillImage(skillInfo_list.get(i).getName()+".jpg"))
								{
									handler.sendEmptyMessage(MSG_IMAGEVIEW_START+i);								}
								}
							
						}
				}).start();
		
	}
	
	private void showPopWindow(View view,Context context,int gravity,int layout,int x,int y)
	{
		View contentView=LayoutInflater.from(context).inflate(layout, null);
		
		popupWindow=new PopupWindow(contentView,LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT,true);
		popupWindow.setTouchable(true);
		popupWindow.setTouchInterceptor(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				//如果为true，事件将被拦截，
				return false;
			}
			
		});
		switch(layout)
		{
		case R.layout.popup_moreinfo:
			final LinearLayout ll_back=(LinearLayout)contentView.findViewById(R.id.ll_next_moreInfo);
			
			final ExpandableListView expandableListView=(ExpandableListView)contentView.findViewById(R.id.expandableListView_pop);
			
			expandableListView.setAdapter(new  ExpandableAdapter(this.getActivity(),groupArray,childArray));
			expandableListView.setVelocityScale(0.5F);
			expandableListView.setOnChildClickListener(new OnChildClickListener(){

				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					// TODO Auto-generated method stub
					selectType=specialType[childPosition];
					handler.sendEmptyMessage(MSG_UPDATE_ALLKIND);
					popupWindow.dismiss();
					return false;
				}
				
			});
			ll_back.setOnClickListener(this);
			
			break;
		}
		popupWindow.showAtLocation(view, gravity, x, y);
		
	}

	public  class  ExpandableAdapter extends  BaseExpandableListAdapter  
	{  
		private List<String> groupArray;  
		private List<List<String>> childArray;
	    Activity activity;  
	     
	    public  ExpandableAdapter(Activity a,List<String>group,List<List<String>> child)  
	    {  
	        activity = a;  
	        groupArray=group;
	        childArray=child;
	    }  
	    @Override
	    public  Object getChild(int  groupPosition, int  childPosition)  
	    {  
	        return  childArray.get(groupPosition).get(childPosition);  
	    }  
	    @Override
	    public  long  getChildId(int  groupPosition, int  childPosition)  
	    {  
	        return  childPosition;  
	    } 
	    @Override
	    public  int  getChildrenCount(int  groupPosition)  
	    {  
	        return  childArray.get(groupPosition).size();  
	    }  
	    @Override
	    public  View getChildView(int  groupPosition, int  childPosition,  
	            boolean  isLastChild, View convertView, ViewGroup parent)  
	    {  
	        String string = childArray.get(groupPosition).get(childPosition);  
	        return  getGenericView(string,false,false);  
	    }  
	    @Override
	    public  Object getGroup(int  groupPosition)  
	    {  
	        return  groupArray.get(groupPosition);
	    }  
	    @Override
	    public  int  getGroupCount()  
	    {  
	        return  groupArray.size();  
	    }  
	    @Override
	    public  long  getGroupId(int  groupPosition)  
	    {  
	        return  groupPosition;  
	    }
	    @Override
	    public  View getGroupView(int  groupPosition, boolean  isExpanded,  
	            View convertView, ViewGroup parent)  
	    {  

	        String string = groupArray.get(groupPosition);  
	        return  getGenericView(string,true,isExpanded);  
	    }  
	    // View stub to create Group/Children 's View   
	    public  View getGenericView(String string,boolean isRoot,boolean isExpanded)  
	    {  
	        // Layout parameters for the ExpandableListView   
	    	LayoutInflater inflater=LayoutInflater.from(SpecialListFragment.this.getContext());
	    	View view=inflater.inflate(R.layout.expandablelist_item, null);
	    	final TextView tv=(TextView)view.findViewById(R.id.tv_expandItem);
	    	tv.setText(string);
	    	if(isRoot)
	    	{
	    		tv.setTextSize(20f);
	    	}
	    	else{
	    		tv.setTextSize(14f);
	    	}
	        return  view;
	    }  
	    public  boolean  hasStableIds()  
	    {  
	        return  false ;  
	    }  
	    public  boolean  isChildSelectable(int  groupPosition, int  childPosition)  
	    {  
	        return  true;
	    }  
	}  
}
