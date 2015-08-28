package com.github.airk.democollection.customvg;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.airk.democollection.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liukai(kevinliukai@diditaxi.com)
 * @since 15/8/17.
 */
public class BookLayout extends ViewGroup {
    private final String TAG = "BookLayout";
    private int mLineMargin;
    private Paint mLinePaint;
    private int mLineWidth = 1;

    private ImageView mClearBtn;
    private ImageView mAddRouteBtn;
    private TextView mTimeView;
    private TextView mStartAddrView;
    private TextView mEndAddrView;
    private TextView mRemarkView;
    private PriceLayout mPriceLayout;

    private int mPinHeight;
    private int mPinAnimHeight;
    private boolean mPinAnimRunning = false;

    private List<Rect> mDividers = new ArrayList<>();

    public BookLayout(Context context) {
        this(context, null);
    }

    public BookLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        mLineMargin = dp2px(10f);
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.parseColor("#333333"));
        mLinePaint.setStyle(Paint.Style.FILL);

        mTimeView = new TextView(context);
        mTimeView.setClickable(true);
        mTimeView.setBackgroundResource(R.drawable.item_selector);
        mTimeView.setLayoutParams(new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                dp2px(60f)));
        mTimeView.setText("何时出发?\n下午15:00");
        mTimeView.setGravity(Gravity.CENTER);
        addView(mTimeView);

        mStartAddrView = new TextView(context);
        mStartAddrView.setClickable(true);
        mStartAddrView.setBackgroundResource(R.drawable.item_selector);
        mStartAddrView.setLayoutParams(new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                dp2px(60f)));
        mStartAddrView.setText("出发地");
        mStartAddrView.setGravity(Gravity.CENTER);
        addView(mStartAddrView);

        mEndAddrView = new TextView(context);
        mEndAddrView.setClickable(true);
        mEndAddrView.setBackgroundResource(R.drawable.item_selector);
        mEndAddrView.setLayoutParams(new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                dp2px(60f)));
        mEndAddrView.setText("目的地");
        mEndAddrView.setGravity(Gravity.CENTER);
        addView(mEndAddrView);

        mRemarkView = new TextView(context);
        mRemarkView.setClickable(true);
        mRemarkView.setBackgroundResource(R.drawable.item_selector);
        mRemarkView.setLayoutParams(new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                dp2px(60f)));
        mRemarkView.setText("留言给车主");
        mRemarkView.setGravity(Gravity.CENTER);
        addView(mRemarkView);

        mPriceLayout = new PriceLayout(context);
        mPriceLayout.setClickable(true);
        mPriceLayout.setBackgroundResource(R.drawable.item_selector);
        addView(mPriceLayout, new LayoutParams(LayoutParams.MATCH_PARENT, dp2px(87f)));

        mClearBtn = new ImageView(context);
        mClearBtn.setClickable(true);
        mClearBtn.setBackgroundResource(R.drawable.clear_btn_selector);
        addView(mClearBtn);

        mAddRouteBtn = new ImageView(context);
        mAddRouteBtn.setClickable(true);
        mAddRouteBtn.setBackgroundResource(R.drawable.circle_btn_selector);
        addView(mAddRouteBtn);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getPaddingTop() + getPaddingBottom();
        /* 清除按钮 */
        measureChild(mClearBtn, widthMeasureSpec, heightMeasureSpec);
        height += mClearBtn.getMeasuredHeight() / 2; //清除按钮的一半需求在高度里
        measureChild(mTimeView, widthMeasureSpec, heightMeasureSpec);
        height += mTimeView.getMeasuredHeight();
        measureChild(mStartAddrView, widthMeasureSpec, heightMeasureSpec);
        height += (mStartAddrView.getMeasuredHeight() + mLineWidth);
        measureChild(mEndAddrView, widthMeasureSpec, heightMeasureSpec);
        height += (mEndAddrView.getMeasuredHeight() + mLineWidth);
        if (mRemarkView.getVisibility() != GONE && mPriceLayout.getVisibility() != GONE) {
            measureChild(mRemarkView, widthMeasureSpec, heightMeasureSpec);
            measureChild(mPriceLayout, widthMeasureSpec, heightMeasureSpec);
            mPinHeight = mRemarkView.getMeasuredHeight() + mPriceLayout.getMeasuredHeight() + mLineWidth * 2;
            if (!mPinAnimRunning) {
                height += (mRemarkView.getMeasuredHeight() + mPriceLayout.getMeasuredHeight() + mLineWidth);
            } else {
                height += mPinAnimHeight;
            }
        }
        measureChild(mAddRouteBtn, widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredWidth(), height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int yPointer = getPaddingTop() + mClearBtn.getMeasuredHeight() / 2;
        mDividers.clear();
        if (mRemarkView.getVisibility() != GONE && mPriceLayout.getVisibility() != GONE) {
            int top = yPointer + mTimeView.getMeasuredHeight() + mStartAddrView.getMeasuredHeight() + mEndAddrView.getMeasuredHeight()
                    + mLineWidth * 3;
            mRemarkView.layout(getPaddingLeft(), top,
                    getPaddingLeft() + mRemarkView.getMeasuredWidth(),
                    top + mRemarkView.getMeasuredHeight());
            mDividers.add(getDividerRect(mRemarkView));
            mPriceLayout.layout(getPaddingLeft(), mRemarkView.getBottom() + mLineWidth,
                    getPaddingLeft() + mPriceLayout.getMeasuredWidth(),
                    mRemarkView.getBottom() + mLineWidth + mPriceLayout.getMeasuredHeight());
        }
        mTimeView.layout(getPaddingLeft(), yPointer,
                getPaddingLeft() + mTimeView.getMeasuredWidth(),
                yPointer + mTimeView.getMeasuredHeight());
        mDividers.add(getDividerRect(mTimeView));
        yPointer += (mTimeView.getMeasuredHeight() + mLineWidth);
        mStartAddrView.layout(getPaddingLeft(), yPointer,
                getPaddingLeft() + mStartAddrView.getMeasuredWidth(),
                yPointer + mStartAddrView.getMeasuredHeight());
        mDividers.add(getDividerRect(mStartAddrView));
        yPointer += (mStartAddrView.getMeasuredHeight() + mLineWidth);
        mEndAddrView.layout(getPaddingLeft(), yPointer,
                getPaddingLeft() + mEndAddrView.getMeasuredWidth(),
                yPointer + mEndAddrView.getMeasuredHeight());
        if (mRemarkView.getVisibility() == VISIBLE) {
            mDividers.add(getDividerRect(mEndAddrView));
        }
        mClearBtn.layout(getMeasuredWidth() - mClearBtn.getMeasuredWidth() - dp2px(10f),
                getPaddingTop(), getMeasuredWidth() - dp2px(10f),
                getPaddingTop() + mClearBtn.getMeasuredHeight());
        mAddRouteBtn.layout(getMeasuredWidth() - mAddRouteBtn.getMeasuredWidth() - dp2px(20f),
                mEndAddrView.getTop() - mAddRouteBtn.getMeasuredHeight() / 2,
                getMeasuredWidth() - dp2px(20f),
                mEndAddrView.getTop() + mAddRouteBtn.getMeasuredHeight() / 2);
    }

    @Override
    public void childDrawableStateChanged(View child) {
        super.childDrawableStateChanged(child);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    private Rect getDividerRect(View v) {
        return new Rect(getPaddingLeft() + mLineMargin, v.getBottom(),
                getMeasuredWidth() - getPaddingRight() - mLineMargin,
                v.getBottom() + mLineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable bg = getResources().getDrawable(R.drawable.custom_bg);
        if (bg != null) {
            bg.setBounds(getPaddingLeft(), getPaddingTop() + mClearBtn.getMeasuredHeight() / 2,
                    getMeasuredWidth() - getPaddingRight() + getPaddingLeft(),
                    getMeasuredHeight() - getPaddingBottom());
            bg.draw(canvas);
        }
        for (Rect r : mDividers) {
            drawDivider(canvas, r);
        }
    }

    private void drawDivider(Canvas canvas, Rect rect) {
        canvas.drawRect(rect, mLinePaint);
    }

    private int dp2px(float dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    public void hideRemarkAndPriceView(boolean anim) {
        if (mRemarkView.getVisibility() != VISIBLE || mPriceLayout.getVisibility() != VISIBLE)
            return;
        if (!anim) {
            mRemarkView.setVisibility(GONE);
            mPriceLayout.setVisibility(GONE);
        } else {
            ValueAnimator valueAnimator = ValueAnimator.ofInt(mPinHeight, 0);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mPinAnimHeight = (Integer) animation.getAnimatedValue();
                    requestLayout();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRemarkView.setVisibility(GONE);
                    mPriceLayout.setVisibility(GONE);
                    mPinAnimRunning = false;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    mPinAnimRunning = true;
                }
            });
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.setDuration(350).start();
        }
    }

    public void showRemarkAndPriceView(boolean anim) {
        if (mRemarkView.getVisibility() != GONE || mPriceLayout.getVisibility() != GONE)
            return;
        if (!anim) {
            mRemarkView.setVisibility(VISIBLE);
            mPriceLayout.setVisibility(VISIBLE);
        } else {
            ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mPinHeight);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mPinAnimHeight = (Integer) animation.getAnimatedValue();
                    requestLayout();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mRemarkView.setVisibility(VISIBLE);
                    mPriceLayout.setVisibility(VISIBLE);
                    mPinAnimRunning = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mPinAnimRunning = false;
                }
            });
            valueAnimator.setInterpolator(new AccelerateInterpolator());
            valueAnimator.setDuration(350).start();
        }
    }

}
