package com.example.movieticks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BookTickets extends AppCompatActivity {
    private static MovieInfo movieInfo;
    DatabaseReference databaseRef;

    int flag = 0;

    int ttl_no_of_seats=0;


    LinearLayout ll_dates;

    ListView listViewTheatresShowtimes;

    View previousView,previousView1;
    String totalSeats;

    LinearLayout curDateGlobal;

    TextView tvMName,tvMLanguage,tvMCast,tvMCertificate,tvMDuration;
    ImageView movieImage;

    List<Map<String, Object>> datesList = new ArrayList<Map<String, Object>>();

    Map<Integer, Map<String, String>> sortedDatesMap = new HashMap<Integer, Map<String, String>>();

    //ArrayList<Date> dateObj=new ArrayList<Date>();

    Map<Integer, Date> dateObjMap = new HashMap<Integer, Date>();


    String mId, citySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_ticket);

        mId = getIntent().getStringExtra("mId");
        citySelected = getIntent().getStringExtra("citySelected");




        tvMName=findViewById(R.id.Mname);
        tvMLanguage=findViewById(R.id.mlanguage);
        tvMCast=findViewById(R.id.Mcast);
        tvMCertificate=findViewById(R.id.mCertificate);
        movieImage=findViewById(R.id.MovieImg);
        tvMDuration=findViewById(R.id.mDuration);


        tvMName.setText(movieInfo.getMovieName());
        tvMLanguage.setText(movieInfo.getmLanguage());
        tvMCertificate.setText(movieInfo.getmCertificate());
        Picasso.with(getBaseContext()).load(movieInfo.getMovieImg()).into(movieImage);
        tvMCast.setText(movieInfo.getmCast());
        tvMDuration.setText(movieInfo.getMovieDuration());
        ll_dates = findViewById(R.id.ll_dates);
        listViewTheatresShowtimes = findViewById(R.id.listViewTheatresShowtimes);

        databaseRef = FirebaseDatabase.getInstance().getReference("All Movies Running in Different Theatres");


      ShowTimeAndSeatsSelectionLayout.sendMInfo(movieInfo);//Sending Movie info 

        databaseRef.child(mId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot datesSnapshot : dataSnapshot.getChildren()) {

                    //sortedDatesMap.clear();
                    int dayCount = Integer.parseInt(datesSnapshot.child("dayCount").getValue().toString());
                    String day, date, month, year, date_string;
                    day = datesSnapshot.child("day").getValue().toString();
                    date = datesSnapshot.child("date").getValue().toString();
                    month = datesSnapshot.child("month").getValue().toString();
                    date_string = datesSnapshot.child("date_string").getValue().toString();


                    Date dateTemp=datesSnapshot.child("dateObject").getValue(Date.class);

                    dateObjMap.put(dayCount,dateTemp);

                    //   final String date_string=datesSnapshot.child("date_string").getValue().toString();


                    Map<String, String> tempMap = new HashMap<String, String>();
                    tempMap.put("day", day);
                    tempMap.put("date", date);
                    tempMap.put("month", month);
                    tempMap.put("date_string", date_string);

                    sortedDatesMap.put(dayCount, tempMap);


                }
                ll_dates.removeAllViews();

                for (int dayCount = 1; dayCount <= sortedDatesMap.size(); dayCount++) {

                  //  if((dateObjMap.get(dayCount)!=null)&&dateObjMap.get(dayCount).before(Calendar.getInstance().getTime()))
                    if((dateObjMap.get(dayCount)!=null)&&dateObjMap.get(dayCount).before(Calendar.getInstance().getTime()))
                        continue;

                    final View dateLayout = LayoutInflater.from(BookTickets.this).inflate(R.layout.date_layout, null);
                    TextView tv_month = dateLayout.findViewById(R.id.tv_month);
                    TextView tv_date = dateLayout.findViewById(R.id.tv_date);
                    TextView tv_day = dateLayout.findViewById(R.id.tv_day);

                    //if(sortedDatesMap.containsKey("month"))
                    if(sortedDatesMap.containsKey(dayCount)){
                    tv_month.setText(sortedDatesMap.get(dayCount).get("month"));
                    tv_date.setText(sortedDatesMap.get(dayCount).get("date"));
                    tv_day.setText(sortedDatesMap.get(dayCount).get("day"));

                    final String date_string = sortedDatesMap.get(dayCount).get("date_string");
                    ShowTimeAndSeatsSelectionLayout.setDateObj(dateObjMap.get(dayCount));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(5, 5, 5, 5);
                    dateLayout.setLayoutParams(params);
                    ll_dates.addView(dateLayout);


                    final int finalI = dayCount;
                        final int finalDayCount = dayCount;
                        View.OnClickListener clickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LinearLayout previousDate = (LinearLayout) previousView;
                            LinearLayout curDate = (LinearLayout) v;
                            curDateGlobal = curDate;

                            // If the clicked view is selected, deselect it
                            if (curDate.isSelected()) {
                                curDate.setSelected(false);
                                curDate.setBackgroundResource(R.drawable.tv_border_selected);
                                ShowTimeAndSeatsSelectionLayout.setDateObj(dateObjMap.get(finalDayCount));

                                if(dateObjMap.get(finalDayCount)==null)
                                        Toast.makeText(BookTickets.this, "DateObj is null", Toast.LENGTH_SHORT).show();

                                //ShowTimeAndSeatsSelectionLayout.setDateObj(dateObjMap.get(finalDayCount));


                            //    Toast.makeText(BookTickets.this, sortedDatesMap.get(finalI).get("day") + " is clicked", Toast.LENGTH_SHORT).show();

                                DatabaseReference databaseRefTemp = FirebaseDatabase.getInstance().getReference("All Movies Running in Different Theatres");

                                databaseRefTemp.child(mId).child(date_string).child(citySelected).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        final ArrayList<Theatre_and_showtimes> theatre_and_showtimesList = new ArrayList<Theatre_and_showtimes>();
                                        theatre_and_showtimesList.clear();
                                        for (DataSnapshot theatre_and_showtimesSnapshot : dataSnapshot.getChildren()) {
                                            Theatre_and_showtimes theatre_and_showtimes = theatre_and_showtimesSnapshot.getValue(Theatre_and_showtimes.class);
                                            theatre_and_showtimesList.add(theatre_and_showtimes);
                                        }

                                        TheatresAndShowtimes_list adapter = new TheatresAndShowtimes_list(BookTickets.this, theatre_and_showtimesList);
                                        adapter.setmId(mId);
                                        listViewTheatresShowtimes.setDividerHeight(20);
                                        listViewTheatresShowtimes.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                // });

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.setMargins(5, 5, 5, 5);
                                dateLayout.setLayoutParams(params);


                                curDate.setClickable(false);
                            } else { // If this isn't selected, deselect  the previous one (if any)
                                if (previousDate != null && previousDate.isSelected()) {
                                    previousDate.setSelected(false);
                                    previousDate.setClickable(true);
                                    previousDate.setBackgroundResource(R.drawable.tv_border);
                                }
                                curDate.setSelected(true);
                                curDate.setBackgroundResource(R.drawable.tv_border_selected);
                                ShowTimeAndSeatsSelectionLayout.setDateObj(dateObjMap.get(finalDayCount));

                                if(dateObjMap.get(finalDayCount)==null)
                                    Toast.makeText(BookTickets.this, "DateObj is null", Toast.LENGTH_SHORT).show();

//                                Toast.makeText(BookTickets.this,datesSnapshot.child("day").getValue().toString()+" is clicked",Toast.LENGTH_SHORT).show();

                               // Toast.makeText(BookTickets.this, sortedDatesMap.get(finalI).get("day") + " is clicked", Toast.LENGTH_SHORT).show();


                                DatabaseReference databaseRefTemp = FirebaseDatabase.getInstance().getReference("All Movies Running in Different Theatres");

                                databaseRefTemp.child(mId).child(date_string).child(citySelected).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        final ArrayList<Theatre_and_showtimes> theatre_and_showtimesList = new ArrayList<Theatre_and_showtimes>();
                                        theatre_and_showtimesList.clear();
                                        for (DataSnapshot theatre_and_showtimesSnapshot : dataSnapshot.getChildren()) {
                                            Theatre_and_showtimes theatre_and_showtimes = theatre_and_showtimesSnapshot.getValue(Theatre_and_showtimes.class);
                                            theatre_and_showtimesList.add(theatre_and_showtimes);
                                        }

                                        TheatresAndShowtimes_list adapter = new TheatresAndShowtimes_list(BookTickets.this, theatre_and_showtimesList);
                                        adapter.setmId(mId);
                                        listViewTheatresShowtimes.setDividerHeight(20);
                                        listViewTheatresShowtimes.setAdapter(adapter);
                                        listViewTheatresShowtimes.setFocusable(false);

                                        listViewTheatresShowtimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                                             //   Toast.makeText(BookTickets.this,"theatre is clicked",Toast.LENGTH_SHORT).show();


                                                @SuppressLint("ViewHolder")
                                                View myLayout = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_how_many_seats, null);

                                                RelativeLayout rl_wholeView=myLayout.findViewById(R.id.rl_wholeView);

                                                final ImageView vehicle_imageView=myLayout.findViewById(R.id.vehicle_imageView);
                                                LinearLayout ll_number_of_seats=myLayout.findViewById(R.id.ll_number_of_seats);

                                                final Button btn_selectSeats=myLayout.findViewById(R.id.btn_selectSeats);





                                                View.OnClickListener clickListener = new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        TextView previousText = (TextView) previousView1;
                                                        TextView curText = (TextView) v;
                                                        ttl_no_of_seats=Integer.parseInt(curText.getText().toString());
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
                                                            //    Toast.makeText(getBaseContext(),curText.getText().toString()+" is clicked",Toast.LENGTH_SHORT).show();

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
                                                            //Toast.makeText(getBaseContext(),curText.getText().toString()+" is clicked",Toast.LENGTH_SHORT).show();
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
                                                    if(i==2)
                                                        tvTemp.performClick();

                                                }


                                                AlertDialog.Builder builder_how_many_seats = new AlertDialog.Builder(BookTickets.this);

                                                builder_how_many_seats.setView(myLayout);

                                                final AlertDialog dialog_how_many_seats = builder_how_many_seats.create();
                                                dialog_how_many_seats.setCanceledOnTouchOutside(false);
                                                dialog_how_many_seats.show();
                                                btn_selectSeats.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        if(ttl_no_of_seats!=0) {
                                                            Intent i = new Intent(getBaseContext(), ShowTimeAndSeatsSelectionLayout.class);
                                                          //  ShowTimeAndSeatsSelectionLayout.sendMInfo(movieInfo);//Sending Movie info
                                                            ShowTimeAndSeatsSelectionLayout.sendTInfo(theatre_and_showtimesList.get(position));//Sending Theatre info
                                                            i.putStringArrayListExtra("showTimes", theatre_and_showtimesList.get(position).getShowTimesList());
                                                            i.putExtra("selectedShowtime", theatre_and_showtimesList.get(position).getShowTimesList().get(0));
                                                            i.putExtra("day", sortedDatesMap.get(finalI).get("day"));
                                                            i.putExtra("date", sortedDatesMap.get(finalI).get("date"));
                                                            i.putExtra("month", sortedDatesMap.get(finalI).get("month"));
                                                            i.putExtra("theatreUid", theatre_and_showtimesList.get(position).getTheatreUid());
                                                            i.putExtra("mId", mId);
                                                            i.putExtra("totalSeats", totalSeats);
                                                            i.putExtra("date_string", date_string);
                                                            i.putExtra("ttl_no_of_seats", String.valueOf(ttl_no_of_seats));
                                                            startActivity(i);
                                                            dialog_how_many_seats.dismiss();
                                                            ttl_no_of_seats = 0;
                                                        }
                                                        else
                                                            Toast.makeText(BookTickets.this,"Pls Select total number of seats",Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });




                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.setMargins(5, 5, 5, 5);
                                dateLayout.setLayoutParams(params);
                                curDate.setClickable(false);

                                previousView = v;
                            }

                        }
                    };

                    dateLayout.setOnClickListener(clickListener);

                    if (flag == 0) {
                        dateLayout.performClick();
                        flag = 1;
                    }
                  }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public static void sendMInfo(MovieInfo SelectedmovieInfo){
        movieInfo=SelectedmovieInfo;
    }
}

