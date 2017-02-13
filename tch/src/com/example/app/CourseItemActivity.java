package com.example.app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.app.Utils.Urls;
import com.example.app.http.SkillSale;
import com.example.app.model.SkillInfo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class CourseItemActivity extends FragmentActivity{

	private static final int MSG_SHOW_INFO=1;
	
	public static final String KEY_COURSENAME=Urls.EXTRA_COURSENAME;
	public static final String KEY_IMAGE_PATH="imagePath";
	
	private TextView tv_courseName,//课程名
	tv_courseIntro,//简介
	tv_price,//价格
	tv_score;//评分
	private RadioButton rb_sale;//已售
	private RatingBar rating_score;//星星评分
	private ImageView iv_skill;
	
	private SkillInfo skillInfo=new SkillInfo();
	private Handler handler=new Handler()
			{
				@Override
				public void handleMessage(Message msg) {
		            super.handleMessage(msg);
		            
		            switch (msg.what) {
					case MSG_SHOW_INFO:
						showInfo();
						break;
					default:
						break;
		            }}
			};
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_item);
		Log.i("CourseItemActivity","onCreate");

		InitControl();
	}
	private void InitControl()
	{
		tv_courseName=(TextView)findViewById(R.id.tv_courseName_itemAty);
		tv_courseIntro=(TextView)findViewById(R.id.tv_intro_itemAty);
		tv_price=(TextView)findViewById(R.id.tv_price_itemAty);
		tv_score=(TextView)findViewById(R.id.tv_score_itemAty);
		rb_sale=(RadioButton)findViewById(R.id.rb_sale_itemAty);
		rating_score=(RatingBar)findViewById(R.id.rating_score_itemAty);
		iv_skill=(ImageView)findViewById(R.id.iv_itemAty);
		
		Intent intent=getIntent();
		String courseName=intent.getStringExtra(KEY_COURSENAME);
		String imagePath=intent.getStringExtra(KEY_IMAGE_PATH);
		//设置课程名
		tv_courseName.setText(courseName);
		//设置imageView
		iv_skill.setImageBitmap(BitmapFactory.decodeFile(imagePath));
		getSkillInfoThread();
	}
	private void showInfo()
	{
		tv_courseName.setText(skillInfo.getName());
		tv_courseIntro.setText(skillInfo.getIntro());
		tv_price.setText("￥"+skillInfo.getPrice());
		tv_score.setText(""+skillInfo.getScore()+"分");
		rating_score.setRating(skillInfo.getScore());
		rb_sale.setText("已售 "+skillInfo.getSale()+" 节课");
		
	}
	private void getSkillInfoThread()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result=SkillSale.getSkillInfo(tv_courseName.getText().toString());
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
					
						JSONObject jsonObj=new JSONObject(result);
						skillInfo.setName(jsonObj.optString(SkillSale.KEY_NAME));
						skillInfo.setIntro(jsonObj.optString(SkillSale.KEY_INTRO));
						skillInfo.setSale(jsonObj.optInt(SkillSale.KEY_SALE));
						skillInfo.setScore((float)jsonObj.optDouble(SkillSale.KEY_SCORE));
						skillInfo.setSalerName(jsonObj.optString(SkillSale.KEY_SALER_NAME));
						skillInfo.setType(jsonObj.optString(SkillSale.KEY_SKILL_TYPE));
						skillInfo.setTime(jsonObj.optInt(SkillSale.KEY_TIME));
						skillInfo.setPrice((float)jsonObj.optDouble(SkillSale.KEY_PRICE));
						
					handler.sendEmptyMessage(MSG_SHOW_INFO);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
		}).start();

	}
}