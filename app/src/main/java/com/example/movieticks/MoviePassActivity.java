package com.example.movieticks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class MoviePassActivity extends AppCompatActivity {

    ListView listViewMoviePasses;
    String userUid;
    DatabaseReference databaseRef;
    ImageView image_close;
    LinearLayout ll_no_pass;

    FirebaseUser user;
    ArrayList<MoviePass> moviesPassesLists=new ArrayList<MoviePass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_pass);


        image_close=findViewById(R.id.image_close);
        listViewMoviePasses=findViewById(R.id.listViewMoviePasses);
        listViewMoviePasses.setDividerHeight(0);
        ll_no_pass=findViewById(R.id.ll_no_pass);




        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        databaseRef = FirebaseDatabase.getInstance().getReference("Users Movie Pass");


        user=FirebaseAuth.getInstance().getCurrentUser();

        if(user==null){
            Intent i = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(i);
            finish();
        }


        else {

            userUid = user.getUid();


            databaseRef.child(userUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    moviesPassesLists.clear();

                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        MoviePass moviePass = userSnapshot.getValue(MoviePass.class);
                        if ((moviePass.getDateObj() != null) && (moviePass.getDateObj().before(Calendar.getInstance().getTime())))
                            continue;
                        moviesPassesLists.add(moviePass);

                    }


                    Collections.sort(moviesPassesLists, new Comparator<MoviePass>() {
                        @Override
                        public int compare(MoviePass o1, MoviePass o2) {
                            return o2.getDateObj().compareTo(o1.getDateObj());
                        }
                    });


                    if (moviesPassesLists.size() <= 0) {
                        ll_no_pass.setVisibility(View.VISIBLE);
                        listViewMoviePasses.setVisibility(View.GONE);
                    } else {
                        ll_no_pass.setVisibility(View.GONE);
                        listViewMoviePasses.setVisibility(View.VISIBLE);
                    }

                    if (moviesPassesLists.size() == 1)
                        Toast.makeText(getBaseContext(), "Only " + String.valueOf(moviesPassesLists.size() + " Pass available"), Toast.LENGTH_LONG).show();
                    else if (moviesPassesLists.size() >= 1)
                        Toast.makeText(getBaseContext(), "Only " + String.valueOf(moviesPassesLists.size() + " Passes are available"), Toast.LENGTH_LONG).show();


                    MoviesPassesList adapter = new MoviesPassesList(MoviePassActivity.this, moviesPassesLists);
                    listViewMoviePasses.setAdapter(adapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}