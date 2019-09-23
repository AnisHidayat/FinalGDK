package com.dicoding.moviefavoriteapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.dicoding.moviefavoriteapp.R;

import static android.provider.MediaStore.MediaColumns.TITLE;
import static com.dicoding.moviefavoriteapp.db.DatabaseContract.getColumnString;

public class MovieFavoriteAdapter extends CursorAdapter {
    public MovieFavoriteAdapter (Context context, Cursor c, boolean autoRequery){
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite_movie, viewGroup, false);
        return view;
    }

    @Override
    public Cursor getCursor(){
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            TextView tvName = view.findViewById(R.id.tv_name);
            tvName.setText(getColumnString(cursor, TITLE));

        }
    }
}

