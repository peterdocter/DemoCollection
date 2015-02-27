package com.github.airk.democollection.ui;

import android.content.Intent;
import android.os.Bundle;

import com.github.airk.democollection.R;
import com.github.airk.democollection.materialprogress.MaterialProgressActivity;
import com.github.airk.democollection.palette.PaletteActivity;
import com.github.airk.democollection.wheeldialog.WheelActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.palette_btn) void doPalette() {
        startActivity(new Intent(this, PaletteActivity.class));
    }

    @OnClick(R.id.wheel_btn) void doWheel() {
        startActivity(new Intent(this, WheelActivity.class));
    }

    @OnClick(R.id.progress_btn) void doProgress() {
        startActivity(new Intent(this, MaterialProgressActivity.class));
    }

}
