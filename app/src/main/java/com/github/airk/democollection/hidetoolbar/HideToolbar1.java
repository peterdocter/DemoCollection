package com.github.airk.democollection.hidetoolbar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.github.airk.democollection.R;
import com.github.airk.democollection.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin on 15/3/4.
 */
public class HideToolbar1 extends BaseActivity {

    @InjectView(R.id.list)
    RecyclerView list;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_hidetoolbar_1_layout);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(new HideAdapter(this));
        list.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new DecelerateInterpolator()).setDuration(300).start();
            }

            @Override
            public void onShow() {
                toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).setDuration(300).start();
            }
        });
    }

    static class HideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;

        private HideAdapter(Activity activity) {
            inflater = LayoutInflater.from(activity);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.item_single_text, parent, false);
            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((Holder) holder).tv.setText("" + (position + 1));
        }

        @Override
        public int getItemCount() {
            return 100;
        }

        class Holder extends RecyclerView.ViewHolder {
            @InjectView(android.R.id.text1)
            TextView tv;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.inject(this, itemView);
            }
        }
    }

    static abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
        private final int HIDE_THRESHOLD = 20;
        private int scrollDistance = 0;
        private boolean visible = true;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if (firstVisibleItemPosition == 0) {
                if (!visible) {
                    onShow();
                    visible = true;
                }
            } else {
                if (scrollDistance > HIDE_THRESHOLD && visible) {
                    onHide();
                    visible = false;
                    scrollDistance = 0;
                } else if (scrollDistance < -HIDE_THRESHOLD && !visible) {
                    onShow();
                    visible = true;
                    scrollDistance = 0;
                }
            }

            if ((visible && dy > 0) || (!visible && dy < 0)) {
                scrollDistance += dy;
            }
        }

        public abstract void onHide();

        public abstract void onShow();
    }

}
