package com.dicoding.moviefavoriteapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dicoding.moviefavoriteapp.adapter.MovieFavoriteAdapter;

import static android.provider.BaseColumns._ID;
import static com.dicoding.moviefavoriteapp.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener{
    private MovieFavoriteAdapter favoriteMovieAdapter;
    ListView lvFavorite;

    private final int LOAD_FAV_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Favorite Movie");
        lvFavorite = findViewById(R.id.lv_favorite);
        favoriteMovieAdapter = new MovieFavoriteAdapter(this, null, true);
        lvFavorite.setAdapter(favoriteMovieAdapter);
        lvFavorite.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(LOAD_FAV_ID, null, this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_FAV_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favoriteMovieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favoriteMovieAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_FAV_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor cursor = (Cursor) favoriteMovieAdapter.getItem(i);
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
        Intent intent = new Intent(MainActivity.this, FormActivity.class);
        intent.setData(Uri.parse(CONTENT_URI+"/"+id));
        startActivity(intent);
    }
}