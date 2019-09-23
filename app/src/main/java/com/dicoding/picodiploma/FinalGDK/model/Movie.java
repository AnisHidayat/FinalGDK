package com.dicoding.picodiploma.FinalGDK.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movie implements Parcelable {
    private int id;
    private String name;
    private String date;
    private String desc;
    private String photo;
    private String backdrop;

    public Movie(JSONObject movie) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getBackdrop() {
        return backdrop;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.date);
        dest.writeString(this.desc);
        dest.writeString(this.photo);
        dest.writeString(this.backdrop);
    }
//*****
    public Movie(int movieId, String movieName, String photo, String back, String date,  String desc) {
        id = movieId;
        name = movieName;
        photo = photo;
        backdrop = back;
        date = date;
        desc = desc;
    }

    public Movie(){

    }
//*****
public Movie(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.date = in.readString();
        this.desc = in.readString();
        this.photo = in.readString();
        this.backdrop = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
