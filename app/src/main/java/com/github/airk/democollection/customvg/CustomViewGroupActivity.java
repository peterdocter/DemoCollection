package com.github.airk.democollection.customvg;

import android.os.Bundle;

import com.github.airk.democollection.BaseActivity;
import com.github.airk.democollection.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author liukai(kevinliukai@diditaxi.com)
 * @since 15/8/17.
 */
public class CustomViewGroupActivity extends BaseActivity {
    @InjectView(R.id.book_layout)
    BookLayout bookLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_viewgroup_layout);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.hide) void doHide() {
        bookLayout.hideRemarkAndPriceView(true);
    }

    @OnClick(R.id.show) void doShow() {
        bookLayout.showRemarkAndPriceView(true);
    }

    @OnClick(R.id.show_price) void doShowPrice() {
//        bookLayout.showPrice();
    }

    @OnClick(R.id.load_price) void doLoadPrice() {
//        bookLayout.loadPrice();
    }
}
