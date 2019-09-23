package com.dicoding.picodiploma.FinalGDK.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.picodiploma.FinalGDK.DetailActivity;
import com.dicoding.picodiploma.FinalGDK.LoadMoviesCallback;
import com.dicoding.picodiploma.FinalGDK.R;
import com.dicoding.picodiploma.FinalGDK.adapter.MovieAdapter;
import com.dicoding.picodiploma.FinalGDK.model.MainVIewModel;
import com.dicoding.picodiploma.FinalGDK.model.Movie;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements View.OnClickListener, LoadMoviesCallback {
    private MainVIewModel mainVIewModel;
    MovieAdapter movieAdapter = new MovieAdapter(getActivity());

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainVIewModel = ViewModelProviders.of(requireActivity()).get(MainVIewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment

        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("Movie Item", data);
                startActivity(intent);
            }
        });

        mainVIewModel.getFavorite().observe(requireActivity(), getFavorite);
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        Context context = getContext();

        RecyclerView rvMovie = root.findViewById(R.id.rv_favorite);

        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setAdapter(movieAdapter);
        rvMovie.setHasFixedSize(true);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainVIewModel.setFavorite("en", requireContext());
    }

    private Observer<ArrayList<Movie>> getFavorite = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> list) {
            if (list != null) {
                movieAdapter.setData(list);
            }
        }
    };

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(ArrayList<Display.Mode> movies) {

    }

    @Override
    public void onClick(View v) {

    }
}
