package com.example.movieticks;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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

import java.util.ArrayList;

public class ProfileBtmNav extends Fragment {
    LinearLayout ProfileItems, ProfileMoviePass, ProfileOrders,ProfileOffers,ProfileNotifications,ProfileSettings,ProfilePrivacy,ProfileHelpFeedback;

    LinearLayout ll_userInfo;
    static LinearLayout ll_userInfostatic;

    ListView listViewProfile;

    private FirebaseUser user;

    static TextView tv_login_signUp_static,tv_fullNameStatic,tv_userEmailIdStatic,tv_mobileNumberStatic;


    String userUid;
static String userUidstatic;

    public static String userName, userPhoneNumber, userEmail;

    TextView tv_login_signUp;

    DatabaseReference databaseRef;

    TextView tv_fullName,tv_userEmailId,tv_mobileNumber;

    ArrayList<LinearLayout> linearLayouts=new ArrayList<LinearLayout>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.profile_btm_nav,container,false);




        tv_login_signUp=view.findViewById(R.id.tv_login_signUp);
        tv_login_signUp_static=view.findViewById(R.id.tv_login_signUp);




        tv_fullName=view.findViewById(R.id.fullName);

        tv_userEmailId=view.findViewById(R.id.userEmailId);
        tv_mobileNumber=view.findViewById(R.id.mobileNumber);

        tv_fullNameStatic=view.findViewById(R.id.fullName);

        tv_userEmailIdStatic=view.findViewById(R.id.userEmailId);
        tv_mobileNumberStatic=view.findViewById(R.id.mobileNumber);




        ll_userInfo=view.findViewById(R.id.ll_userInfo);
        ll_userInfostatic=view.findViewById(R.id.ll_userInfo);

        ProfileItems=view.findViewById(R.id.ProfileItems);
        ProfileMoviePass=view.findViewById(R.id.ProfileMoviePass);
        ProfileOrders=view.findViewById(R.id.ProfileOrders); //It can be used later
        ProfileOffers=view.findViewById(R.id.ProfileOffers);
        ProfileNotifications=view.findViewById(R.id.ProfileNotification);
        ProfileSettings=view.findViewById(R.id.ProfileSettings);
        ProfilePrivacy=view.findViewById(R.id.ProfilePrivacy);
        ProfileHelpFeedback=view.findViewById(R.id.ProfileHelpFeedback);


        user=FirebaseAuth.getInstance().getCurrentUser();

        if(user==null){
            ll_userInfo.setVisibility(View.GONE);
            tv_login_signUp.setVisibility(View.VISIBLE);
            tv_login_signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), LoginActivity.class);
                    startActivity(i);
                   // Toast.makeText(getActivity(),"Login Button clicked",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            tv_login_signUp.setVisibility(View.GONE);


            userUid=user.getUid();



            DatabaseReference rootRef =  FirebaseDatabase.getInstance().getReference("Users");
            DatabaseReference uidRef = rootRef.child(userUid);
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    tv_fullName.setText(dataSnapshot.child("userName").getValue(String.class));
                    tv_userEmailId.setText(dataSnapshot.child("user_EmailId").getValue(String.class));
                    tv_mobileNumber.setText(dataSnapshot.child("userMobileNumber").getValue(String.class));

                    //Toast.makeText(getActivity(), userName +" is User  Name",Toast.LENGTH_SHORT).show();

                    ll_userInfo.setVisibility(View.VISIBLE);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
            uidRef.addListenerForSingleValueEvent(eventListener);




        }



        ProfileMoviePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MoviePassActivity.class);
                startActivity(i);
             //   Toast.makeText(getActivity(),"MoviePass clicked",Toast.LENGTH_SHORT).show();
            }
        });

        ProfileOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyOrdersActivity.class);
                startActivity(i);
               // Toast.makeText(getActivity(),"Orders clicked",Toast.LENGTH_SHORT).show();
            }
        });

        ProfileOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Offers clicked",Toast.LENGTH_SHORT).show();
            }
        });

        ProfileNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Notifications clicked",Toast.LENGTH_SHORT).show();
            }
        });

        ProfileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Settings clicked",Toast.LENGTH_SHORT).show();
            }
        });

        ProfilePrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PrivacyPolicy.class);
                startActivity(i);

               // Toast.makeText(getActivity(),"Privacy Policy clicked",Toast.LENGTH_SHORT).show();
            }
        });

        ProfileHelpFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), HelpAndFeedback.class);
                startActivity(i);
                //Toast.makeText(getContext(),"Help & Feedback clicked",Toast.LENGTH_SHORT).show();
            }
        });




       // return inflater.inflate(R.layout.profile_btm_nav, container, false);
        return view;
    }


    public static String getUserName() {
        return userName;
    }

    public static String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setLl_userInfo(){


        tv_login_signUp_static.setVisibility(View.GONE);


        userUidstatic=FirebaseAuth.getInstance().getCurrentUser().getUid();



        DatabaseReference rootRef =  FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference uidRef = rootRef.child(userUidstatic);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tv_fullNameStatic.setText(dataSnapshot.child("userName").getValue(String.class));
                tv_userEmailIdStatic.setText(dataSnapshot.child("user_EmailId").getValue(String.class));
                tv_mobileNumberStatic.setText(dataSnapshot.child("userMobileNumber").getValue(String.class));

                //Toast.makeText(getActivity(), userName +" is User  Name",Toast.LENGTH_SHORT).show();

                ll_userInfostatic.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        uidRef.addListenerForSingleValueEvent(eventListener);

    }
}
