package com.github.airk.democollection.scrollview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.airk.democollection.R;
import com.github.airk.democollection.ui.BaseActivity;

/**
 * Created by kevin on 15/6/24.
 */
public class ScrollCustomViewActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sview);
        final SViewListView sViewListView = (SViewListView) findViewById(R.id.pull_list);
        sViewListView.setOnRefreshListener(new SView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sViewListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sViewListView.onRefreshComplete();
                    }
                }, 1500);
            }
        });
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new SimpleAdapter());
    }

    private class SimpleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(ScrollCustomViewActivity.this);
            tv.setText("" + (position + 1));
            tv.setPadding(12, 8, 12, 8);
            return tv;
        }
    }
}
