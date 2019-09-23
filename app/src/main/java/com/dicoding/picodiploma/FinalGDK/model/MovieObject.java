package com.dicoding.picodiploma.FinalGDK.model;

import org.json.JSONObject;

public class MovieObject {
    private String id;
    private String photo;
    private String name;
    private String date;
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public MovieObject(JSONObject object){
        try {
            String id = object.getString("id");
            String photo = object.getString("photo");
            String name = object.getString("name");
            String date = object.getString("date");
            String desc = object.getString("desc");

            this.id = id;
            this.photo = "http://image.tmdb.org/t/p/w185"+photo;
            this.name = name;
            this.date = date;
            this.desc = desc;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
