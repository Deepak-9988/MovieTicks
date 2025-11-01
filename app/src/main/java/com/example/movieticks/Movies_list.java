package com.example.movieticks;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Movies_list extends ArrayAdapter<MovieInfo> {
    private Activity context;
    private List<MovieInfo> movieslist;



    public Movies_list(Activity context, List<MovieInfo> movieslist) {
        super(context, R.layout.list_layout, movieslist);
        this.context = context;
        this.movieslist = movieslist;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView textViewMName = listViewItem.findViewById(R.id.Mname);
        TextView textViewMLanguage = listViewItem.findViewById(R.id.mlanguage);
        TextView textViewMcast=listViewItem.findViewById(R.id.Mcast);
        ImageView movieImg = listViewItem.findViewById(R.id.MovieImg);
        TextView textViewCertificate=listViewItem.findViewById(R.id.mCertificate);
        MovieInfo movieInfo = movieslist.get(position);

        textViewMName.setText(movieInfo.getMovieName());
        textViewMLanguage.setText(movieInfo.getmLanguage());
        textViewCertificate.setText(movieInfo.getmCertificate());
        Picasso.with(context).load(movieInfo.getMovieImg()).into(movieImg);
        textViewMcast.setText(movieInfo.getmCast());

        return listViewItem;

    }
}