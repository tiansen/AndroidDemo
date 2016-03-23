package com.example.ts.slifdemo.myLayout;

import android.content.Context;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class MyLinearLayout extends RelativeLayout {
    private Scroller mScroller;
    private int mTouchSlop;
    private float mLastMotionX;
    private float mLastMotionY;
    private VelocityTracker mVelocityTracker;
    private static final int SNAP_VELOCITY = 1000;
    private static final String TAG = "MyLinearLayout";
    private int width;// 这两个对象传进来就是为了获取宽度的

    public MyLinearLayout(Context context) {
        super(context);
        init();

    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        mScroller = new Scroller(getContext());
        //就是一个滑动的距离
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void smoothScrollTo() {
        int dx = -this.width - mScroller.getFinalX();
        if (dx == 0) {
            smoothScrollBy(this.width);
            return;
        }
        smoothScrollBy(-this.width);
    }

    public void smoothScrollBy(int dx) {
        int oldScrollX = getScrollX();
        mScroller.startScroll(oldScrollX, getScrollY(), dx,
                getScrollY());
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (!mScroller.isFinished()) {
            if (mScroller.computeScrollOffset()) {
                int oldX = getScrollX();
                int oldY = getScrollY();
                int x = mScroller.getCurrX();
                int y = mScroller.getCurrY();
                if (oldX != x || oldY != y) {
                    scrollTo(x, y);
                }
                invalidate();
            }
        }
        super.computeScroll();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //是否被拖动
    private boolean mIsBeingDragged;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        final int action = ev.getAction();
        final float x = ev.getX();
        final float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "down");
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastMotionX = x;
                mLastMotionY = y;
                if (getScrollX() == -getLeftWidth()
                        && mLastMotionX < getLeftWidth()) {
                    return false;
                }


                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, x + "_" + getScrollX() + "_" + "move_event");
                final float deltaX = mLastMotionX - x;//滑动的距离
                mLastMotionX = x;//上一次滑动到达的位置
                float oldScrollX = getScrollX();//现在所偏移的距离
                float scrollX = oldScrollX + deltaX;//这一次滑动后应该偏移的位置

                if (deltaX < 0 && oldScrollX <= 0) { // 向右滑动
                    final float leftBound = 0;
                    final float rightBound = -getLeftWidth();
                    if (scrollX > leftBound) {
                        scrollX = leftBound;
                    } else if (scrollX < rightBound) {
                        scrollX = rightBound;
                    }
                } else if (deltaX > 0 && oldScrollX < 0) { // 向左滑动

                } else if (deltaX > 0 && oldScrollX >= 0) {
                    scrollTo((int) 0, getScrollY());
                    return true;
                }
                Log.i(TAG, scrollX + "move_event");
                scrollTo((int) scrollX, getScrollY());

//                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP://如果移动后，View停在中间，使用以下方法。

                final VelocityTracker velocityTracker = mVelocityTracker;
                //做一个滑动速度的判断
                velocityTracker.computeCurrentVelocity(1000);
                int velocityX = (int) velocityTracker.getXVelocity();
                oldScrollX = getScrollX();
                int dx = 0;
                if (oldScrollX < 0) {
                    if (oldScrollX < -getLeftWidth() / 2
                            || velocityX > SNAP_VELOCITY) {
                        dx = -getLeftWidth() - (int) oldScrollX;
                    } else if (oldScrollX >= -getLeftWidth() / 2
                            || velocityX < -SNAP_VELOCITY) {
                        dx = (int) -oldScrollX;
                    }
                }

                smoothScrollTo(dx);

                break;

        }
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }

        return true;
    }


    private int getLeftWidth() {
        return this.width;
    }

    public void setLeftWidth(int width) {
        this.width = width;
    }

    void smoothScrollTo(int dx) {
        int duration = 500;
        int oldScrollX = getScrollX();
        mScroller.startScroll(oldScrollX, getScrollY(), dx, getScrollY(),
                duration);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        postInvalidate();
    }


}
