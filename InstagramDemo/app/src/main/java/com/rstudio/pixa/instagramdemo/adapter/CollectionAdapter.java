package com.rstudio.pixa.instagramdemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SquaringDrawable;
import com.bumptech.glide.request.target.Target;
import com.rstudio.pixa.instagramdemo.R;
import com.rstudio.pixa.instagramdemo.model.InstaPost;

import java.util.ArrayList;

/**
 * Created by Ryan on 6/9/16.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionCell> {

    Context mContext;
    public ArrayList<InstaPost> data;
    LayoutInflater inf;

    public CollectionAdapter(Context context, ArrayList<InstaPost> data) {
        this.mContext = context;
        this.data = data;
        this.inf = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public CollectionCell onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inf.inflate(R.layout.collection_cell, null);
        return new CollectionCell(view);
    }

    @Override
    public void onBindViewHolder(final CollectionCell holder, int position) {
        InstaPost data = this.data.get(position);
        holder.tv.setText(data.caption.text);
        Glide.with(mContext).load(data.images.low_resolution.url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Bitmap bit = ((GlideBitmapDrawable) resource).getBitmap();
                        Palette palette = Palette.from(bit).generate();
                        int color = palette.getDarkVibrantColor(palette.getDarkMutedColor(Color.MAGENTA));
                        holder.view.setBackgroundColor(color);
                        return false;
                    }
                })
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CollectionCell extends RecyclerView.ViewHolder {
        public View view;
        public ImageView img;
        public TextView tv;
        public CollectionCell(View itemView) {
            super(itemView);
            view = itemView;
            img = ((ImageView) view.findViewById(R.id.cell_img));
            tv = ((TextView) view.findViewById(R.id.cell_cap));
        }
    }
}
