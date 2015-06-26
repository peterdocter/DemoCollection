package com.github.airk.democollection.scrollview;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

/**
 * Created by kevin on 15/6/24.
 */
public abstract class SView extends LinearLayout {
    private final String TAG = "SView";
    private final int INVALID_POINTER = -1;

    private int mInitialX;
    private int mInitialY;
    private int mLastX;
    private int mLastY;
    private int mActivePointer = INVALID_POINTER;
    private boolean mIsDragged = false;
    private int mTouchSlop;
    private int mLastAction = -1;
    private boolean canOverScroll = true;

    private SmoothScroller mCurrentScroller;
    private LoadingView headerView;

    private OnRefreshListener listener;

    public SView(Context context) {
        this(context, null);
    }

    public SView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        headerView = new LoadingView(getContext());
        init();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    protected void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        if (headerView.getParent() == this) {
            removeView(headerView);
        }
        addView(headerView, 0, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        headerView.setVisibility(INVISIBLE);
        refreshView();
    }

    protected abstract boolean isReadyToPullFromStart();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        refreshView();
    }

    private void refreshView() {
        int topPad = getPaddingTop();
        int bottomPad = getPaddingBottom();
        int leftPad = getPaddingLeft();
        int rightPad = getPaddingRight();

        ViewGroup.LayoutParams lp = headerView.getLayoutParams();
        lp.height = getMaxHeaderHeight();
        headerView.requestLayout();
        setPadding(leftPad, -getMaxHeaderHeight(), rightPad, bottomPad);
    }

    private int getMaxHeaderHeight() {
        return (int) (getHeight() / 2.0f * 1.2f);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if (mLastAction == -1 || mLastAction != action) {
            Log.d(TAG, "IT Action: " + action);
            mLastAction = action;
        }

        if (!isReadyToPullFromStart()) {
            mIsDragged = false;
            return false;
        }

        if (action != MotionEvent.ACTION_DOWN && mIsDragged) {
            return true;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mIsDragged = false;
                if (mActivePointer == INVALID_POINTER) {
                    mActivePointer = MotionEventCompat.getPointerId(ev, 0);
                }
                if (mActivePointer == INVALID_POINTER)
                    break;
                mLastX = mInitialX = (int) MotionEventCompat.getX(ev, mActivePointer);
                mLastY = mInitialY = (int) MotionEventCompat.getY(ev, mActivePointer);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mActivePointer == INVALID_POINTER)
                    break;
                int x = (int) MotionEventCompat.getX(ev, mActivePointer);
                int y = (int) MotionEventCompat.getY(ev, mActivePointer);
                int diffX = x - mLastX;
                int diffY = y - mLastY;
                Log.d(TAG, "ITITIT   X:" + mLastX + " Y:" + mLastY);

                if (mTouchSlop < Math.abs(diffY) && diffY > 1 && isReadyToPullFromStart()) {
                    mIsDragged = true;
                    mLastX = x;
                    mLastY = y;
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsDragged = false;
                break;
        }
        return mIsDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if (mLastAction == -1 || mLastAction != action) {
            Log.d(TAG, "Action: " + action);
            mLastAction = action;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if (mActivePointer == INVALID_POINTER) {
                    mActivePointer = MotionEventCompat.getPointerId(ev, 0);
                }
                if (mActivePointer == INVALID_POINTER)
                    break;
                mLastX = mInitialX = (int) MotionEventCompat.getX(ev, mActivePointer);
                mLastY = mInitialY = (int) MotionEventCompat.getY(ev, mActivePointer);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mIsDragged) {
                    if (mActivePointer == INVALID_POINTER)
                        break;
                    mLastX = (int) MotionEventCompat.getX(ev, mActivePointer);
                    mLastY = (int) MotionEventCompat.getY(ev, mActivePointer);
                    int diffY = mLastY - mInitialY;
                    Log.d(TAG, "X:" + mLastX + " Y:" + mLastY + " Diff: " + diffY);
                    if (diffY > 1) {
                        headerView.setVisibility(VISIBLE);
                        int scrollDistance = Math.min((int) (diffY / 2.0f), canOverScroll ? getMaxHeaderHeight() : headerView.mInnerView.getHeight());
                        scrollTo(0, -scrollDistance);
                    }
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsDragged = false;
                mLastAction = -1;
                if (mLastY - mInitialY >= headerView.mInnerView.getHeight()) {
//                    int diffY = getScrollY() - headerView.mInnerView.getHeight();
                    smoothScrollTo(-headerView.mInnerView.getHeight());
                    onReleaseToRefresh();
                } else {
                    onReleaseToReset();
                }
                mLastX = mLastY = mInitialX = mInitialY = -1;
        }
        return false;
    }

    private void onReleaseToRefresh() {
        if (listener != null) {
            listener.onRefresh();
        }
    }

    public void onRefreshComplete() {
        smoothScrollTo(0);
    }

    private void onReleaseToReset() {
        smoothScrollTo(0);
    }

    private void smoothScrollTo(int targetY) {
        if (mCurrentScroller != null) {
            mCurrentScroller.stop();
        }
        mCurrentScroller = new SmoothScroller(getScrollY(), targetY, 200);
        post(mCurrentScroller);
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    private class SmoothScroller implements Runnable {
        private final int fromY;
        private final int toY;
        private final long duration;
        private long startTime = -1L;
        private Interpolator interpolator = new AccelerateInterpolator();
        private int currentY = -1;

        private SmoothScroller(int fromY, int toY, long duration) {
            this.fromY = fromY;
            this.toY = toY;
            this.duration = duration;
        }

        @Override
        public void run() {
            if (startTime == -1L) {
                startTime = System.currentTimeMillis();
            } else {
                long now = System.currentTimeMillis();
                float factor = (now - startTime) / (float) duration;

                int deltaY = Math.round((fromY - toY) * interpolator.getInterpolation(factor));
                currentY = fromY - deltaY;
                if (currentY == 0) {
                    headerView.setVisibility(INVISIBLE);
                }
                if (currentY > toY) {
                    currentY = toY;
                }
                scrollTo(0, currentY);
            }
            if (currentY != toY) {
                postOnAnimation(this);
            }
        }

        void stop() {
            removeCallbacks(this);
        }
    }
}
