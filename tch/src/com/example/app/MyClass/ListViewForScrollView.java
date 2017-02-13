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
     * ��д�÷������ﵽʹListView��ӦScrollView��Ч��
     * �ᵼ��Ĭ����ʾ������ListView����Ҫ�ֶ���ScrollView���������
     * sv = (ScrollView) findViewById(R.id.act_solution_4_sv);
     * sv.smoothScrollTo(0, 0);
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
        MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}