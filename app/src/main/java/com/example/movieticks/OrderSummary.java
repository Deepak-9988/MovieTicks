package com.example.movieticks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static com.example.movieticks.ShowTimeAndSeatsSelectionLayout.date_string;
import static com.example.movieticks.ShowTimeAndSeatsSelectionLayout.theatreUid;

public class OrderSummary extends AppCompatActivity {

    DatabaseReference databaseRef;

    private static MovieInfo movieInfo;

    MoviePass moviePass;
    private static Date dateObj;

    private UserInfo userInfo;
    String userUid;
    private static Theatre_and_showtimes theatreInfo;
   // private ArrayList<Integer> selectedSeatsListArray;
    //  private static ArrayList<Integer> selectedSeatsList=new ArrayList<Integer>();

    private static ArrayList<String> selectedSeatsListSilver=new ArrayList<String>();
    private static ArrayList<String> selectedSeatsListGold=new ArrayList<String>();
    private static ArrayList<String> selectedSeatsListPlatinum=new ArrayList<String>();

    int ttlPrice;

    private static SeatMapInfo seatMapInfo;

    TextView tv_seats_selected,tv_email,tv_mobile;

    private static int selectedSeatsCountSilver,selectedSeatsCountGold,selectedSeatsCountPlatinum;


    TextView tvMName,tvMLanguage,tvMCast,tvMCertificate;
    TextView tv_theatreName,tv_theatreAddress,tv_tickets_details,tv_edit_icon,tv_date_info_summary,tv_price_info_summary;
    ImageView movieImage;
    Button btn_back,btn_pay;





    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        tvMName=findViewById(R.id.Mname);
        tvMLanguage=findViewById(R.id.mlanguage);
        tvMCast=findViewById(R.id.Mcast);
        tvMCertificate=findViewById(R.id.mCertificate);
        movieImage=findViewById(R.id.MovieImg);



        databaseRef = FirebaseDatabase.getInstance().getReference("Users");

