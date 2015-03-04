package com.github.airk.democollection.hidetoolbar;

import android.content.Intent;
import android.os.Bundle;

import com.github.airk.democollection.R;
import com.github.airk.democollection.ui.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kevin on 15/3/4.
 */
public class HideToolbarParent extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidetoolbar_parent);
        ButterKnife.inject(this);
    }

    @OnClick(android.R.id.button1)
    void doStartHide1() {
        startActivity(new Intent(this, HideToolbar1.class));
    }

    @OnClick(android.R.id.button2)
    void doStarHide2() {
        startActivity(new Intent(this, HideToolbar2.class));
    }
}
