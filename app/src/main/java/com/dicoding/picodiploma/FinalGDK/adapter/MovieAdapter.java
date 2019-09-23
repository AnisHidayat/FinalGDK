package com.dicoding.picodiploma.FinalGDK.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.FinalGDK.R;
import com.dicoding.picodiploma.FinalGDK.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ListViewHolder> {
    private ArrayList<Movie> listMovie = new ArrayList<>();
    public MovieAdapter(FragmentActivity activity){

    }
    public void setData(ArrayList<Movie> items){
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }


    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MovieAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_movie_item, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.ListViewHolder holder, int position) {
        Movie movie = listMovie.get(position);
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500"+movie.getPhoto())
                .into(holder.imgPhoto);
        holder.tvName.setText(movie.getName());
        holder.tvDate.setText(movie.getDate());
        holder.tvDesc.setText(movie.getDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listMovie.get(holder.getAdapterPosition()));
            }
        });
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie data);
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvName, tvDate, tvDesc;
        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_movie_photo);
            tvName = itemView.findViewById(R.id.txt_name);
            tvDate = itemView.findViewById(R.id.txt_date);
            tvDesc = itemView.findViewById(R.id.txt_description);
        }
    }
}
