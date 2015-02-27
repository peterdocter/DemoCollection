package com.github.airk.democollection.palette;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.airk.democollection.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.Getter;

/**
 * Created by kevin on 15/1/15.
 */
public class PaletteColorAdapter extends RecyclerView.Adapter<PaletteColorAdapter.BaseHolder> {
    private Context context;
    private ArrayList<ColorData> data;
    private String topUrl;

    @Getter
    private class ColorData {
        int color;
        String name;

        private ColorData(String name, int color) {
            this.name = name;
            this.color = color;
        }
    }

    private final int TYPE_TOP = 1;
    private final int TYPE_COLOR = 2;

    public PaletteColorAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(String url, Palette palette) {
        topUrl = url;
        if (palette != null) {
            data.add(new ColorData("Vibrant", palette.getVibrantSwatch().getRgb()));
            data.add(new ColorData("Vibrant Dark", palette.getDarkVibrantSwatch().getRgb()));
            data.add(new ColorData("Vibrant Light", palette.getLightVibrantSwatch().getRgb()));
            data.add(new ColorData("Muted", palette.getMutedSwatch().getRgb()));
            data.add(new ColorData("Muted Dark", palette.getDarkMutedSwatch().getRgb()));
            data.add(new ColorData("Muted Light", palette.getLightMutedSwatch().getRgb()));
        } else {
            data.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        } else {
            return TYPE_COLOR;
        }
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_COLOR) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_palette_color_layout, parent, false);
            return new ColorHolder(view);
        } else if (viewType == TYPE_TOP) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_palette_img, parent, false);
            return new TopHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        if (getItemViewType(position) == TYPE_TOP) {
            ImageView image = ((TopHolder) holder).image;
            Glide.with(context).load(topUrl).asBitmap().into(image);
        } else {
            ColorHolder colorHolder = (ColorHolder) holder;
            colorHolder.bindData(data.get(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1/*TOP*/;
    }
    public class BaseHolder extends RecyclerView.ViewHolder {

        public BaseHolder(View itemView) {
            super(itemView);
        }
    }

    public class TopHolder extends BaseHolder {
        @InjectView(R.id.image)
        ImageView image;

        public TopHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public class ColorHolder extends BaseHolder {
        @InjectView(R.id.color_img)
        ImageView image;
        @InjectView(R.id.color_name)
        TextView name;

        public ColorHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void bindData(ColorData data) {
            ColorDrawable drawable = new ColorDrawable(data.getColor());
            image.setImageDrawable(drawable);
            name.setText(data.getName());
        }
    }
}
