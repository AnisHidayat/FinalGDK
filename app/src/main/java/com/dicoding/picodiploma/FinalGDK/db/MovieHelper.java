package com.dicoding.picodiploma.FinalGDK.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dicoding.picodiploma.FinalGDK.model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.picodiploma.FinalGDK.db.DatabaseContract.MovieColumns.BACKDROP;
import static com.dicoding.picodiploma.FinalGDK.db.DatabaseContract.MovieColumns.DATE;
import static com.dicoding.picodiploma.FinalGDK.db.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.dicoding.picodiploma.FinalGDK.db.DatabaseContract.MovieColumns.PHOTO;
import static com.dicoding.picodiploma.FinalGDK.db.DatabaseContract.MovieColumns.TITLE;
import static com.dicoding.picodiploma.FinalGDK.db.DatabaseContract.TABLE_NAME;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    public MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    private void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getAllMovie() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        open();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));
                movie.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                movie.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setBackdrop(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        close();
        return arrayList;
    }

    public void insertMovie(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getId());
        args.put(PHOTO, movie.getPhoto());
        args.put(TITLE, movie.getName());
        args.put(DATE, movie.getDate());
        args.put(DESCRIPTION, movie.getDesc());
        args.put(BACKDROP, movie.getBackdrop());

        database.insert(DATABASE_TABLE, null, args);
    }

    public boolean movieCek (Movie movie){
        ContentValues args = new ContentValues();
        Cursor result =  database.query(DATABASE_TABLE, null, _ID + "= " + movie.getId(), null, null, null, null, null);
        boolean ada = result.getCount() > 0;
        result.close();
        return ada;
    }

    public void deleteMovie(int id) {
        database.delete(TABLE_NAME, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE
                ,null
                , _ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }

    public long insertProvider (ContentValues values){
        return  database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider (String id, ContentValues values){
        return database.update(DATABASE_TABLE, values, _ID +" = ?", new String[]{id});
    }

    public int deleteProvider (String id){
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}



