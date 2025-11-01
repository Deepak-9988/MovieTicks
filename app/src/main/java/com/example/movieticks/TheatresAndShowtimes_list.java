package com.example.movieticks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheatresAndShowtimes_list extends ArrayAdapter<Theatre_and_showtimes> {
    private Activity context;
    String mId;
    private List<Theatre_and_showtimes> theatre_and_showtimesList;

    View previousView;
    String totalSeats;

    int ttl_no_of_seats=0;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public TheatresAndShowtimes_list(Activity context, ArrayList<Theatre_and_showtimes> theatre_and_showtimesList) {
        super(context,R.layout.theatres_and_showtimes_list_layout,theatre_and_showtimesList);
        this.context=context;
        this.theatre_and_showtimesList=theatre_and_showtimesList;

    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.theatres_and_showtimes_list_layout,null,true);
        TextView textViewTName=listViewItem.findViewById(R.id.tv_theatreName);
        TextView textViewTAddress=listViewItem.findViewById(R.id.tv_theatreAddress);
        Theatre_and_showtimes theatre_and_showtimes=theatre_and_showtimesList.get(position);

        final LinearLayout ll_showTimes=listViewItem.findViewById(R.id.ll_showTimes);

        for(int i=0;i<theatre_and_showtimesList.get(position).showTimesList.size();i++){
            final TextView tv_showTime=new TextView(getContext());
            tv_showTime.setText(theatre_and_showtimesList.get(position).showTimesList.get(i));

            tv_showTime.setBackgroundResource(R.drawable.tv_border);
            final int finalI = i;
            tv_showTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    @SuppressLint("ViewHolder")
                    View myLayout = LayoutInflater.from(getContext()).inflate(R.layout.dialog_how_many_seats, null);

                    LinearLayout ll_number_of_seats=myLayout.findViewById(R.id.ll_number_of_seats);
                    final ImageView vehicle_imageView=myLayout.findViewById(R.id.vehicle_imageView);
                    final Button btn_selectSeats=myLayout.findViewById(R.id.btn_selectSeats);




                        View.OnClickListener clickListener = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView previousText = (TextView) previousView;
                                TextView curText = (TextView) v;
                                ttl_no_of_seats=Integer.parseInt(curText.getText().toString());
                                // If the clicked view is selected, deselect it
                                if (curText.isSelected()) {
                                    curText.setSelected(false);
                                    curText.setTextColor(Color.RED);
                                    curText.setClickable(false);

                                    //Toast.makeText(getContext(),curText.getText().toString()+" is clicked",Toast.LENGTH_SHORT).show();


                                } else { // If this isn't selected, deselect  the previous one (if any)
                                    if (previousText != null && previousText.isSelected()) {
                                        previousText.setSelected(false);
                                        previousText.setClickable(true);
                                        previousText.setTextColor(Color.RED);
                                        //Toast.makeText(getContext(),curText.getText().toString()+" is clicked",Toast.LENGTH_SHORT).show();

                                    }
                                    curText.setSelected(true);
                                    curText.setTextColor(Color.BLUE);


                                    int ttlSeats=Integer.parseInt(curText.getText().toString());
                                    if(ttlSeats==1||ttlSeats==2)
                                        vehicle_imageView.setBackgroundResource(R.drawable.scooter_1_2);
                                    else if(ttlSeats==3||ttlSeats==4)
                                        vehicle_imageView.setBackgroundResource(R.drawable.car_for_3_4);
                                    else if(ttlSeats==5||ttlSeats==6)
                                        vehicle_imageView.setBackgroundResource(R.drawable.car_for_56);
                                    else if(ttlSeats==7||ttlSeats==8)
                                        vehicle_imageView.setBackgroundResource(R.drawable.van_78);
                                    else if(ttlSeats==9||ttlSeats==10)
                                        vehicle_imageView.setBackgroundResource(R.drawable.bus_10);

                                    btn_selectSeats.setText("Select "+String.valueOf(ttlSeats)+" Seats");

                                  //  Toast.makeText(getContext(),curText.getText().toString()+" is clicked",Toast.LENGTH_SHORT).show();
                                    totalSeats= curText.getText().toString();
                                    curText.setClickable(false);
                                    previousView = v;
                                }

                            }
                        };


                    ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                        for(int i=1;i<11;i++)
                        {
                            TextView tvTemp=new TextView(getContext());
                            tvTemp.setText(String.valueOf(i));
                            tvTemp.setTextColor(Color.RED);
                            tvTemp.setLayoutParams(params);
                            tvTemp.setOnClickListener(clickListener);

                            ll_number_of_seats.addView(tvTemp);
                            if(i==2)
                                tvTemp.performClick();
                        }





                    AlertDialog.Builder builder_how_many_seats = new AlertDialog.Builder(getContext());

                    builder_how_many_seats.setView(myLayout);

                    final AlertDialog dialog_how_many_seats = builder_how_many_seats.create();
                    dialog_how_many_seats.setCanceledOnTouchOutside(false);
                    dialog_how_many_seats.show();
                    btn_selectSeats.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(getContext(),"select Seats Btn click",Toast.LENGTH_SHORT).show();
                            if(ttl_no_of_seats!=0) {
                            Intent i = new Intent(getContext(), ShowTimeAndSeatsSelectionLayout.class);
                       ///     ShowTimeAndSeatsSelectionLayout.sendMInfo(movieInfo);//Sending Movie info
                            ShowTimeAndSeatsSelectionLayout.sendTInfo(theatre_and_showtimesList.get(position));//Sending Theatre info
                            i.putStringArrayListExtra("showTimes",theatre_and_showtimesList.get(position).getShowTimesList());
                            i.putExtra("selectedShowtime",tv_showTime.getText().toString());
                            i.putExtra("day",theatre_and_showtimesList.get(position).getDay());
                            i.putExtra("date",theatre_and_showtimesList.get(position).getDate());
                            i.putExtra("month",theatre_and_showtimesList.get(position).getMonth());
                            i.putExtra("theatreUid",theatre_and_showtimesList.get(position).getTheatreUid());
                            i.putExtra("mId",mId);
                            i.putExtra("date_string",theatre_and_showtimesList.get(position).getDate_string());
                            i.putExtra("ttl_no_of_seats", String.valueOf(ttl_no_of_seats));
                            context.startActivity(i);
                            dialog_how_many_seats.dismiss();
                            ttl_no_of_seats = 0;
                            }
                            else
                                Toast.makeText(getContext(),"Pls Select total number of seats",Toast.LENGTH_SHORT).show();
                        }
                    });



                   // Toast.makeText(getContext(),theatre_and_showtimesList.get(position).showTimesList.get(finalI),Toast.LENGTH_SHORT).show();
                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 5, 5);
            tv_showTime.setLayoutParams(params);
            tv_showTime.setTextColor(context.getResources().getColor(R.color.mainTheme));

            ll_showTimes.addView(tv_showTime);


        }


        

        textViewTName.setText(theatre_and_showtimes.getTheatreName());
        textViewTAddress.setText(theatre_and_showtimes.getTheatreAddress());
        return listViewItem;


    }


}
