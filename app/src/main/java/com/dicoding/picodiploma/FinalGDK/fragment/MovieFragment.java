package com.dicoding.picodiploma.FinalGDK.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.picodiploma.FinalGDK.DetailActivity;
import com.dicoding.picodiploma.FinalGDK.R;
import com.dicoding.picodiploma.FinalGDK.adapter.MovieAdapter;
import com.dicoding.picodiploma.FinalGDK.model.MainVIewModel;
import com.dicoding.picodiploma.FinalGDK.model.Movie;

import java.util.ArrayList;

public class MovieFragment extends Fragment { private MainVIewModel mainVIewModel;

    MovieAdapter movieAdapter = new MovieAdapter(getActivity());
    ProgressBar progressBar;
    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainVIewModel = ViewModelProviders.of(requireActivity()).get(MainVIewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_movie, container, false);
        RecyclerView rvMovie = root.findViewById(R.id.rv_movie);
        progressBar = root.findViewById(R.id.progressBar);
        showLoading(true);

        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("Movie Item", data);
                startActivity(intent);
            }
        });

        mainVIewModel.getMovie().observe(requireActivity(), getMovie);
        return root;
    }
    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> list) {
            if (list != null) {
                movieAdapter.setData(list);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
