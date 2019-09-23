package com.dicoding.picodiploma.FinalGDK;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.dicoding.picodiploma.FinalGDK.model.Movie;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private ArrayList<Movie> mData;
    private boolean mHasResult = false;
    private String mKumpulanFilm;
    public static final String API_URL = "d58453864d86d84d04f9c5974c45c4b1";

    public SearchAsyncTaskLoader (final Context context, String kumpulanFilm){
        super(context);
        onContentChanged();
        this.mKumpulanFilm = kumpulanFilm;
    }

    @Override
    protected void onStartLoading(){
        if(takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList <Movie> data){
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset(){
        super.onReset();
        onStopLoading();
        if(mHasResult){
            onReleaseResource(mData);
            mData = null;
            mHasResult = false;
        }
    }

    private static final String API_KEY = API_URL;
    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Movie> movieItemses = new ArrayList<>();
        String url ="https://api.themoviedb.org/3/search/movie?api_key="+ API_KEY +"&language=en-US&query="+ mKumpulanFilm;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart(){
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("baca url", "onSuccess: Yes");
                try{
                    String result = new String (responseBody);
                    JSONObject responeObject = new JSONObject(result);
                    JSONArray list = responeObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItems = new Movie();
                        movieItemses.add(movieItems);
                        Log.d("LIST", "onSuccess" + movieItems.getPhoto());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieItemses;
    }

    protected void onReleaseResource(ArrayList<Movie> data){
        //nothing to do
    }
}
