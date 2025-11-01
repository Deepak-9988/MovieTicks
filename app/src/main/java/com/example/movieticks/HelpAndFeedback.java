package com.example.movieticks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class HelpAndFeedback extends AppCompatActivity {

    EditText etPrivacyPolicyEdit;
    FirebaseUser user;
    DatabaseReference databaseRef;
    TextView tv_done;
    ImageView image_close;
    String userUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_feedback);

        image_close=findViewById(R.id.image_close);
        etPrivacyPolicyEdit=findViewById(R.id.etPrivacyPolicyEdit);

        tv_done=findViewById(R.id.tv_done);

        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        databaseRef = FirebaseDatabase.getInstance().getReference("Users Feedback");


        user= FirebaseAuth.getInstance().getCurrentUser();

        if(user==null){
            Intent i = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(i);
            finish();
        }


        else {

            tv_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userUid = user.getUid();


                    if(etPrivacyPolicyEdit.getText().toString().isEmpty())
                        Toast.makeText(getBaseContext(), "Pls write Some Feedback", Toast.LENGTH_LONG).show();

                    else {


                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users");
                        DatabaseReference uidRef = rootRef.child(userUid);
                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String userName, userPhoneNumber;
                                userName = dataSnapshot.child("userName").getValue(String.class);
                                userPhoneNumber = dataSnapshot.child("userMobileNumber").getValue(String.class);
                                databaseRef.child(userName+"_"+userPhoneNumber+"_"+userUid).child(UUID.randomUUID().toString()).setValue(etPrivacyPolicyEdit.getText().toString());
                                Toast.makeText(getBaseContext(), "Thanks For Your Valuable Feedback\n               Keep Visiting", Toast.LENGTH_LONG).show();

                                finish();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        };
                        uidRef.addListenerForSingleValueEvent(eventListener);

                    }


                }
            });





        }





    }
}