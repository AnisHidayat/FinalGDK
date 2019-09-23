package com.dicoding.picodiploma.FinalGDK.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.picodiploma.FinalGDK.model.Movie;
import com.dicoding.picodiploma.FinalGDK.R;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.GridViewHolder>{
    private ArrayList<Movie> listTv  = new ArrayList<>();
    public void setData(ArrayList<Movie> items){
        listTv.clear();
        listTv.addAll(items);
        notifyDataSetChanged();
    }

    private TvAdapter.OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(TvAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public TvAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid_tv, viewGroup, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GridViewHolder holder, int position) {
        Movie movie = listTv.get(position);
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500"+movie.getPhoto())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.imgPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listTv.get(holder.getAdapterPosition()));
            }
        });
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie data);
    }

    @Override
    public int getItemCount() {
        return listTv.size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;

        GridViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_tv_photo);
        }
    }
}
