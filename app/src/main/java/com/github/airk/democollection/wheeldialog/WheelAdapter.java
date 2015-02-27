package com.github.airk.democollection.wheeldialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.airk.democollection.R;

/**
 * Created by kevin on 15/2/27.
 */
public class WheelAdapter extends BaseAdapter {
    private Context context;

    public WheelAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 100 + 2;
    }

    @Override
    public String getItem(int position) {
        return "" + position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == getCount() - 1) {
            return -1;
        }
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_wheel_layout, parent, false);
        TextView tv = (TextView) v.findViewById(R.id.item_tv);
        if (getItemViewType(position) == -1) {
            tv.setText("");
        } else {
            tv.setText(getItem(position));
        }
        return v;
    }
}
