package com.github.airk.democollection.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.github.airk.democollection.R;

/**
 * Created by kevin on 15/6/26.
 */
public class SViewListView extends SView {
    private ListView listView;

    public SViewListView(Context context) {
        super(context);
    }

    public SViewListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SViewListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        listView = (ListView) LayoutInflater.from(getContext()).inflate(R.layout.item_single_listview, this, false);
        addView(listView);
        super.init();
    }

    @Override
    protected boolean isReadyToPullFromStart() {
        return listView.getFirstVisiblePosition() == 0;
    }
}
