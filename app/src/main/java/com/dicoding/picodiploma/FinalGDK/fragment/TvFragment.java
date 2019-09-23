package com.dicoding.picodiploma.FinalGDK.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.picodiploma.FinalGDK.DetailActivity;
import com.dicoding.picodiploma.FinalGDK.R;
import com.dicoding.picodiploma.FinalGDK.adapter.TvAdapter;
import com.dicoding.picodiploma.FinalGDK.model.MainVIewModel;
import com.dicoding.picodiploma.FinalGDK.model.Movie;

import java.util.ArrayList;

public class TvFragment extends Fragment {    private MainVIewModel mainVIewModel;

    TvAdapter adapter = new TvAdapter();
    ProgressBar progressBar;

    public TvFragment() {
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

        View root = inflater.inflate(R.layout.fragment_tv, container, false);
        RecyclerView rvTv = root.findViewById(R.id.rv_tv);
        progressBar = root.findViewById(R.id.progressBar);

        showLoading(true);

        rvTv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvTv.setAdapter(adapter);

        adapter.setOnItemClickCallback(new TvAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("Movie Item", data);
                startActivity(intent);
            }
        });

        mainVIewModel.getTv().observe(requireActivity(), getTv);
        return root;
    }
    private Observer<ArrayList<Movie>> getTv = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> list) {
            if (list != null) {
                adapter.setData(list);
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
