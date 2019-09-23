package com.dicoding.picodiploma.FinalGDK.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.FinalGDK.R;
import com.dicoding.picodiploma.FinalGDK.model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchAdapter extends BaseAdapter {
    private ArrayList<Movie> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public SearchAdapter(Context context){
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<Movie> items){
        mData = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    @Override
    public Movie getItem(int position){
        return mData.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.row_movie_item, null);
            holder.imageView = convertView.findViewById(R.id.img_movie_photo);
            holder.txtName = convertView.findViewById(R.id.txt_name);
            holder.txtDate = convertView.findViewById(R.id.txt_date);
            holder.txtDesc = convertView.findViewById(R.id.txt_description);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(mData.get(position).getPhoto()).into(holder.imageView);
        holder.txtName.setText(mData.get(position).getName());
        String re_date = mData.get(position).getDate();
        holder.txtDesc.setText(mData.get(position).getDesc());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try{
            Date date = dateFormat.parse(re_date);
            android.icu.text.SimpleDateFormat newDateFormat = new android.icu.text.SimpleDateFormat("EEEE, dd MMMM yyyy");
            String release_date = newDateFormat.format(date);
            holder.txtDate.setText(release_date);
        }catch (ParseException e){
            e.printStackTrace();
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView txtName;
        TextView txtDate;
        TextView txtDesc;
    }
}
