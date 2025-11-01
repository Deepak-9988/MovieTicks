package com.example.movieticks;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Theatres_list extends ArrayAdapter<TheatreInfo> {
    private Activity context;
    private List<TheatreInfo> theatreList;

    public Theatres_list(Activity context, List<TheatreInfo> theatresList){
        super(context,R.layout.theatres_list_layout,theatresList);
        this.context=context;
        this.theatreList=theatresList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.theatres_list_layout,null,true);
        TextView textViewTName=listViewItem.findViewById(R.id.tv_theatreName);
        TextView textViewTAddress=listViewItem.findViewById(R.id.tv_theatreAddress);
        TheatreInfo theatreInfo=theatreList.get(position);
        textViewTName.setText(theatreInfo.getTheatreName());
        textViewTAddress.setText(theatreInfo.getTheatreAddress());
        return listViewItem;


    }
}

