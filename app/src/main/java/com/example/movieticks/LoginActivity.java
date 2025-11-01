package com.example.movieticks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    String userUid;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    private static View view;
   private static boolean userLogInOrNot=false;
    private static CheckBox show_hide_password;

     EditText login_emailid;
    EditText et_password;
    TextView tv_create_account;

    Button btn_login;
    LinearLayout loginLayout;

    private static Animation shakeAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            userLogInOrNot=true;
            ShowTimeAndSeatsSelectionLayout.setUserLogInOrNot(userLogInOrNot);
            userUid=FirebaseAuth.getInstance().getCurrentUser().getUid();
            ShowTimeAndSeatsSelectionLayout.setUserId(userUid);
            finish();
        }


        shakeAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.shake);
        view = getLayoutInflater().inflate(R.layout.activity_login, null);



        login_emailid = findViewById(R.id.login_emailid);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tv_create_account=findViewById(R.id.tv_create_account);
        show_hide_password =findViewById(R.id.show_hide_password);
        loginLayout = findViewById(R.id.login_layout);



        tv_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, CreateAccount.class);
                startActivity(i);
                finish();
            }
        });


        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            et_password.setInputType(InputType.TYPE_CLASS_TEXT);
                            et_password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            et_password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            et_password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });




        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                if(login_emailid.getText().toString().isEmpty()||et_password.getText().toString().isEmpty())
                    Toast.makeText(LoginActivity.this,"Pls fill all details",Toast.LENGTH_SHORT).show();
                else
                {



                    firebaseAuth.signInWithEmailAndPassword(login_emailid.getText().toString(), et_password.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        userLogInOrNot=true;
                                        ShowTimeAndSeatsSelectionLayout.setUserLogInOrNot(userLogInOrNot);
                                        Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_SHORT).show();
                                        ShowTimeAndSeatsSelectionLayout.setUserId(id);
                                        finish();

                                    } else {
                                        Toast.makeText(LoginActivity.this,"User not exist",Toast.LENGTH_SHORT).show();


                                    }
                                }
                            });


                }*/
            checkValidation();
            }
        });



    }



    private void checkValidation() {
        // Get email id and password
        String getEmailId = login_emailid.getText().toString();
        String getPassword = et_password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Utils.regEx);

        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getBaseContext(), view,
                    "Enter both credentials.");

        }
        // Check if email id is valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getBaseContext(), view, "Your Email Id is Invalid.");

            // Else do login and do your stuff
        else
        {

            firebaseAuth.signInWithEmailAndPassword(login_emailid.getText().toString(), et_password.getText().toString())
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userLogInOrNot=true;
                                ShowTimeAndSeatsSelectionLayout.setUserLogInOrNot(userLogInOrNot);
                                userUid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                ShowTimeAndSeatsSelectionLayout.setUserId(userUid);
                                ProfileBtmNav.setLl_userInfo();
                                Toast.makeText(LoginActivity.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();

                                finish();

                            } else {
                                Toast.makeText(LoginActivity.this,"Pls Check Email Id and Password",Toast.LENGTH_SHORT).show();


                            }
                        }
                    });

        }

    }


}
