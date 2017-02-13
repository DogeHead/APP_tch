package com.example.app.MyClass;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;
public class ListViewForScrollView extends ListView {
    public ListViewForScrollView(Context context) {
        super(context);
    }
    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ListViewForScrollView(Context context, AttributeSet attrs,
        int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     * 会导致默认显示首项是ListView，需要手动将ScrollView滚动至最顶端
     * sv = (ScrollView) findViewById(R.id.act_solution_4_sv);
     * sv.smoothScrollTo(0, 0);
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
        MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}