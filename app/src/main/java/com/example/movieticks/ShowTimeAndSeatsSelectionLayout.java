package com.example.movieticks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.FutureTask;

public class ShowTimeAndSeatsSelectionLayout extends AppCompatActivity {

    DatabaseReference databaseRef;
    private static MovieInfo movieInfo;
    private static Theatre_and_showtimes theatreInfo;
    private static Date dateObj;
    private static boolean userLogInOrNot=false;
    private static String userId;
    FirebaseUser user;
    ArrayList<String> showTimesList =new ArrayList<String>();
    ArrayList<String> abcList=new ArrayList<String>();
   // private ArrayList<String> selectedSeatsListArray=new ArrayList<String>();

    ImageView movieImage;
    private ArrayList<String> selectedSeatsListSilver=new ArrayList<String>();
    private ArrayList<String> selectedSeatsListGold=new ArrayList<String>();
    private ArrayList<String> selectedSeatsListPlatinum=new ArrayList<String>();
   // public static Map<String, Integer> selectedSeatsList = new HashMap<String, Integer>();

    public static String selectedShowtime,theatreUid,mId;

    public static String day,date,month,date_string;

    int count=0;int row_count=0;int seat_count=0,ttl_no_of_seats, ttl_no_of_seats_temp ,selectedSeatsCount=0;

    int last_silver_seat,last_gold_seat,last_platinum_seat;

    SeatMapInfo seatMapInfo=null,seatMapInfoTemp=null;

    int selectedSeatsCountSilver,selectedSeatsCountGold,selectedSeatsCountPlatinum;
   // ArrayList<String> seatAvailabilityTemp;

