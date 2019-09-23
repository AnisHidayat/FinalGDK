package com.dicoding.picodiploma.FinalGDK;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dicoding.picodiploma.FinalGDK.fragment.FavoriteFragment;
import com.dicoding.picodiploma.FinalGDK.fragment.MovieFragment;
import com.dicoding.picodiploma.FinalGDK.fragment.SearchFragment;
import com.dicoding.picodiploma.FinalGDK.fragment.TvFragment;
import com.dicoding.picodiploma.FinalGDK.model.MainVIewModel;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigation;
    Fragment fragment = new MovieFragment();
    MainVIewModel mainVIewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_frame, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;

                case R.id.navigation_tv:
                    fragment = new TvFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_frame, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;

                case R.id.navigation_favorite:
                    fragment = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_frame, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;

                case R.id.navigation_search:
                    fragment = new SearchFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_frame, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null){
            getSupportFragmentManager().putFragment(outState, "page", fragment);
            outState.putInt("selectedItem", navigation.getSelectedItemId());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null){
            navigation.setSelectedItemId(navigation.getMenu().getItem(0).getItemId());
        }else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "page");
            navigation.setSelectedItemId(savedInstanceState.getInt("selectedItem"));
        }

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.container_frame, fragment, null)
                .commit();

        mainVIewModel = ViewModelProviders.of(this).get(MainVIewModel.class);
        mainVIewModel.setMovie(getString(R.string.language));
        mainVIewModel.setTv(getString(R.string.language));
        mainVIewModel.setFavorite(getString(R.string.language), this);
    }
}
