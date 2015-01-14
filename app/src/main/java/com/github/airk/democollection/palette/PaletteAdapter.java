package com.github.airk.democollection.palette;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.airk.democollection.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Getter;

/**
 * Created by kevin on 15/1/14.
 */
public class PaletteAdapter extends RecyclerView.Adapter<PaletteAdapter.ViewHolder> {
    private Context context;
    private int size;
    private ArrayList<String> data;

    public PaletteAdapter(Context context) {
        this.context = context;
        size = context.getResources().getDisplayMetrics().widthPixels / 2 - 4 * context.getResources().getDimensionPixelSize(R.dimen.palette_item_padding);
        data = new ArrayList<>();
    }

    public void setData(String[] urls) {
        if (urls != null) {
            data = new ArrayList<>(Arrays.asList(urls));
        } else {
            data.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_palette_img, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Getter
    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.card_container)
        View card;
        @InjectView(R.id.image)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void bindData(final String url) {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>(size, size) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            getImage().setImageBitmap(resource);
                        }
                    });
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PaletteColorActivity.class);
                    intent.putExtra("data", url);
                    context.startActivity(intent);
                }
            });
        }
    }
}
