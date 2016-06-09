package com.rstudio.pixa.retrofitdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rstudio.pixa.retrofitdemo.model.MovieModel;
import com.rstudio.pixa.retrofitdemo.R;
import com.rstudio.pixa.retrofitdemo.utils.Config;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Ryan on 6/8/16.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainRecyclerCell> {

    public Context mContext;
    public ArrayList<MovieModel> data;
    public LayoutInflater inf;

    public MainAdapter(Context context, JSONArray data) {
        this(context, MovieModel.getMovieData(data));
    }

    public MainAdapter(Context context, ArrayList<MovieModel> data) {
        this.mContext = context;
        this.data = data;
        inf = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public MainRecyclerCell onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inf.inflate(R.layout.main_cell, null);
        MainRecyclerCell cell = new MainRecyclerCell(view);
        return cell;
    }

    @Override
    public void onBindViewHolder(MainRecyclerCell holder, int position) {
        MovieModel cell = this.data.get(position);
        holder.title.setText(cell.original_title);
        holder.overview.setText(cell.overview);
        Glide.with(mContext).load(Config.BASE_MEDIA_URL + cell.poster_path).into(holder.thumb);
    }

    public void setData(JSONArray array) {
        ArrayList<MovieModel> data = MovieModel.getMovieData(array);
        setData(data);
    }

    public void setData(ArrayList<MovieModel> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MainRecyclerCell extends RecyclerView.ViewHolder {
        public View view;
        public ImageView thumb;
        public TextView title, overview;
        public MainRecyclerCell(View itemView) {
            super(itemView);
            this.view = itemView;
            thumb = (ImageView) view.findViewById(R.id.cell_thumb);
            title = (TextView) view.findViewById(R.id.cell_title);
            overview = ((TextView) view.findViewById(R.id.cell_overview));
        }
    }
}
