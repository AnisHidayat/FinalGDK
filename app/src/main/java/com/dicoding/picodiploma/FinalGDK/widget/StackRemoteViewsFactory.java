package com.dicoding.picodiploma.FinalGDK.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.FinalGDK.R;
import com.dicoding.picodiploma.FinalGDK.db.MovieHelper;
import com.dicoding.picodiploma.FinalGDK.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> widgeItems = new ArrayList<>();
    private final Context mContext;

    public StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        MovieHelper movieHelper = new MovieHelper(mContext);
        ArrayList<Movie> favMovie = movieHelper.getAllMovie();
        for (int i=0; i < favMovie.size(); i++) {
            String url ="https://image.tmdb.org/t/p/w780"+ favMovie.get(i).getPhoto();
            try {
                Bitmap gambar = Glide.with(mContext).asBitmap().load(url).submit().get();
                widgeItems.add(gambar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgeItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.widgetImgView, widgeItems.get(position));

        Bundle bundle = new Bundle();
        bundle.putInt(Widget.EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtras(intent);

        rv.setOnClickFillInIntent(R.id.widgetImgView, intent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
