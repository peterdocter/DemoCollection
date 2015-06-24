package com.github.airk.democollection.listinflate;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.airk.democollection.R;
import com.github.airk.democollection.ui.BaseActivity;

/**
 * Created by kevin on 15/3/9.
 */
public class InflateNullActivity extends BaseActivity {

    private InflateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = new ListView(this);
        setContentView(listView);
        adapter = new InflateAdapter(this);
        listView.setAdapter(adapter);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                Log.d("Animation", "" + interpolatedTime);
                if (interpolatedTime == 0f) {
                    Log.e("Animation", "start");
                } else if (interpolatedTime == 1f) {
                    Log.e("Animation", "end");
                }
            }
        };
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setDuration(30 * 1000);
        listView.startAnimation(animation);
    }

    public enum Changer {
        ONE {
            @Override
            public void doChange(View v) {

            }

            @Override
            public void getContext(View v) {

            }
        },

        TWO {
            @Override
            public void doChange(View v) {

            }

            @Override
            public void getContext(View v) {

            }
        };

        public abstract void doChange(View v);
        public abstract void getContext(View v);
    }

    private static class InflateAdapter extends BaseAdapter {
        Context ctx;

        private InflateAdapter(Context ctx) {
            this.ctx = ctx;
        }

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
            return position + 1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(ctx).inflate(R.layout.item_text_parent_layout, parent, false);
            TextView tv = (TextView) v.findViewById(android.R.id.text1);
            tv.setText(getItemId(position) + "");
            return v;
        }
    }
}
