package com.github.airk.democollection.hidetoolbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.github.airk.democollection.R;
import com.github.airk.democollection.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin on 15/3/4.
 */
public class HideToolbar2 extends BaseActivity {
    @InjectView(R.id.list)
    RecyclerView list;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.toolbar_container)
    View toolbarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_hidetoolbar_2_layout);
        ButterKnife.inject(this);

        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(new HideAdapter(this));
        list.setPadding(list.getPaddingLeft(), list.getPaddingTop() + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48f, getResources().getDisplayMetrics()),
                list.getPaddingRight(), list.getPaddingBottom());
        list.setOnScrollListener(new HidingScrollListener(this) {
            @Override
            public void onMoved(int distance) {
                toolbarContainer.setTranslationY(-distance);
            }

            @Override
            public void onShow() {
                toolbarContainer.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void onHide() {
                toolbarContainer.animate().translationY(-ViewHelper.getActionBarHeight(HideToolbar2.this)).setInterpolator(new AccelerateInterpolator(2)).start();
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
        private static final float HIDE_THRESHOLD = 20;
        private static final float SHOW_THRESHOLD = 70;

        private int toolbarOffset = 0;
        private int toolbarHeight;
        private boolean visible = true;

        protected HidingScrollListener(Context ctx) {
            toolbarHeight = ViewHelper.getActionBarHeight(ctx);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (getFirstPosition(recyclerView) == 0) {
                    setVisible();
                } else {
                    if (visible) {
                        if (toolbarOffset > HIDE_THRESHOLD) {
                            setInvisible();
                        } else {
                            setVisible();
                        }
                    } else {
                        if ((toolbarHeight - toolbarOffset) > SHOW_THRESHOLD) {
                            setVisible();
                        } else {
                            setInvisible();
                        }
                    }
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (getFirstPosition(recyclerView) == 0) {
                return;
            }

            clipToolbarOffset();
            onMoved(toolbarOffset);

            if ((toolbarOffset < toolbarHeight && dy > 0) || (toolbarOffset > 0 && dy < 0)) {
                toolbarOffset += dy;
            }
        }

        private int getFirstPosition(RecyclerView recyclerView) {
            return ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        }

        private void clipToolbarOffset() {
            if (toolbarOffset > toolbarHeight) {
                toolbarOffset = toolbarHeight;
            } else if (toolbarOffset < 0) {
                toolbarOffset = 0;
            }
        }

        private void setVisible() {
            if (toolbarOffset > 0) {
                onShow();
                toolbarOffset = 0;
            }
            visible = true;
        }

        private void setInvisible() {
            if (toolbarOffset < toolbarHeight) {
                onHide();
                toolbarOffset = toolbarHeight;
            }
            visible = false;
        }

        public abstract void onMoved(int distance);

        public abstract void onShow();

        public abstract void onHide();
    }

}