    LinearLayout ll_showTimes;
    View previousView,previousView1;
    TextView tv_dayInfo,tv_movieName,tv_theatreName,tv_no_of_tickets,    tv_temp;
    Button btn_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_time_and__seats__selection_layout);


        movieImage=findViewById(R.id.movieImage);
        tv_movieName=findViewById(R.id.tv_movieName);
        tv_theatreName=findViewById(R.id.tv_theatreName);
        tv_no_of_tickets=findViewById(R.id.tv_no_of_tickets);
        btn_pay=findViewById(R.id.btn_pay);

      //  btn_pay.setVisibility(View.GONE);

       // databaseRef = FirebaseDatabase.getInstance().getReference("All Movies");

        //if(dateObj==null)
          //     Toast.makeText(getBaseContext(),"DateObj is null",Toast.LENGTH_SHORT).show();



            showTimesList=getIntent().getStringArrayListExtra("showTimes");
        selectedShowtime=getIntent().getStringExtra("selectedShowtime");
        day=getIntent().getStringExtra("day");
        date=getIntent().getStringExtra("date");
        month=getIntent().getStringExtra("month");
        theatreUid=getIntent().getStringExtra("theatreUid");
        mId=getIntent().getStringExtra("mId");
        date_string=getIntent().getStringExtra("date_string");
        ttl_no_of_seats=Integer.parseInt(getIntent().getStringExtra("ttl_no_of_seats"));


        tv_no_of_tickets.setText(ttl_no_of_seats+" Tickets");
        tv_movieName.setText(movieInfo.getMovieName());
        Picasso.with(getBaseContext()).load(movieInfo.getMovieImg()).into(movieImage);
        tv_theatreName.setText(theatreInfo.getTheatreName()+","+theatreInfo.getTheatreAddress());



        for (char c = 'A'; c <= 'Z'; c++)
            abcList.add (String.valueOf (c));

        final View myLayout = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_how_many_seats, null);
        final ImageView vehicle_imageView=myLayout.findViewById(R.id.vehicle_imageView);

        final LinearLayout ll_number_of_seats=myLayout.findViewById(R.id.ll_number_of_seats);

        final Button btn_selectSeats=myLayout.findViewById(R.id.btn_selectSeats);
        tv_no_of_tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TextView previousText = (TextView) previousView1;
                        TextView curText = (TextView) v;
                        ttl_no_of_seats_temp=Integer.parseInt(curText.getText().toString());
                        // If the clicked view is selected, deselect it
                        if (curText.isSelected()) {
                            curText.setSelected(false);
                            curText.setTextColor(Color.RED);
                            curText.setClickable(false);

                         //   Toast.makeText(getBaseContext(),curText.getText().toString()+" is clicked",Toast.LENGTH_SHORT).show();


                        } else { // If this isn't selected, deselect  the previous one (if any)
                            if (previousText != null && previousText.isSelected()) {
                                previousText.setSelected(false);
                                previousText.setClickable(true);
                                previousText.setTextColor(Color.RED);
                                //Toast.makeText(getBaseContext(),curText.getText().toString()+" is clicked",Toast.LENGTH_SHORT).show();

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




                            //  Toast.makeText(getBaseContext(),curText.getText().toString()+" is clicked",Toast.LENGTH_SHORT).show();
                          //  totalSeats= curText.getText().toString();
                            curText.setClickable(false);
                            previousView1 = v;
                        }

                    }
                };




                ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);

                for(int i=1;i<11;i++)
                {


                    TextView tvTemp=new TextView(getBaseContext());
                    tvTemp.setText(String.valueOf(i));
                    tvTemp.setTextColor(Color.RED);
                    tvTemp.setLayoutParams(params);
                    tvTemp.setOnClickListener(clickListener);

                    ll_number_of_seats.addView(tvTemp);
                    if(i==ttl_no_of_seats)
                        tvTemp.performClick();
                }


                AlertDialog.Builder builder_how_many_seats = new AlertDialog.Builder(ShowTimeAndSeatsSelectionLayout.this);

                builder_how_many_seats.setView(myLayout);

                final AlertDialog dialog_how_many_seats = builder_how_many_seats.create();
                dialog_how_many_seats.setCanceledOnTouchOutside(false);
                dialog_how_many_seats.show();

                btn_selectSeats.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ttl_no_of_seats_temp<ttl_no_of_seats)
                        {
                            seatMapInfo=seatMapInfoTemp;
                            ttl_no_of_seats=ttl_no_of_seats_temp;
                            selectedSeatsCount=0;

                            View myLayout = LayoutInflater.from(ShowTimeAndSeatsSelectionLayout.this).inflate(R.layout.seat_map, null);

                            LinearLayout ll_silver,ll_gold,ll_platinum;
                            RelativeLayout rl_temp;

                            rl_temp=findViewById(R.id.rl_temp);
                            rl_temp.removeAllViews();

                            ll_silver = myLayout.findViewById(R.id.linearLayout_silver);
                            ll_gold = myLayout.findViewById(R.id.linearLayout_gold);
                            ll_platinum = myLayout.findViewById(R.id.linearLayout_platinum);

                            ll_silver.addView(set_seats_in_layout(seatMapInfo.getSeatRows().get("silver_seat_rows"),seatMapInfo.Seats_per_rows.get("seats_per_row_silver"),false));
                            last_silver_seat=seat_count;

                            ll_gold.addView(set_seats_in_layout(seatMapInfo.SeatRows.get("gold_seat_rows"),seatMapInfo.Seats_per_rows.get("seats_per_row_gold"),false));
                            last_gold_seat=seat_count;

                            ll_platinum.addView(set_seats_in_layout(seatMapInfo.SeatRows.get("platinum_seat_rows"),seatMapInfo.Seats_per_rows.get("seats_per_row_platinum"),false));
                            last_platinum_seat=seat_count;

    //        This Code should also be changed at line 297

                            rl_temp.addView(myLayout);
                           // Toast.makeText(getBaseContext(),"First is "+seatMapInfo.getSeatEnableDisableList().get(0)+"\nRowCount="+row_count+"\nSeatCount="+seat_count,Toast.LENGTH_SHORT).show();
                            row_count=0;
                            seat_count=0;
                            tv_no_of_tickets.setText(ttl_no_of_seats+" Tickets");
                            dialog_how_many_seats.dismiss();
                        }
                        else{
                            ttl_no_of_seats=ttl_no_of_seats_temp;
                            if(ttl_no_of_seats>0&&ttl_no_of_seats<11) {
                                dialog_how_many_seats.dismiss();
                                tv_no_of_tickets.setText(ttl_no_of_seats+" Tickets");
                                //ttl_no_of_seats = 0;
                            }
                            else
                                Toast.makeText(ShowTimeAndSeatsSelectionLayout.this,"Pls Select total number of seats",Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });


        databaseRef = FirebaseDatabase.getInstance().getReference("All Theatres UID");





     /*   databaseRef.child(theatreUid).child(mId).child(date_string).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
*/
              //  seatMapInfo = dataSnapshot.getValue(SeatMapInfo.class);
                //seatMapInfoTemp=dataSnapshot.getValue(SeatMapInfo.class);
             //   seatAvailabilityTemp=seatMapInfo.getSeatAvailability().get(selectedShowtime);

        //        Toast.makeText(getBaseContext(),"First is "+seatMapInfo.getSeatEnableDisableList().get(0)+"\nRowCount="+row_count+"\nSeatCount="+seat_count,Toast.LENGTH_SHORT).show();



                ll_showTimes=findViewById(R.id.ll_showTimes);
                tv_dayInfo=findViewById(R.id.tv_dayInfo);

                tv_dayInfo.setText(date+" "+month +", "+day);

                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        v.setBackgroundResource(R.drawable.selected_seat_design);
                        ((TextView) v).setTextColor(Color.parseColor("#7528E4"));

                        if(previousView!=null) {
                            ((TextView) previousView).setTextColor(Color.WHITE);
                            previousView.setBackgroundResource(R.drawable.tv_border_only_outline);
                        }

                        selectedSeatsCount=0;
                        databaseRef.child(theatreUid).child(mId).child(date_string).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                seatMapInfo = dataSnapshot.getValue(SeatMapInfo.class);
                                seatMapInfoTemp=dataSnapshot.getValue(SeatMapInfo.class);

                                last_silver_seat=seatMapInfo.getSeatRows().get("silver_seat_rows")*seatMapInfo.Seats_per_rows.get("seats_per_row_silver");
                                last_gold_seat=last_silver_seat+seatMapInfo.SeatRows.get("gold_seat_rows")*seatMapInfo.Seats_per_rows.get("seats_per_row_gold");
                                last_platinum_seat=last_gold_seat+seatMapInfo.SeatRows.get("platinum_seat_rows")*seatMapInfo.Seats_per_rows.get("seats_per_row_platinum");
                              //  Toast.makeText(getBaseContext(),"First is "+seatMapInfo.getSeatEnableDisableList().get(0)+"\nRowCount="+row_count+"\nSeatCount="+seat_count,Toast.LENGTH_SHORT).show();




                                TextView previousText = (TextView) previousView;
                                TextView curText = (TextView) v;
                                if (previousText != null && previousText.isSelected()) {
                                    previousText.setSelected(false);
                                    previousText.setClickable(true);
                                    previousText.setBackgroundResource(R.drawable.tv_border_only_outline);
                               //     previousText.setTextColor(Color.WHITE);
                               //     Toast.makeText(getBaseContext(),curText.getText().toString()+" is clicked",Toast.LENGTH_SHORT).show();

                                }
                                curText.setSelected(true);
                                curText.setBackgroundResource(R.drawable.selected_seat_design);
                               // curText.setTextColor(Color.BLACK);

                                selectedShowtime=curText.getText().toString();
                                View myLayout = LayoutInflater.from(ShowTimeAndSeatsSelectionLayout.this).inflate(R.layout.seat_map, null);

                                LinearLayout ll_silver,ll_gold,ll_platinum;
                                TextView tv_silver_ticket_price,tv_gold_ticket_price,tv_platinum_ticket_price;

                                RelativeLayout rl_temp;

                                rl_temp=findViewById(R.id.rl_temp);
                                rl_temp.removeAllViews();

                                ll_silver = myLayout.findViewById(R.id.linearLayout_silver);
                                ll_gold = myLayout.findViewById(R.id.linearLayout_gold);
                                ll_platinum = myLayout.findViewById(R.id.linearLayout_platinum);

                                tv_silver_ticket_price=myLayout.findViewById(R.id.tv_silver_ticket_price);
                                tv_gold_ticket_price=myLayout.findViewById(R.id.tv_gold_ticket_price);
                                tv_platinum_ticket_price=myLayout.findViewById(R.id.tv_platinum_ticket_price);

                                tv_silver_ticket_price.setText("Silver Ticket Price: "+seatMapInfo.getTicket_price().get("silver_ticket_price")+"Rs");
                                tv_gold_ticket_price.setText("Gold Ticket Price: "+seatMapInfo.getTicket_price().get("gold_ticket_price")+"Rs");
                                tv_platinum_ticket_price.setText("Platinum Ticket Price: "+seatMapInfo.getTicket_price().get("platinum_ticket_price")+"Rs");


                                ll_silver.addView(set_seats_in_layout(seatMapInfo.getSeatRows().get("silver_seat_rows"),seatMapInfo.Seats_per_rows.get("seats_per_row_silver"),false));
                                ll_gold.addView(set_seats_in_layout(seatMapInfo.SeatRows.get("gold_seat_rows"),seatMapInfo.Seats_per_rows.get("seats_per_row_gold"),false));
                                ll_platinum.addView(set_seats_in_layout(seatMapInfo.SeatRows.get("platinum_seat_rows"),seatMapInfo.Seats_per_rows.get("seats_per_row_platinum"),false));
                          //      This Code should also be changed at line 190


                                rl_temp.addView(myLayout);
                              //  Toast.makeText(getBaseContext(),"First is "+seatMapInfo.getSeatEnableDisableList().get(0)+"\nRowCount="+row_count+"\nSeatCount="+seat_count,Toast.LENGTH_SHORT).show();
                                row_count=0;
                                seat_count=0;





                               // tv_dayInfo.setText(selectedShowtime+", "+day+", "+date+" "+month);
                                tv_dayInfo.setText(date+" "+month +", "+day);
                                curText.setClickable(false);
                                previousView = v;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


