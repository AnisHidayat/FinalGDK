package com.dicoding.picodiploma.FinalGDK.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.dicoding.picodiploma.FinalGDK.db.MovieHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainVIewModel extends ViewModel {
    private static final String API_KEY = "YOUR API KEY";
    private MutableLiveData<ArrayList<Movie>> movie = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> tv = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> favorite = new MutableLiveData<>();

    public void setMovie(String language) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listMovie = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&language="+language+"&page=1";

        System.out.println("Call Requset API");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItems = new Movie(movie);
                        movieItems.setId(movie.getInt("id"));
                        movieItems.setPhoto(movie.getString("poster_path"));
                        movieItems.setName(movie.getString("title"));
                        movieItems.setDate(movie.getString("release_date"));
                        movieItems.setDesc(movie.getString("overview"));
                        movieItems.setBackdrop(movie.getString("backdrop_path"));
                        listMovie.add(movieItems);
                    }
                    movie.postValue(listMovie);
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("onFailure", error.getMessage());
            }

        });
    }

    public void setTv(String language) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listTv = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/top_rated?api_key="+API_KEY+"&language="+language+"&page=1";

        System.out.println("Call Requset API");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItems = new Movie(movie);
                        movieItems.setId(movie.getInt("id"));
                        movieItems.setPhoto(movie.getString("poster_path"));
                        movieItems.setName(movie.getString("name"));
                        movieItems.setDate(movie.getString("first_air_date"));
                        movieItems.setDesc(movie.getString("overview"));
                        movieItems.setBackdrop(movie.getString("backdrop_path"));
                        listTv.add(movieItems);
                    }
                    tv.postValue(listTv);
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("onFailure", error.getMessage());
            }

        });
    }

    public void setFavorite(String language, Context context) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listFavorite = new ArrayList<>();
        MovieHelper movieHelper = new MovieHelper(context);
        favorite.postValue(movieHelper.getAllMovie());
    }

    public LiveData<ArrayList<Movie>> getMovie() {
        return movie;
    }

    public LiveData<ArrayList<Movie>> getTv() {
        return tv;
    }

    public LiveData<ArrayList<Movie>> getFavorite() {
        return favorite;
    }

}
