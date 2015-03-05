package com.github.airk.democollection.materialprogress.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

/**
 * Created by kevin on 15/2/27.
 * <p/>
 * WIP
 */
public class MaterialProgressBar extends View {
    private final String TAG = MaterialProgressBar.class.getSimpleName();

    private final int DEFAULT_DARK_COLOR = Color.BLACK;
    private final int DEFAULT_LIGHT_COLOR = Color.GRAY;

    private int proStyle = STYLE_CIRCLE;
    public static final int STYLE_CIRCLE = 0x101;
    public static final int STYLE_BAR = 0x102;

    private final int DEFAULT_ANIM_DURATION_IN_MS = 1200;

    private int circleSize;
    private int strokeSize;
    public static final int CIRCLE_SIZE_SMALL_IN_DP = 28;
    public static final int CIRCLE_SIZE_NORMAL_IN_DP = 40;
    public static final int CIRCLE_SIZE_LARGE_IN_DP = 64;

    private final int DEFAULT_STROKE_SIZE_IN_DP = 4;

    private ArrayList<Integer> colors;

    private Interpolator interpolator = new LinearInterpolator();

    private RectF container;

    private Paint firstPaint;
    private Paint secPaint;

    private float ticker = 0f;
    private float firstPercent = Float.MIN_VALUE;
    private float secPercent = Float.MIN_VALUE;

    public MaterialProgressBar(Context context) {
        this(context, null);
    }

    public MaterialProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        circleSize = (int) (CIRCLE_SIZE_NORMAL_IN_DP * getResources().getDisplayMetrics().density);
        strokeSize = (int) (DEFAULT_STROKE_SIZE_IN_DP * getResources().getDisplayMetrics().density);
    }

    int counter;
    float angle = -90f;

    @Override
    protected void onDraw(Canvas canvas) {
        if (firstPercent == Float.MIN_VALUE) {
            firstPercent = 0f;
        }
        if (secPercent == Float.MIN_VALUE) {
            secPercent = 0f;
        }

        if (firstPaint == null) {
            firstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            firstPaint.setStyle(Paint.Style.STROKE);
//            firstPaint.setStyle(Paint.Style.FILL);
            firstPaint.setColor(DEFAULT_DARK_COLOR);
//            firstPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                    DEFAULT_STROKE_SIZE_IN_DP, getResources().getDisplayMetrics()));
            firstPaint.setStrokeWidth(1);
        }
        if (secPaint == null) {
            secPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            secPaint.setStyle(Paint.Style.STROKE);
            secPaint.setColor(DEFAULT_LIGHT_COLOR);
            secPaint.setStrokeWidth(3);
        }

        if (container == null) {
            int left = (getWidth() - circleSize) / 2;
            int top = (getHeight() - circleSize) / 2;
            int right = getWidth() - left;
            int bottom = getHeight() - top;
            container = new RectF(left, top, right, bottom);
        }

//        canvas.drawArc(container, 135 * firstPercent + 135 * counter, 320 * firstPercent, false, firstPaint);

        canvas.drawArc(container, -90f + 360 * firstPercent, 1f, true, firstPaint);
        canvas.drawArc(container, (-90f + counter * 30) + 30 * firstPercent, 1f, true, firstPaint);
        canvas.drawCircle(container.centerX(), container.centerY(), circleSize / 2, secPaint);

        step();
        invalidate();
    }

    private void step() {
        if (ticker /*% 1*/ >= 1f) {
            ticker = 0f;
            counter++;
        }
        secPercent = firstPercent = interpolator.getInterpolation(ticker);
        ticker += 0.01;

        if (firstPercent >= 1f) {
            firstPercent = Float.MIN_VALUE;
        }
        if (secPercent >= 1f) {
            secPercent = Float.MIN_VALUE;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveSize(circleSize + strokeSize, widthMeasureSpec),
                resolveSize(circleSize + strokeSize, heightMeasureSpec));
    }
}
