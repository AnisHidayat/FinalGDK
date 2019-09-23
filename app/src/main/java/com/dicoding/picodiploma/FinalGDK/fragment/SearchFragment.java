package com.dicoding.picodiploma.FinalGDK.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dicoding.picodiploma.FinalGDK.DetailActivity;
import com.dicoding.picodiploma.FinalGDK.R;
import com.dicoding.picodiploma.FinalGDK.SearchAsyncTaskLoader;
import com.dicoding.picodiploma.FinalGDK.adapter.MovieAdapter;
import com.dicoding.picodiploma.FinalGDK.adapter.SearchAdapter;
import com.dicoding.picodiploma.FinalGDK.model.Movie;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";
    ListView listView;
    SearchAdapter adapter;
    EditText edtMovie;
    Button btnCari;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        adapter = new SearchAdapter(getActivity());
        adapter.notifyDataSetChanged();

        listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movieItems = (Movie)parent.getItemAtPosition(position);

                Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                detailIntent.putExtra(DetailActivity.EXTRA_PHOTO, movieItems.getPhoto());
                detailIntent.putExtra(DetailActivity.EXTRA_NAME, movieItems.getName());
                detailIntent.putExtra(DetailActivity.EXTRA_DESCRIPTION, movieItems.getDesc());
                detailIntent.putExtra(DetailActivity.EXTRA_DATE, movieItems.getDate());

                startActivity(detailIntent);
            }
        });

        edtMovie = view.findViewById(R.id.edt_movie);
        btnCari = view.findViewById(R.id.btn_search);
        btnCari.setOnClickListener(myListener);

        String movie = edtMovie.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, movie);

//        getLoaderManager().initLoader(0, bundle, (LoaderManager.LoaderCallbacks<Object>) this);
        return view;
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        String kumpulanMovie = "";
        if (args != null){
            kumpulanMovie = args.getString(EXTRAS_MOVIE);
        }
        return new SearchAsyncTaskLoader(getActivity(), kumpulanMovie);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener myListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String movie = edtMovie.getText().toString();

            if (TextUtils.isEmpty(movie))return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, movie);
            getLoaderManager().restartLoader(0,bundle, SearchFragment.this);
        }
    };
}
