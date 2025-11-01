package com.example.movieticks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class PrivacyPolicy extends AppCompatActivity {


    String userUid;
    DatabaseReference databaseRef;
    ImageView image_close;
    TextView tvPrivacyPolicyEdit;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);


        image_close=findViewById(R.id.image_close);
        tvPrivacyPolicyEdit=findViewById(R.id.tvPrivacyPolicyEdit);
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        databaseRef = FirebaseDatabase.getInstance().getReference("Users Movie Pass");


        user= FirebaseAuth.getInstance().getCurrentUser();

        if(user==null){
            Intent i = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(i);
            finish();
        }


        else {

            userUid = user.getUid();


            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference uidRef = rootRef.child("Privacy Policy");
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String str = dataSnapshot.child("privacyPolicy").getValue(String.class);
                    if(str!=null)
                        tvPrivacyPolicyEdit.setText(str);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            uidRef.addListenerForSingleValueEvent(eventListener);



        }



    }
}