package com.dicoding.moviefavoriteapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dicoding.moviefavoriteapp.model.FavoriteModel;

import static android.provider.MediaStore.MediaColumns.TITLE;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {
        EditText edtTitle, edtPopularity;
        Button btnSubmit;

    public static String EXTRA_FAV_ITEM = "extra_fav_item";
    private FavoriteModel favoriteModel = null;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        edtTitle = findViewById(R.id.edt_title);

        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        Uri uri = getIntent().getData();
        if (uri != null){
            Cursor cursor = getContentResolver().query(uri, null, null, null,null);
            if (cursor != null){
                if(cursor.moveToFirst()) favoriteModel = new FavoriteModel(cursor);
                cursor.close();
            }
        }

        String actionBarTitle = null;
        String btnActionTitle = null;
        if (favoriteModel != null){
            isUpdate = true;
            actionBarTitle = "Update";
            btnActionTitle = "Simpan";

            edtTitle.setText(favoriteModel.getTitle());

        }

        btnSubmit.setText(btnActionTitle);
        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit){
            String title = edtTitle.getText().toString().trim();
            String popularity = edtPopularity.getText().toString().trim();

            boolean isEmptyField = false;
            if (TextUtils.isEmpty(title)){
                isEmptyField = true;
                edtTitle.setError("Field tidak boleh kosong");
            }

            if(!isEmptyField){
                ContentValues contentValues = new ContentValues();
                contentValues.put(TITLE, title);

                if(isUpdate){
                    Uri uri = getIntent().getData();
                    getContentResolver().update(uri, contentValues, null,null);
                    Toast.makeText(this, "Satu Film berhasil di update", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isUpdate){
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }

        if (item.getItemId() == R.id.action_delete){
            showDeleteAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteAlertDialog(){
        String dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
        String dialogTitle = " Hapus Film";

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Uri uri = getIntent().getData();
                        getContentResolver().delete(uri, null, null);
                        Toast.makeText(FormActivity.this, "Satu item berhasil dihapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}
