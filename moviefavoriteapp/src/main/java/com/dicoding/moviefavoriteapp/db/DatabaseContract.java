package com.dicoding.moviefavoriteapp.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_NAME = "tb_favorite";
    public static final class MovieColumns implements BaseColumns {
        //static final String TABLE_NAME = "tb_favorite";

        static final String PHOTO = "photo";

        static final String TITLE = "title";

        static final String DATE = "date";

        static final String DESCRIPTION = "description";

        static final String BACKDROP = "backdrop_path";


    }
    public static final String AUTHORITY = "com.dicoding.picodiploma.FinalGDK";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}

