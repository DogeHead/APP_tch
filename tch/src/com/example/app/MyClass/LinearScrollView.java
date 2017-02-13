package com.example.app.MyClass;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.app.R;
import com.example.app.MyInterface.OnLoadInterface;
/*
 * 失败
 * 错误语句：mLinearLayout.addView(mLinearViewFooter);
 * 错误原因：ava.lang.IllegalStateException: The specified child already has a parent. 
 * You must call removeView() on the child's parent first.
 * 
 */
public class LinearScrollView extends ScrollView{
	private OnLoadInterface mOnLoad;
	private LinearLayout mLinearLayout;
	private View mLinearViewFooter;
	private boolean isLoading;
	public LinearScrollView(Context context)
    {
        super(context);
    }
    public LinearScrollView(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        
        mLinearViewFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer, null,
    	        false);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        View view = (View)getChildAt(getChildCount()-1);
        int d = view.getBottom();
        d -= (getHeight()+getScrollY());
//        Log.i("onScrollChanged","scrollY"+getScrollY());
        if(d<=50)
        {
            //you are at the end of the list in scrollview 
            //do what you wanna do here
        	loadData();
        }
        else
            super.onScrollChanged(l,t,oldl,oldt);
    }
    @Override
	  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
	    super.onLayout(changed, left, top, right, bottom);

	    // 初始化ListView对象
	    if (mLinearLayout == null) {
	    	getLinearLayout();
	    }
	    
	  }
    private void getLinearLayout()
    {
    	int childs=getChildCount();
    	if(childs>0)
    	{
    		View childView=getChildAt(0);
    		mLinearLayout=(LinearLayout)childView;
    	}
	    
    }
    
    public void setOnLoad(OnLoadInterface load)
    {
    	mOnLoad=load;
    }
    public void setLoading(boolean loading) {
	    isLoading = loading;
	    if (isLoading) {
	      mLinearLayout.addView(mLinearViewFooter);
	    } else {
	      mLinearLayout.removeView(mLinearViewFooter);
	    }
	  }
    public void loadData()
    {
    	if(mOnLoad!=null)
    	{
    	//设置加载动画
    	setLoading(true);	
    	mOnLoad.onLoad();
    	}
    }
}

