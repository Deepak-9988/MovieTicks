package com.example.movieticks;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesPassesList extends ArrayAdapter<MoviePass> {

    private Activity context;
    private List<MoviePass> moviePassList;

    public MoviesPassesList(Activity context, List<MoviePass> moviePassList){
        super(context,R.layout.movie_pass_list_layout,moviePassList);
        this.context=context;
        this.moviePassList=moviePassList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView tvMName,tvMLanguage,tvMCast,tvMCertificate;
        TextView tv_theatreName,tv_theatreAddress,tv_tickets_details,tv_edit_icon,tv_date_info_summary,tv_price_info_summary;
        TextView tv_seats_selected;
        ImageView movieImage;

        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.movie_pass_list_layout,null,true);

        tvMName=listViewItem.findViewById(R.id.Mname);
        tvMLanguage=listViewItem.findViewById(R.id.mlanguage);
        tvMCast=listViewItem.findViewById(R.id.Mcast);
        tvMCertificate=listViewItem.findViewById(R.id.mCertificate);
        movieImage=listViewItem.findViewById(R.id.MovieImg);
        tv_date_info_summary=listViewItem.findViewById(R.id.tv_date_info_summary);
        tv_theatreName=listViewItem.findViewById(R.id.tv_theatreName);
        tv_theatreAddress=listViewItem.findViewById(R.id.tv_theatreAddress);
        tv_tickets_details=listViewItem.findViewById(R.id.tv_tickets_details);
        tv_seats_selected=listViewItem.findViewById(R.id.tv_seats_selected);
        tv_seats_selected.setMovementMethod(new ScrollingMovementMethod());
        tv_price_info_summary=listViewItem.findViewById(R.id.tv_price_info_summary);

        MoviePass moviePass=moviePassList.get(position);
        MovieInfo movieInfo;
        movieInfo=moviePass.getMovieInfo();
        movieInfo = moviePass.getMovieInfo();
        tvMName.setText(movieInfo.getMovieName());
        tvMLanguage.setText(movieInfo.getmLanguage());
        tvMCertificate.setText(movieInfo.getmCertificate());
        Picasso.with(context).load(movieInfo.getMovieImg()).into(movieImage);
        tvMCast.setText(movieInfo.getmCast());

        tv_date_info_summary.setText(moviePass.getTimeAndDate());
        tv_theatreName.setText(moviePass.getTheatreName());
        tv_theatreAddress.setText(moviePass.getTheatreAddress());
        tv_tickets_details.setText(moviePass.getTicketDetails());
        tv_seats_selected.setText(moviePass.getSeatsSelected());
        tv_price_info_summary.setText(String.valueOf(moviePass.getTtlPrice()));




        return listViewItem;
    }
}
