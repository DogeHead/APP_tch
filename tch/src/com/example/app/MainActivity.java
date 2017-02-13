package com.example.app;

import java.io.File;
import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.Utils.Urls;
import com.example.app.fragment.*;
public class MainActivity extends FragmentActivity {
	
	private FragmentManager fragmentManager;
	private Resources resources;

	/*
	 * 是否确认退出
	 */
	private boolean isExit=false;
	/*
	 * 退出的Message
	 */
	private final int EXIT_APP = 10;
	/*
	 * Handler
	 */
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
            super.handleMessage(msg);
            
            switch (msg.what) {
			case EXIT_APP:
				isExit=false;
				break;
			default:
				break;
            }}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Log.i("Main","Main.onCreate");
		//获得各控件句柄
		InitControl();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			exit();
			return true;
		}
		return false;
	}
	public void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            //2s后重置退出判断条件
            handler.sendEmptyMessageDelayed(EXIT_APP, 2000);
        } else {
        	clearImages();
        	android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
	private void clearImages()
	{
		Log.i("MainActivity","clearImages");
		File file=new File(Urls.IMAGE_SAVE_PATH);
		if(file.isDirectory())
		{
			File[] childFile=file.listFiles();
			if (childFile == null || childFile.length == 0) { 
                file.delete(); 
                return; 
            } 
			for(File f:childFile)
			{
				Log.i("delete file",f.getName());
				f.delete();
			}
		}
		else{
			Log.i("MainActivity","not dir");
		}
	}
	private void InitControl(){
		fragmentManager=getSupportFragmentManager();
		resources=getResources();

		ArrayList<Fragment> fmList=new ArrayList<Fragment>();
		ArrayList<String> strList=new ArrayList<String>();
		
		fmList.add(new HomeFragment());
		fmList.add(new SecondFragment());
		fmList.add(new ThirdFragment());
		fmList.add(new ForthFragment());
		strList.add("首页");
		strList.add("特长");
		strList.add("我的");
		strList.add("设置");
		/*
		 * 字体不同状态的颜色
		 */
		int textColor[]=new int[]{
			R.color.TabHost_NoSelect,R.color.TabHost_Select	
		};
		//tabHost不同状态的图标
		int drawTabList_nor[]=new int[]{
			R.drawable.icon_home_nor,R.drawable.icon_square_nor,
			R.drawable.icon_meassage_nor,R.drawable.icon_selfinfo_nor
		};
		int drawTabList_sel[]=new int[]{
				R.drawable.icon_home_sel,R.drawable.icon_square_sel,
				R.drawable.icon_meassage_sel,R.drawable.icon_selfinfo_sel
			};
		FragmentTransaction ft=fragmentManager.beginTransaction();
		
		ft.replace(R.id.ll_container_mainAty,
				new VpBottomFragment(fmList, strList,drawTabList_nor,drawTabList_sel,textColor));
		
		ft.commit();
	}

}
