package com.github.airk.democollection.materialprogress.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by kevin on 15/2/27.
 * <p/>
 * <declare-styleable name="ProgressClock">
 * <attr name="hour_width" format="dimension|reference" />
 * <attr name="hour_color" format="color|reference" />
 * <attr name="hour_ratio" format="float" />
 * <attr name="min_width" format="dimension|reference" />
 * <attr name="min_color" format="color|reference" />
 * <attr name="clock_bg" format="reference" />
 * <attr name="stroke_width" format="dimension|reference" />
 * </declare-styleable>
 */
public class ProgressClock extends View {
    private final String TAG = ProgressClock.class.getSimpleName();

    private static final int DEFAULT_CLOCK_TOTAL_SIZE = 40; //in dp
    private static final int DEFAULT_POINTER_COLOR = Color.BLACK;
    private static final int DEFAULT_HOUR_POINTER_WIDTH = 2; //in dp
    private static final int DEFAULT_MIN_POINTER_WIDTH = 1; //in dp
    private static final int DEFAULT_STROKE_WIDTH = 4; //in dp
    private static final float DEFAULT_RATIO = 0.8f;

    private int clockSize;
    private int clockTotalSize;
    private int strokeSize;
    private int hourPointWidth;
    private int minPointWidth;
    private Drawable background;

    private Interpolator interpolator = new LinearInterpolator();
    private RectF minRect;
    private RectF hourRect;

    private Paint minPaint;
    private Paint hourPaint;
    private Paint strokePaint;
    private Paint bgPaint;

    private float ticker = 0f;
    private float minPercent = Float.MIN_VALUE;
    private float hourPercent = Float.MIN_VALUE;

    public ProgressClock(Context context) {
        this(context, null);
    }

    public ProgressClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        TypedArray array = context.obtainStyledAttributes(attrs, )
        clockTotalSize = (int) (DEFAULT_CLOCK_TOTAL_SIZE * getResources().getDisplayMetrics().density);
        strokeSize = (int) (DEFAULT_STROKE_WIDTH * getResources().getDisplayMetrics().density);
    }

    int counter;

    @Override
    protected void onDraw(Canvas canvas) {
        if (minPercent == Float.MIN_VALUE) {
            minPercent = 0f;
        }
        if (hourPercent == Float.MIN_VALUE) {
            hourPercent = 0f;
        }

        if (minPaint == null) {
            minPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            minPaint.setStyle(Paint.Style.STROKE);
//            minPaint.setStyle(Paint.Style.FILL);
            minPaint.setColor(DEFAULT_POINTER_COLOR);
//            minPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                    DEFAULT_STROKE_SIZE_IN_DP, getResources().getDisplayMetrics()));
            minPaint.setStrokeWidth(1);
        }
        if (hourPaint == null) {
            hourPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            hourPaint.setStyle(Paint.Style.STROKE);
            hourPaint.setColor(DEFAULT_POINTER_COLOR);
            hourPaint.setStrokeWidth(3);
        }

        if (minRect == null) {
            int left = (getWidth() - clockTotalSize) / 2;
            int top = (getHeight() - clockTotalSize) / 2;
            int right = getWidth() - left;
            int bottom = getHeight() - top;
            minRect = new RectF(left, top, right, bottom);
        }
        if (hourRect == null) {
            int left = (int) ((getWidth() - clockTotalSize * 0.8f) / 2);
            int top = (int) ((getHeight() - clockTotalSize * 0.8f) / 2);
            int right = (int) (left + clockTotalSize * 0.8f);
            int bottom = (int) (top + clockTotalSize * 0.8f);
            hourRect = new RectF(left, top, right, bottom);
        }

//        canvas.drawArc(minRect, 135 * minPercent + 135 * counter, 320 * minPercent, false, minPaint);

        canvas.drawArc(minRect, -90f + 360 * minPercent, 1f, true, minPaint);
        canvas.drawArc(hourRect, (-90f + counter * 30) + 30 * minPercent, 1f, true, minPaint);
        canvas.drawCircle(minRect.centerX(), minRect.centerY(), clockTotalSize / 2, hourPaint);

        step();
        invalidate();
    }

    private void step() {
        if (ticker /*% 1*/ >= 1f) {
            ticker = 0f;
            counter++;
        }
        hourPercent = minPercent = interpolator.getInterpolation(ticker);
        ticker += 0.01;

        if (minPercent >= 1f) {
            minPercent = Float.MIN_VALUE;
        }
        if (hourPercent >= 1f) {
            hourPercent = Float.MIN_VALUE;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveSize(clockTotalSize + strokeSize, widthMeasureSpec),
                resolveSize(clockTotalSize + strokeSize, heightMeasureSpec));
    }
}
