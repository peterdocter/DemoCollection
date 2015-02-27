package com.github.airk.democollection.wheeldialog;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.airk.democollection.R;
import com.github.airk.democollection.ui.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kevin on 15/2/27.
 */
public class WheelActivity extends BaseActivity {

    @InjectView(R.id.three_btn)
    Button threeBtn;

    private int offset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_layout);
        ButterKnife.inject(this);

        offset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40f, getResources().getDisplayMetrics());
    }

    @OnClick(R.id.three_btn)
    void doShowDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.three_wheel_dialog, null);

        ListView yearList = (ListView) view.findViewById(R.id.year);
        yearList.setAdapter(new WheelAdapter(this));
        yearList.setOnScrollListener(new WheelScrollListener());
        ListView monthList = (ListView) view.findViewById(R.id.month);
        monthList.setAdapter(new WheelAdapter(this));
        monthList.setOnScrollListener(new WheelScrollListener());
        ListView dayList = (ListView) view.findViewById(R.id.day);
        dayList.setAdapter(new WheelAdapter(this));
        dayList.setOnScrollListener(new WheelScrollListener());
        new MaterialDialog.Builder(this)
                .customView(view, false)
                .show();
    }

    private class WheelScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == SCROLL_STATE_IDLE) {
                int first = view.getFirstVisiblePosition();
                int last = view.getLastVisiblePosition();
                if (first == 0) {
                    view.smoothScrollToPositionFromTop(1, offset, 220);
                } else if (last == view.getChildCount() - 1) {
                    view.smoothScrollToPositionFromTop(view.getChildCount() - 2, offset, 220);
                } else {
                    view.smoothScrollToPositionFromTop(first + 1, offset, 220);
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }
}
