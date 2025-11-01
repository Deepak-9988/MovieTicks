package com.example.movieticks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

    private static View view;
    private static EditText fullName, emailId, mobileNumber, location, password, confirmPassword;
    private static TextView already_user;
    private static Button signUpButton;
    private static CheckBox terms_conditions;

    String userUid;

    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        view = getLayoutInflater().inflate(R.layout.activity_create_account, null);
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();


        fullName = findViewById(R.id.fullName);
        emailId =findViewById(R.id.userEmailId);
        mobileNumber =findViewById(R.id.mobileNumber);
        location = findViewById(R.id.location);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        signUpButton = findViewById(R.id.signUpBtn);
        already_user = findViewById(R.id.already_user);
        terms_conditions = findViewById(R.id.terms_conditions);



        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            already_user.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }



        already_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateAccount.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });




    }




    private void checkValidation() {

        // Get all edittext texts
        final String getFullName = fullName.getText().toString();
        final String getEmailId = emailId.getText().toString();
        final String getMobileNumber = mobileNumber.getText().toString();
        final String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)

            new CustomToast().Show_Toast(getBaseContext(), view, "All fields are required.");

            // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getBaseContext(), view,
                    "Your Email Id is Invalid.");

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getBaseContext(), view,
                    "Both password doesn't match.");

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getBaseContext(), view,
                    "Please select Terms and Conditions.");

            // Else do signup or do your stuff
        else{






            firebaseAuth.createUserWithEmailAndPassword(getEmailId, getPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //checking if success
                            if(task.isSuccessful()){

                                String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                UserInfo userInfo=new UserInfo(getFullName,getMobileNumber,getEmailId,getLocation,currentUserUid);

                                databaseReference.child(currentUserUid).setValue(userInfo);
                                ProfileBtmNav.setLl_userInfo();
                                Toast.makeText(getBaseContext(), "Sign Up done Successfully", Toast.LENGTH_SHORT)
                                        .show();
                                ShowTimeAndSeatsSelectionLayout.setUserId(userUid);

                                onBackPressed();
                               // finish();

                                //display some message here
                            }else{

                                Toast.makeText(getBaseContext(), "Email Id already registered", Toast.LENGTH_SHORT)
                                        .show();

                                //display some message here
                            }

                        }
                    });






        }

    }


}