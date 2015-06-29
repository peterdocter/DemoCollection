package com.github.airk.democollection.palette;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.github.airk.democollection.R;
import com.github.airk.democollection.model.FakeImageData;
import com.github.airk.democollection.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin on 15/1/14.
 */
public class PaletteActivity extends BaseActivity {
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    int itemPadding;
    PaletteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.inject(this);

        itemPadding = getResources().getDimensionPixelSize(R.dimen.palette_item_padding);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left += itemPadding;
                outRect.right += itemPadding;
                outRect.top += itemPadding;
                outRect.bottom += itemPadding;
            }
        });
        mAdapter = new PaletteAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setData(FakeImageData.getAllImages());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
