package com.github.airk.democollection.palette;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.github.airk.democollection.R;
import com.github.airk.democollection.ui.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin on 15/1/15.
 */
public class PaletteColorActivity extends BaseActivity {
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.recycler_view)
    RecyclerView mRecycler;

    PaletteColorAdapter mAdapter;
    int padding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette_color);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        padding = getResources().getDimensionPixelSize(R.dimen.palette_item_color_padding);
        final String img = getIntent().getStringExtra("data");

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 4;
                }
                return 1;
            }
        });
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left += padding;
                outRect.right += padding;
                outRect.top += padding;
                outRect.bottom += padding;
            }
        });
        mAdapter = new PaletteColorAdapter(this);
        mRecycler.setAdapter(mAdapter);

        Glide.with(this).load(img).asBitmap().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                Palette.generateAsync(resource, new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        mAdapter.setData(img, palette.getSwatches());
                    }
                });
                return false;
            }
        }).into(image);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