//                        }

                    }
                };



                for(int i=0;i<showTimesList.size();i++){
                    TextView tv_showTime=new TextView(getBaseContext());
                    tv_showTime.setText(showTimesList.get(i));
                    tv_showTime.setTextColor(Color.WHITE);


                    tv_showTime.setOnClickListener(clickListener);

                    if(showTimesList.get(i).equals(selectedShowtime)){
                        tv_showTime.performClick();

                    }
                    else
                        tv_showTime.setBackgroundResource(R.drawable.tv_border_only_outline);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(5, 5, 5, 5);
                    tv_showTime.setLayoutParams(params);

                    ll_showTimes.addView(tv_showTime);
                }





           // }
           /* @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }*/

           // });


        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    user= FirebaseAuth.getInstance().getCurrentUser();
                    if(user==null)
                    {
                        Toast.makeText(getBaseContext(), "You need Login first", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ShowTimeAndSeatsSelectionLayout.this, LoginActivity.class);
                        startActivity(i);
                    }

                    //databaseRef = FirebaseDatabase.getInstance().getReference("All Theatres UID");
                    //databaseRef.child(theatreUid).child(mId).child(date_string).setValue(seatMapInfo);// It can be used later


                    if (selectedSeatsCount != ttl_no_of_seats) {
                        Toast.makeText(getBaseContext(), "Pls Choose " + (ttl_no_of_seats - selectedSeatsCount) + " more seats", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(user!=null){
                        OrderSummary.sendMInfo(movieInfo);
                        OrderSummary.sendTInfo(theatreInfo);
                        OrderSummary.setSelectedSeatsCount(selectedSeatsCountSilver,selectedSeatsCountGold,selectedSeatsCountPlatinum);
                        OrderSummary.setSeatMapInfo(seatMapInfo);
                        OrderSummary.setDateObj(dateObj);
                        Collections.sort(selectedSeatsListSilver);
                        Collections.sort(selectedSeatsListGold);
                        Collections.sort(selectedSeatsListPlatinum);

                        selectedSeatsListSilver = new ArrayList<String>(new LinkedHashSet<String>(selectedSeatsListSilver));
                        selectedSeatsListGold = new ArrayList<String>(new LinkedHashSet<String>(selectedSeatsListGold));
                        selectedSeatsListPlatinum = new ArrayList<String>(new LinkedHashSet<String>(selectedSeatsListPlatinum));

                        OrderSummary.setSelectedSeatsList(selectedSeatsListSilver,selectedSeatsListGold,selectedSeatsListPlatinum);


                        Intent i = new Intent(ShowTimeAndSeatsSelectionLayout.this, OrderSummary.class);
                        startActivity(i);
                       // btn_pay.setText(seatMapInfo.getTicket_price().get("silver_ticket_price") + "×" + selectedSeatsCountSilver + "+" + seatMapInfo.getTicket_price().get("gold_ticket_price") + "×" + selectedSeatsCountGold + "+" + seatMapInfo.getTicket_price().get("platinum_ticket_price") + "×" + selectedSeatsCountPlatinum);
                      //  Toast.makeText(getBaseContext(), "Done", Toast.LENGTH_SHORT).show();
                        }
                    }

            }
        });


    }

    public LinearLayout set_seats_in_layout(int no_of_rows, int seats_per_row, boolean editable) {

        LinearLayout ll_seat_all_rows = new LinearLayout(this);
        ll_seat_all_rows.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll_seat_all_rows.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 3, 5, 3);
        params.gravity = Gravity.CENTER_HORIZONTAL;

        /*LinearLayout ll_seat_first_row = new LinearLayout(this);
        ll_seat_first_row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll_seat_first_row.setOrientation(LinearLayout.HORIZONTAL);


        ll_seat_first_row.setWeightSum(seats_per_row + 1);
*/

        for (int i = 0; i <= no_of_rows; i++)
        {
            if(i==0)   //this was used for showing numbers above seats
            {
/*
                LinearLayout ll_seat_row = new LinearLayout(this);
                ll_seat_row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ll_seat_row.setOrientation(LinearLayout.HORIZONTAL);
                ll_seat_row.setWeightSum(seats_per_row);

                for (int j = 1; j <= seats_per_row; j++) {

                    if (j == 1) {
                        final TextView abc = new TextView(this);
                        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params2.setMargins(5, 3, 10, 3);
                        params2.width = 14;
                        params2.gravity = Gravity.CENTER;
                        abc.setTextSize(7);
                        abc.setLayoutParams(params2);
                        abc.setTextColor(Color.WHITE);
                        abc.setText(" ");
                        ll_seat_row.addView(abc);
                    }
                    final TextView b = new TextView(this);


                    if(seats_per_row>9)
                    {
                        b.setTextSize(5);
                        if(j<10)
                            b.setText("   "+j);
                        else
                            b.setText(String.valueOf(j));
                        params.weight = 1;
                        b.setLayoutParams(params);
                        b.setGravity(Gravity.CENTER);
                        b.setTextColor(Color.WHITE);
                    }
                    else
                    {
                        b.setTextSize(5);
                        b.setText(String.valueOf(j));
                        params.weight = 1;
                        b.setLayoutParams(params);
                        b.setGravity(Gravity.CENTER);
                        b.setTextColor(Color.WHITE);
                    }

                    ll_seat_row.addView(b);

                }
                ll_seat_all_rows.addView(ll_seat_row);
*/
            }




            else{
                LinearLayout ll_seat_row = new LinearLayout(this);
                ll_seat_row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ll_seat_row.setOrientation(LinearLayout.HORIZONTAL);


                ll_seat_row.setWeightSum(seats_per_row);
                //  params.gravity = Gravity.START;
                //  ll_seat_row.setLayoutParams(params);
                params.setMargins(5, 3, 5, 3);

                int seatCountRowWise=0;
                for (int j = 1; j <= seats_per_row; j++) {
                    if (j == 1) {
                        final TextView abc = new TextView(this);
                        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params2.setMargins(5, 0, 10, 0);
                        params2.width = 14;
                      ///  params2.gravity = Gravity.CENTER;
                        abc.setTextSize(7);
                        abc.setLayoutParams(params2);
                        abc.setTextColor(Color.WHITE);
                        abc.setGravity(Gravity.CENTER);
                        if (row_count < 26) {
                            abc.setText(abcList.get(row_count));
                            row_count++;
                        }

                        ll_seat_row.addView(abc);
                    }





                    final TextView b = new TextView(this);
                    //  b.setText("LL"+i+":"+j);
                    b.setTextSize(5);
                   // b.setBackgroundResource(R.drawable.seat_design);
                    params.weight = 1;
                    b.setLayoutParams(params);
                    b.setGravity(Gravity.CENTER);
                    // b.setPadding(7,7,7,7);
                    final int finalI = i;
                    final int finalJ = j;
                    seat_count++;

                    final int finalSeatCount=seat_count;
                    //  seatCountList.add(finalSeatCount);

                    if(editable) // Unnecessary in this app, it was used in admin app
                    {
                       /* seatMapInfo.seatEnableDisableList.add(finalSeatCount,"enable");
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (seatMapInfo.seatEnableDisableList.get(finalSeatCount).equals("enable")) {
                                    b.setBackgroundColor(Color.BLUE);
                                    seatMapInfo.seatEnableDisableList.set(finalSeatCount,"disable");
                                }
                                else {
                                    b.setBackgroundResource(R.drawable.seat_design);
                                    seatMapInfo.seatEnableDisableList.set(finalSeatCount,"enable");
                                }
                                Toast.makeText(getBaseContext(), "SeatNo. :"+finalSeatCount+"\nLL:" + finalI + " " + finalJ + " is clicked", Toast.LENGTH_SHORT).show();
                            }
                        });*/
                    }
                    else
                    {

                       // Toast.makeText(getBaseContext(), "Size:-"+seatMapInfo.seatEnableDisableList.size(), Toast.LENGTH_SHORT).show();
                        if(finalSeatCount<seatMapInfo.getSeatEnableDisableList().size())
                            if (seatMapInfo.getSeatEnableDisableList().get(finalSeatCount).equals("enable"))
                            {

                                seatCountRowWise++;
                               // b.setText(String.valueOf(seatCountRowWise));
                                b.setTextColor(Color.WHITE);

                                if(seats_per_row>9)
                                {
                                    b.setTextSize(5);
                                    if(j<10) {
                                        b.setText("  " + seatCountRowWise+" ");
                                    }
                                    else
                                        b.setText(seatCountRowWise+"");
                                      //  b.setText(String.valueOf(j));
                                    params.weight = 1;
                                    b.setLayoutParams(params);
                                    b.setGravity(Gravity.CENTER);
                                    b.setTextColor(Color.WHITE);
                                }
                                else
                                {
                                    b.setTextSize(5);
                                   // b.setText(String.valueOf(j));
                                    b.setText(seatCountRowWise+"");
                                    params.weight = 1;
                                    b.setLayoutParams(params);
                                    b.setGravity(Gravity.CENTER);
                                    b.setTextColor(Color.WHITE);
                                }




                                final int finalSeatCountRowWise = seatCountRowWise;
                                final int final_row_count = row_count;
                                b.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TextView textView = (TextView) v;

                                        if(selectedSeatsCount<ttl_no_of_seats&&selectedSeatsCount>=0) {
                                            if (textView.isSelected()) { //When tv is Unselected
                                                //textView.setTextColor(Color.RED);
                                               // selectedSeatsListArray.remove(abcList.get(final_row_count-1)+"-"+ finalSeatCountRowWise);
                                                selectedSeatsCount--;
                                                b.setBackgroundResource(R.drawable.available_seat_design);
                                                b.setTextColor(Color.WHITE);
                                                seatMapInfo.getSeatAvailability().get(selectedShowtime).set(finalSeatCount,"available");
                                             //   Toast.makeText(getBaseContext(), "Seat No.:- "+finalSeatCount +"is Unselected", Toast.LENGTH_SHORT).show();



                                             /*   if(finalSeatCount<last_silver_seat) {
                                                    selectedSeatsCountSilver--;
                                                    Toast.makeText(getBaseContext(), "Silver seat price is:" + seatMapInfo.getTicket_price().get("silver_ticket_price") + "Rs", Toast.LENGTH_SHORT).show();
                                                }
                                                else if(finalSeatCount<last_gold_seat){
                                                    selectedSeatsCountGold--;
                                                    Toast.makeText(getBaseContext(), "Gold seat price is:" +seatMapInfo.getTicket_price().get("gold_ticket_price")+"Rs", Toast.LENGTH_SHORT).show();
                                                }
                                                else if(finalSeatCount<last_platinum_seat) {
                                                    selectedSeatsCountPlatinum--;
                                                    Toast.makeText(getBaseContext(), "Platinum seat price is:" + seatMapInfo.getTicket_price().get("platinum_ticket_price") + "Rs", Toast.LENGTH_SHORT).show();
                                                }
                                               */


                                                v.setSelected(false);
                                            }
                                            else {
                                                // textView.setTextColor(Color.BLUE);
                                                selectedSeatsCount++;
                                                b.setBackgroundResource(R.drawable.selected_seat_design);
                                                b.setTextColor(Color.BLACK);

                                               /* if(finalSeatCount<last_gold_seat)
                                                {
                                                    Toast.makeText(getBaseContext(), "Silver seat price :"+seatMapInfo.getTicket_price().get("silver_ticket_price")+"Rs", Toast.LENGTH_SHORT).show();
                                                }
                                                else if(finalSeatCount<last_gold_seat)
                                                {
                                                    Toast.makeText(getBaseContext(), "Gold seat price :"+seatMapInfo.getTicket_price().get("gold_ticket_price")+"Rs", Toast.LENGTH_SHORT).show();
                                                }*/
                                           //    selectedSeatsListArray.add(abcList.get(final_row_count-1)+"-"+ finalSeatCountRowWise);

                                                seatMapInfo.getSeatAvailability().get(selectedShowtime).set(finalSeatCount,"not available");
                                                //     seatAvailabilityTemp.set(finalSeatCount,"not available");


                                                if(finalSeatCount<=last_silver_seat) {
                                                    selectedSeatsCountSilver++;
                                                    selectedSeatsListSilver.add(abcList.get(final_row_count-1)+"-"+ finalSeatCountRowWise);
                                                 //   Toast.makeText(getBaseContext(), "Silver seat price is:" + seatMapInfo.getTicket_price().get("silver_ticket_price") + "Rs", Toast.LENGTH_SHORT).show();
                                                }
                                                else if(finalSeatCount<=last_gold_seat){
                                                    selectedSeatsCountGold++;
                                                    selectedSeatsListGold.add(abcList.get(final_row_count-1)+"-"+ finalSeatCountRowWise);
                                                   // Toast.makeText(getBaseContext(), "Gold seat price is:" +seatMapInfo.getTicket_price().get("gold_ticket_price")+"Rs", Toast.LENGTH_SHORT).show();
                                                }
                                                else if(finalSeatCount<=last_platinum_seat) {
                                                    selectedSeatsCountPlatinum++;
                                                    selectedSeatsListPlatinum.add(abcList.get(final_row_count-1)+"-"+ finalSeatCountRowWise);
                                                   // Toast.makeText(getBaseContext(), "Platinum seat price is:" + seatMapInfo.getTicket_price().get("platinum_ticket_price") + "Rs", Toast.LENGTH_SHORT).show();
                                                }




                                              //  Toast.makeText(getBaseContext(), "Seat No.:- "+finalSeatCount +"is Selected", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getBaseContext(), abcList.get(final_row_count-1)+"-"+ finalSeatCountRowWise, Toast.LENGTH_SHORT).show();

                                           //     selectedSeatsList.add(null);

                                                v.setSelected(true);
                                            }
                                        }

                                        else if(textView.isSelected()){
                                            b.setBackgroundResource(R.drawable.available_seat_design);
                                            seatMapInfo.getSeatAvailability().get(selectedShowtime).set(finalSeatCount,"available");
                                           // Toast.makeText(getBaseContext(), "Seat No.:- "+finalSeatCount +"is Unselected", Toast.LENGTH_SHORT).show();
                                            selectedSeatsCount--;


                                            if(finalSeatCount<last_silver_seat) {
                                                selectedSeatsCountSilver--;
                                                //Toast.makeText(getBaseContext(), "Silver seat price is:" + seatMapInfo.getTicket_price().get("silver_ticket_price") + "Rs", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(finalSeatCount<last_gold_seat){
                                                selectedSeatsCountGold--;
                                               // Toast.makeText(getBaseContext(), "Gold seat price is:" +seatMapInfo.getTicket_price().get("gold_ticket_price")+"Rs", Toast.LENGTH_SHORT).show();
                                            }
                                            else if(finalSeatCount<last_platinum_seat) {
                                                selectedSeatsCountPlatinum--;
                                               // Toast.makeText(getBaseContext(), "Platinum seat price is:" + seatMapInfo.getTicket_price().get("platinum_ticket_price") + "Rs", Toast.LENGTH_SHORT).show();
                                            }





                                            v.setSelected(false);
                                        }
                                        else if(ttl_no_of_seats==10)
                                            Toast.makeText(getBaseContext(), "Maximum 10 seats per ticket", Toast.LENGTH_SHORT).show();

                                        else
                                            Toast.makeText(getBaseContext(), "To select more seats increase more tickets", Toast.LENGTH_LONG).show();


                                    }
                                });


                                if(seatMapInfo.getSeatAvailability().get(selectedShowtime).get(finalSeatCount).equals("available"))
                                    b.setBackgroundResource(R.drawable.available_seat_design);
                                else if(seatMapInfo.getSeatAvailability().get(selectedShowtime).get(finalSeatCount).equals("not available")){
                                     b.setBackgroundResource(R.drawable.booked_seats_design);
                                     b.setClickable(false);
                                }





                                /*b.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(getBaseContext(), "Seat No.:- "+finalSeatCount, Toast.LENGTH_SHORT).show();

                                    }
                                });*/
                            }
                            else
                                b.setBackgroundResource(R.drawable.disabled_seat_design);
                    }

                    ll_seat_row.addView(b);
                }
                ll_seat_all_rows.addView(ll_seat_row);

            }
        }
        return ll_seat_all_rows;

    }


    public static void sendMInfo(MovieInfo SelectedmovieInfo){                 //To receive movie info from another activity
        movieInfo=SelectedmovieInfo;
    }

    public static void sendTInfo(Theatre_and_showtimes SelectedTheaterInfo){                 //To receive Theatre info from another activity
        theatreInfo=SelectedTheaterInfo;
    }

    public static void setUserLogInOrNot(boolean userLogInOrNotTemp){                 //To receive LoginInfo info from another activity
        userLogInOrNot=userLogInOrNotTemp;
    }

    public static void setUserId(String userIdTemp){                 //To receive User ID from another activity
        userId=userIdTemp;
    }


    public static void setDateObj(Date dateObjTemp){                 //To receive Date from another activity
        dateObj=dateObjTemp;
    }
}
