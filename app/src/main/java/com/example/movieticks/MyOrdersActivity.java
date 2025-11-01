package com.example.movieticks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyOrdersActivity extends AppCompatActivity {
    ListView listViewMoviePasses;
    String userUid;
    DatabaseReference databaseRef;
    ImageView image_close;

    FirebaseUser user;
    LinearLayout ll_no_orders;

    ArrayList<MoviePass> moviesPassesLists=new ArrayList<MoviePass>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        image_close=findViewById(R.id.image_close);
        listViewMoviePasses=findViewById(R.id.listViewMoviePasses);
        listViewMoviePasses.setDividerHeight(0);
        ll_no_orders=findViewById(R.id.ll_no_orders);

        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        user=FirebaseAuth.getInstance().getCurrentUser();

        if(user==null){
            Intent i = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(i);
            finish();
        }


        else {

            userUid = user.getUid();


            databaseRef = FirebaseDatabase.getInstance().getReference("Users Movie Pass");


            databaseRef.child(userUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    moviesPassesLists.clear();

                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        MoviePass moviePass = userSnapshot.getValue(MoviePass.class);
                        moviesPassesLists.add(moviePass);

                    }



                    Collections.sort(moviesPassesLists, new Comparator<MoviePass>() {   //To Sort Orders
                        @Override
                        public int compare(MoviePass o1, MoviePass o2) {
                            return o2.getDateObj().compareTo(o1.getDateObj());
                        }
                    });

                    if (moviesPassesLists.size() <= 0) {
                        ll_no_orders.setVisibility(View.VISIBLE);
                        listViewMoviePasses.setVisibility(View.GONE);
                    } else {
                        ll_no_orders.setVisibility(View.GONE);
                        listViewMoviePasses.setVisibility(View.VISIBLE);
                    }

                   // Toast.makeText(getBaseContext(), String.valueOf(moviesPassesLists.size()), Toast.LENGTH_LONG).show();
                    MoviesPassesList adapter = new MoviesPassesList(MyOrdersActivity.this, moviesPassesLists);
                    listViewMoviePasses.setAdapter(adapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}