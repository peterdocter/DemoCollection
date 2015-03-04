package com.github.airk.democollection.rxjava;

import android.os.Bundle;

import com.github.airk.democollection.R;
import com.github.airk.democollection.ui.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kevin on 15/3/3.
 */
public class RxActivity extends BaseActivity {

    private RxUse rxUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_layout);
        ButterKnife.inject(this);

        rxUse = new RxUse();
    }

    @OnClick(R.id.rxjava_btn)
    void doUseRxJava() {
        rxUse.firstUse();
    }

    @OnClick(R.id.observe_btn)
    void doObserve() {
        rxUse.createUse();
    }
}
