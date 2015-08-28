package com.github.airk.democollection.customvg;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author liukai(kevinliukai@diditaxi.com)
 * @since 15/8/18.
 * <p/>
 * 显示预估价格的Layout，包含一个Loading，一个价格和优惠信息。
 */
public class PriceLayout extends ViewGroup {
    private ProgressBar mProgress;
    private TextView mPrice;
    private TextView mPriceExtra;

    public PriceLayout(Context context) {
        super(context);
        mProgress = new ProgressBar(context);
        addView(mProgress);
        mPrice = new TextView(context);
        addView(mPrice);
        mPriceExtra = new TextView(context);
        addView(mPriceExtra);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(mProgress, widthMeasureSpec, heightMeasureSpec);
        measureChild(mPriceExtra, widthMeasureSpec, heightMeasureSpec);
        measureChild(mPrice, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), Math.max(getMeasuredHeight(),
                Math.max(mPrice.getMeasuredHeight() + mPriceExtra.getMeasuredHeight(), mProgress.getMeasuredHeight())));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mProgress.getVisibility() != GONE) {
            mProgress.layout(getPaddingLeft() + getMeasuredWidth() / 2 - mProgress.getMeasuredWidth() / 2,
                    getPaddingTop() + getMeasuredHeight() / 2 - mProgress.getMeasuredHeight() / 2,
                    getPaddingLeft() + getMeasuredWidth() / 2 + mProgress.getMeasuredWidth() / 2,
                    getPaddingTop() + getMeasuredHeight() / 2 + mProgress.getMeasuredHeight() / 2);
        }
        if (mPrice.getVisibility() != GONE) {
            if (mPriceExtra.getVisibility() != GONE) {
                int totalHeight = mPriceExtra.getMeasuredHeight() + mPrice.getMeasuredHeight();
                mPrice.layout(getPaddingLeft() + getMeasuredWidth() / 2 - mPrice.getMeasuredWidth() / 2,
                        getPaddingTop() + getMeasuredHeight() / 2 - totalHeight / 2,
                        getPaddingLeft() + getMeasuredWidth() / 2 + mPrice.getMeasuredWidth() / 2,
                        getPaddingTop() + getMeasuredHeight() / 2 - totalHeight / 2 + mPrice.getMeasuredHeight());
            } else {
                mPrice.layout(getPaddingLeft() + getMeasuredWidth() / 2 - mPrice.getMeasuredWidth() / 2,
                        getPaddingTop() + getMeasuredHeight() / 2 - mPrice.getMeasuredHeight() / 2,
                        getPaddingLeft() + getMeasuredWidth() / 2 + mPrice.getMeasuredWidth() / 2,
                        getPaddingTop() + getMeasuredHeight() / 2 + mPrice.getMeasuredHeight() / 2);
            }
        }
        if (mPriceExtra.getVisibility() != GONE) {
            if (mPrice.getVisibility() != GONE) {
                int totalHeight = mPriceExtra.getMeasuredHeight() + mPrice.getMeasuredHeight();
                mPriceExtra.layout(getPaddingLeft() + getMeasuredWidth() / 2 - mPriceExtra.getMeasuredWidth() / 2,
                        getPaddingTop() + getMeasuredHeight() / 2 - totalHeight / 2 + mPrice.getMeasuredHeight(),
                        getPaddingLeft() + getMeasuredWidth() / 2 + mPriceExtra.getMeasuredWidth() / 2,
                        getPaddingTop() + getMeasuredHeight() / 2 - totalHeight / 2 + mPrice.getMeasuredHeight() + mPriceExtra.getMeasuredHeight());
            } else {
                mPriceExtra.layout(getPaddingLeft() + getMeasuredWidth() / 2 - mPriceExtra.getMeasuredWidth() / 2,
                        getPaddingTop() + getMeasuredHeight() / 2 - mPriceExtra.getMeasuredHeight() / 2,
                        getPaddingLeft() + getMeasuredWidth() / 2 + mPriceExtra.getMeasuredWidth() / 2,
                        getPaddingTop() + getMeasuredHeight() / 2 + mPriceExtra.getMeasuredHeight() / 2);
            }
        }
    }

    protected void showProgress() {
        mProgress.setVisibility(VISIBLE);
    }

    protected void hideProgress() {
        mProgress.setVisibility(GONE);
    }

    protected TextView getPriceView() {
        return mPrice;
    }

    protected TextView getPriceExtra() {
        return mPriceExtra;
    }
}
