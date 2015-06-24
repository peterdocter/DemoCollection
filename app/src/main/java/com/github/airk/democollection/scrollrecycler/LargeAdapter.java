package com.github.airk.democollection.scrollrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin on 15/3/10.
 */
public class LargeAdapter extends RecyclerView.Adapter<LargeAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText("" + (position + 1));
    }

    @Override
    public int getItemCount() {
        return 1000;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(android.R.id.text1)
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
