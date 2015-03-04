package com.github.airk.democollection.hidetoolbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;

import com.github.airk.democollection.R;

/**
 * Created by kevin on 15/1/8.
 */
public class ViewHelper {

    public static void setBackground(View v, Drawable d) {
        if (Build.VERSION.SDK_INT >= 16) {
            v.setBackground(d);
        } else {
            v.setBackgroundDrawable(d);
        }
    }

    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

}
