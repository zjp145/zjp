package com.zhang.sqone.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定的Viewpager添加监听事件
 */
public class MyViewPager extends ViewPager {
    //滑动事件的使用判断
    private boolean scrollble = true;
    //构造方法
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //控件发生触摸事件的回调接口
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //
        if(scrollble)
            return super.onTouchEvent(ev);
        else
            return false;
    }
    //用于触摸事件的拦截 当方法返回的是ture的时候将就不会向子模块传递触摸事件了
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if(scrollble)
            return super.onInterceptTouchEvent(arg0);
        else
            return false;
    }

    //判断当前的滑动事件
    public boolean isScrollble() {
        return scrollble;
    }
    //设置是否滑动 可以在MyViewpager中动态的设置
    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }

}
