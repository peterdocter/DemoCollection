package com.github.airk.democollection.scrollrecycler;

import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.airk.democollection.R;
import com.github.airk.democollection.ui.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin on 15/3/10.
 */
public class ScrollRecyclerActivity extends BaseActivity {

    @InjectView(android.R.id.list)
    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_recyclerview_layout);
        ButterKnife.inject(this);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        FixedItemHeightLinearLayoutManager layoutManager = new FixedItemHeightLinearLayoutManager(this, 500);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);
        list.setAdapter(new LargeAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_scroll_recycler, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_up) {
            list.smoothScrollToPosition(0);
            return true;
        } else if (id == R.id.action_down) {
            list.smoothScrollToPosition(list.getAdapter().getItemCount());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class FixedItemHeightLinearLayoutManager extends LinearLayoutManager {
        int duration;

        public FixedItemHeightLinearLayoutManager(Context context, int duration) {
            super(context);
            this.duration = duration;
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            View firstVisibleView = recyclerView.getChildAt(0);
            int itemHeight = firstVisibleView.getHeight();
            int currentPosition = recyclerView.getChildPosition(firstVisibleView);

            int distanceInPixel = Math.abs((currentPosition - position) * itemHeight);
            if (distanceInPixel == 0) {
                distanceInPixel = (int) Math.abs(firstVisibleView.getY());
            }
            SmoothScroll scroll = new SmoothScroll(ScrollRecyclerActivity.this, distanceInPixel, duration);
            scroll.setTargetPosition(position);
            startSmoothScroll(scroll);
        }


        private class SmoothScroll extends LinearSmoothScroller {
            private int TARGET_SEEK_SCROLL_DISTANCE_PX = 10000;
            private float distance;
            private float duration;

            public SmoothScroll(Context context, int distance, int duration) {
                super(context);
                this.distance = distance;

                float msPerPx = calculateSpeedPerPixel(context.getResources().getDisplayMetrics());
                this.duration = distance < TARGET_SEEK_SCROLL_DISTANCE_PX ? (int) (Math.abs(distance) * msPerPx) : duration;
            }

            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return FixedItemHeightLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            @Override
            protected int calculateTimeForScrolling(int dx) {
                float proportion = (float) dx / distance;
                return (int) (duration * proportion);
            }
        }
    }


}