        userUid=FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot:dataSnapshot.getChildren()){
                    if(userSnapshot.getKey().equals(userUid)) {
                        userInfo = userSnapshot.getValue(UserInfo.class);
                        tv_email.setText(userInfo.getUser_EmailId());
                        tv_mobile.setText(userInfo.getUserMobileNumber());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        tvMName.setText(movieInfo.getMovieName());
        tvMLanguage.setText(movieInfo.getmLanguage());
        tvMCertificate.setText(movieInfo.getmCertificate());
        Picasso.with(getBaseContext()).load(movieInfo.getMovieImg()).into(movieImage);
        tvMCast.setText(movieInfo.getmCast());

        tv_date_info_summary=findViewById(R.id.tv_date_info_summary);
        tv_theatreName=findViewById(R.id.tv_theatreName);
        tv_theatreAddress=findViewById(R.id.tv_theatreAddress);

        tv_tickets_details=findViewById(R.id.tv_tickets_details);
        tv_seats_selected=findViewById(R.id.tv_seats_selected);
        tv_seats_selected.setMovementMethod(new ScrollingMovementMethod());

        tv_price_info_summary=findViewById(R.id.tv_price_info_summary);

        tv_email=findViewById(R.id.tv_email);
        tv_mobile=findViewById(R.id.tv_mobile);





        tv_edit_icon=findViewById(R.id.tv_edit_icon);
        tv_edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Edit Icon Clicked", Toast.LENGTH_SHORT).show();

            }
        });

        btn_back=findViewById(R.id.btn_back);
        btn_pay=findViewById(R.id.btn_pay);








        tv_date_info_summary.setText(ShowTimeAndSeatsSelectionLayout.selectedShowtime+", "+ShowTimeAndSeatsSelectionLayout.day+", "+ShowTimeAndSeatsSelectionLayout.date+" "+ShowTimeAndSeatsSelectionLayout.month);
        tv_theatreName.setText(theatreInfo.theatreName);
        tv_theatreAddress.setText(theatreInfo.theatreAddress);

        tv_tickets_details.setText("Silver Tickets-"+ selectedSeatsCountSilver+"\n"+"Gold Tickets-"+selectedSeatsCountGold+"\n"+"Platinum Tickets-"+selectedSeatsCountPlatinum);


        tv_price_info_summary.setText(seatMapInfo.getTicket_price().get("silver_ticket_price") + "×" + selectedSeatsCountSilver + " + " + seatMapInfo.getTicket_price().get("gold_ticket_price") + "×" + selectedSeatsCountGold + " + " + seatMapInfo.getTicket_price().get("platinum_ticket_price") + "×" + selectedSeatsCountPlatinum+" = "+(seatMapInfo.getTicket_price().get("silver_ticket_price") *selectedSeatsCountSilver + seatMapInfo.getTicket_price().get("gold_ticket_price") * selectedSeatsCountGold  + seatMapInfo.getTicket_price().get("platinum_ticket_price") *selectedSeatsCountPlatinum));

        ttlPrice=seatMapInfo.getTicket_price().get("silver_ticket_price") *selectedSeatsCountSilver + seatMapInfo.getTicket_price().get("gold_ticket_price") * selectedSeatsCountGold  + seatMapInfo.getTicket_price().get("platinum_ticket_price") *selectedSeatsCountPlatinum;

        btn_pay.setText("PAY-₹"+ttlPrice);

        for(int i=0;i<selectedSeatsListSilver.size();i++){
            String strTemp=tv_seats_selected.getText().toString();
            if(i!=selectedSeatsListSilver.size()-1)
                tv_seats_selected.setText(strTemp+selectedSeatsListSilver.get(i)+", ");
            else
                tv_seats_selected.setText(strTemp+selectedSeatsListSilver.get(i));
        }

        if(selectedSeatsListSilver.size()<1) {
            tv_seats_selected.setText(tv_seats_selected.getText().toString() + "- - -");
            tv_price_info_summary.setText( seatMapInfo.getTicket_price().get("gold_ticket_price") + "×" + selectedSeatsCountGold + " + " + seatMapInfo.getTicket_price().get("platinum_ticket_price") + "×" + selectedSeatsCountPlatinum+"="+(seatMapInfo.getTicket_price().get("silver_ticket_price") *selectedSeatsCountSilver + seatMapInfo.getTicket_price().get("gold_ticket_price") * selectedSeatsCountGold  + seatMapInfo.getTicket_price().get("platinum_ticket_price") *selectedSeatsCountPlatinum));

           // tv_price_info_summary.setText(seatMapInfo.getTicket_price().get("gold_ticket_price") + "×" + selectedSeatsCountGold + " + " + seatMapInfo.getTicket_price().get("platinum_ticket_price") + "×" + selectedSeatsCountPlatinum);

        }
        tv_seats_selected.setText(tv_seats_selected.getText().toString()+"\n");

        for(int i=0;i<selectedSeatsListGold.size();i++){
            String strTemp=tv_seats_selected.getText().toString();
            if(i!=selectedSeatsListGold.size()-1)
                tv_seats_selected.setText(strTemp+selectedSeatsListGold.get(i)+", ");
            else
                tv_seats_selected.setText(strTemp+selectedSeatsListGold.get(i));
        }

        if(selectedSeatsListGold.size()<1) {
            tv_seats_selected.setText(tv_seats_selected.getText().toString() + "- - -");
            tv_price_info_summary.setText(seatMapInfo.getTicket_price().get("silver_ticket_price") + "×" + selectedSeatsCountSilver +  " + " + seatMapInfo.getTicket_price().get("platinum_ticket_price") + "×" + selectedSeatsCountPlatinum+"="+(seatMapInfo.getTicket_price().get("silver_ticket_price") *selectedSeatsCountSilver + seatMapInfo.getTicket_price().get("gold_ticket_price") * selectedSeatsCountGold  + seatMapInfo.getTicket_price().get("platinum_ticket_price") *selectedSeatsCountPlatinum));

        }
        tv_seats_selected.setText(tv_seats_selected.getText().toString()+"\n");

        for(int i=0;i<selectedSeatsListPlatinum.size();i++){
            String strTemp=tv_seats_selected.getText().toString();
            if(i!=selectedSeatsListPlatinum.size()-1)
                tv_seats_selected.setText(strTemp+selectedSeatsListPlatinum.get(i)+", ");
            else
                tv_seats_selected.setText(strTemp+selectedSeatsListPlatinum.get(i));
        }
        if(selectedSeatsListPlatinum.size()<1) {
            tv_seats_selected.setText(tv_seats_selected.getText().toString() + "- - -");
            tv_price_info_summary.setText(seatMapInfo.getTicket_price().get("platinum_ticket_price") + "×" + selectedSeatsCountPlatinum+"="+(seatMapInfo.getTicket_price().get("silver_ticket_price") *selectedSeatsCountSilver + seatMapInfo.getTicket_price().get("gold_ticket_price") * selectedSeatsCountGold  + seatMapInfo.getTicket_price().get("platinum_ticket_price") *selectedSeatsCountPlatinum));

        }


        if(selectedSeatsListGold.size()<1&&selectedSeatsListPlatinum.size()<1)
            tv_price_info_summary.setText(seatMapInfo.getTicket_price().get("silver_ticket_price") + "×" + selectedSeatsCountSilver+"="+(seatMapInfo.getTicket_price().get("silver_ticket_price") *selectedSeatsCountSilver + seatMapInfo.getTicket_price().get("gold_ticket_price") * selectedSeatsCountGold  + seatMapInfo.getTicket_price().get("platinum_ticket_price") *selectedSeatsCountPlatinum));
        else if (selectedSeatsListSilver.size()<1&&selectedSeatsListPlatinum.size()<1)
            tv_price_info_summary.setText(seatMapInfo.getTicket_price().get("gold_ticket_price") + "×" + selectedSeatsCountGold+"="+(seatMapInfo.getTicket_price().get("silver_ticket_price") *selectedSeatsCountSilver + seatMapInfo.getTicket_price().get("gold_ticket_price") * selectedSeatsCountGold  + seatMapInfo.getTicket_price().get("platinum_ticket_price") *selectedSeatsCountPlatinum));
        else if(selectedSeatsListSilver.size()<1&&selectedSeatsListGold.size()<1)
            tv_price_info_summary.setText(seatMapInfo.getTicket_price().get("platinum_ticket_price") + "×" + selectedSeatsCountPlatinum+"="+(seatMapInfo.getTicket_price().get("silver_ticket_price") *selectedSeatsCountSilver + seatMapInfo.getTicket_price().get("gold_ticket_price") * selectedSeatsCountGold  + seatMapInfo.getTicket_price().get("platinum_ticket_price") *selectedSeatsCountPlatinum));




        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
               

            }
        });


        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moviePass=new MoviePass(movieInfo,tv_date_info_summary.getText().toString(),tv_theatreName.getText().toString(),tv_theatreAddress.getText().toString(),tv_tickets_details.getText().toString(),tv_seats_selected.getText().toString(),ttlPrice,dateObj);

                databaseRef = FirebaseDatabase.getInstance().getReference("Users Movie Pass");
                String moviePassUid=movieInfo.getmId()+ UUID.randomUUID().toString()+"_"+moviePass.getTimeAndDate();

                databaseRef.child(userUid).child(moviePassUid).setValue(moviePass);


                databaseRef = FirebaseDatabase.getInstance().getReference("All Theatres UID");
                databaseRef.child(theatreUid).child(movieInfo.mId).child(date_string).setValue(seatMapInfo);



                Toast.makeText(getBaseContext(), "Ticket Booked Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderSummary.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                Intent intent1 = new Intent(getBaseContext(), MoviePassActivity.class);
                startActivity(intent1);

            }
        });




    }



    public static void sendMInfo(MovieInfo SelectedmovieInfo){                 //To receive movie info from another activity
        movieInfo=SelectedmovieInfo;
    }

    public static void sendTInfo(Theatre_and_showtimes SelectedTheaterInfo){                 //To receive Theatre info from another activity
        theatreInfo=SelectedTheaterInfo;
    }

    public static void setSelectedSeatsList(ArrayList<String> selectedSeatTemp1,ArrayList<String> selectedSeatTemp2,ArrayList<String> selectedSeatTemp3){                 //To receive SelectedSeats from another activity
        selectedSeatsListSilver=selectedSeatTemp1;
        selectedSeatsListGold=selectedSeatTemp2;
        selectedSeatsListPlatinum=selectedSeatTemp3;
    }



    public static void setSeatMapInfo(SeatMapInfo seatMapInfoTemp){                 //To receive SeatMapInfo from another activity
        seatMapInfo=seatMapInfoTemp;
    }

    public static void setSelectedSeatsCount( int selectedSeatsCountSilverT,int selectedSeatsCountGoldT,int selectedSeatsCountPlatinumT){
        selectedSeatsCountSilver=selectedSeatsCountSilverT;
        selectedSeatsCountGold=selectedSeatsCountGoldT;
        selectedSeatsCountPlatinum=selectedSeatsCountPlatinumT;
    }

    public static void setDateObj(Date dateObjTemp){                 //To receive Date from another activity
        dateObj=dateObjTemp;
    }

}