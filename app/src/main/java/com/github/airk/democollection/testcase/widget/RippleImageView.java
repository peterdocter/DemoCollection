package com.github.airk.democollection.testcase.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.airk.democollection.R;

/**
 * Created by kevin on 15/4/2.
 */
public class RippleImageView extends FrameLayout {
    private ImageView imgView;

    int[] bg = new int[]{R.attr.selectableItemBackground};
    int[] extra = new int[]{android.R.attr.scaleType};

    private static final ImageView.ScaleType[] sScaleTypeArray = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };

    public RippleImageView(Context context) {
        this(context, null);
    }

    public RippleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray b = context.obtainStyledAttributes(bg);
        Drawable d = b.getDrawable(0);
        if (d != null) {
            setForeground(d);
        }
        setClickable(true);
        b.recycle();

        imgView = new ImageView(context);
        addView(imgView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        TypedArray a = context.obtainStyledAttributes(attrs, extra);
        int scaleType = a.getInt(0, -1);
        if (scaleType != -1) {
            imgView.setScaleType(sScaleTypeArray[scaleType]);
        }
        a.recycle();
    }

    public void setImageBitmap(Bitmap bm) {
        imgView.setImageBitmap(bm);
    }

    public void setImageDrawable(Drawable d) {
        imgView.setImageDrawable(d);
    }

    public void setImageResource(int resId) {
        imgView.setImageResource(resId);
    }
}
