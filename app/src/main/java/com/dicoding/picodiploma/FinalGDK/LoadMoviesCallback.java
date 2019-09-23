package com.dicoding.picodiploma.FinalGDK;

import android.view.Display;

import java.util.ArrayList;

public interface LoadMoviesCallback {
    void preExecute();

    void postExecute(ArrayList<Display.Mode> movies);
}


