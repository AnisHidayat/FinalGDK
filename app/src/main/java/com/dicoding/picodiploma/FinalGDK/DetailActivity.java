package com.dicoding.picodiploma.FinalGDK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.picodiploma.FinalGDK.db.MovieHelper;
import com.dicoding.picodiploma.FinalGDK.model.Movie;
import com.dicoding.picodiploma.FinalGDK.model.MovieObject;

import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity  {

    private TextView tvAddRemove;
    private MovieHelper movieHelper;
    private MovieObject favMovieObject;

    public static String EXTRA_PHOTO = "EXTRA_PHOTO";
    public static String EXTRA_NAME = "EXTRA_NAME";
    public static String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static String EXTRA_DATE = "EXTRA_DATE";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvAddRemove = findViewById(R.id.tv_add_remove);

        Intent intent = getIntent();
        final Movie movie = intent.getParcelableExtra("Movie Item");

        final String image = movie.getPhoto();
        String textName = movie.getName();
        String textDate = movie.getDate();
        String textDesc = movie.getDesc();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(textName);
        }

        ImageView imageView = findViewById(R.id.img_photo2);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+image)
                .apply(new RequestOptions().override(350, 550))
                .into(imageView);

        TextView tvName = findViewById(R.id.txt_name2);
        tvName.setText(textName);

        TextView tvDate = findViewById(R.id.txt_date2);
        tvDate.setText(textDate);

        TextView tvDesc = findViewById(R.id.txt_description2);
        tvDesc.setText(textDesc);

        final ToggleButton toggleButton = findViewById(R.id.myFavButton);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();


        toggleButton.setTextOff("");
        toggleButton.setTextOn("");
        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_off));

            tvName.setText(movie.getName());
            tvDate.setText(movie.getDate());
            tvDesc.setText(movie.getDesc());

        Uri uri = getIntent().getData();
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()) favMovieObject = new MovieObject((JSONObject) cursor);
                cursor.close();
            }
        }

        boolean dataValue = movieHelper.movieCek(movie);
        toggleButton.setChecked(dataValue);
        if (dataValue) {
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_on));
            tvAddRemove.setText("Unfavorite");
        }else {
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_off));
            tvAddRemove.setText("Add to Favorite");
        }
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean on = toggleButton.isChecked();

                if (on) {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_on));
                    movieHelper.insertMovie(movie);
                    Toast.makeText(getApplicationContext(), R.string.toast_add, Toast.LENGTH_SHORT).show();
                    Log.d("tambah","tb is checked");
                    finish();
                }
                else{
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_off));
                    movieHelper.deleteMovie(movie.getId());
                    Toast.makeText(getApplicationContext(), R.string.toast_remove, Toast.LENGTH_SHORT).show();
                    Log.d("hapus","tb is unchacked");
                    finish();
                }
            }
        });
    }
}
