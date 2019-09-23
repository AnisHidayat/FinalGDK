package com.dicoding.moviefavoriteapp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.MediaColumns.TITLE;
import static com.dicoding.moviefavoriteapp.db.DatabaseContract.getColumnInt;
import static com.dicoding.moviefavoriteapp.db.DatabaseContract.getColumnString;

public class FavoriteModel {
    private int id;
    private String title, popularity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }


    public int describeContents() {
        return 0;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
    }

    public FavoriteModel() {

    }

    public FavoriteModel(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, TITLE);
    }

    protected FavoriteModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.popularity = in.readString();
    }

    public static final Parcelable.Creator<FavoriteModel> CREATOR = new Parcelable.Creator<FavoriteModel>() {
        @Override
        public FavoriteModel createFromParcel(Parcel source) {
            return new FavoriteModel(source);
        }

        @Override
        public FavoriteModel[] newArray(int size) {
            return new FavoriteModel[size];
        }
    };
}
