package com.github.airk.democollection.scrollview;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.github.airk.democollection.R;

/**
 * Created by kevin on 15/6/26.
 */
public class LoadingView extends FrameLayout {
    View mInnerView;
    public LoadingView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.sview_loading_header, this);
        mInnerView = findViewById(R.id.content);
    }
}
