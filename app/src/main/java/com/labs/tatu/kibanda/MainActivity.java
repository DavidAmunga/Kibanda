package com.labs.tatu.kibanda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.labs.tatu.kibanda.admin.HomeAdmin;
import com.labs.tatu.kibanda.common.Common;
import com.labs.tatu.kibanda.model.User;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Button btnSignIn,btnSignUp;
    TextView txtSlogan;

    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        Typeface face=Typeface.createFromAsset(getAssets(),"nabila.ttf");
        txtSlogan.setTypeface(face);

//        Init Paper
        Paper.init(this);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(signIn);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(signIn);

            }
        });

//        Check Remember
        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        if (user != null && pwd != null) {
            if (!user.isEmpty() && !pwd.isEmpty()) {
                login(user, pwd);
            }
        }

    }

    private void login(final String phone, final String pwd) {


//        Init Firebase
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("User");

        final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setMessage("Please wait....");
        mDialog.setCancelable(false);
        mDialog.show();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Check If user does not exist in Database
                if (dataSnapshot.child(phone).exists()) {


                    //Get User Information
                    mDialog.dismiss();
                    User user = dataSnapshot.child(phone).getValue(User.class);
                    user.setPhone(phone);
                    if (user.getPassword().equals(pwd)) {
                        if (user.getName().equals("admin")) {
                            Intent homeIntent = new Intent(MainActivity.this, HomeAdmin.class);
//                                Create Variable to save current user
                            Common.currentUser = user;
                            startActivity(homeIntent);
                            finish();
                        } else {
                            Intent homeIntent = new Intent(MainActivity.this, Home.class);
//                                Create Variable to save current user
                            Common.currentUser = user;
                            startActivity(homeIntent);
                            finish();
                        }


                    } else {
                        Toast.makeText(MainActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mDialog.dismiss();
                    Toast.makeText(MainActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void initViews() {
        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        btnSignUp=(Button)findViewById(R.id.btnSignUp);
        txtSlogan=(TextView)findViewById(R.id.txtSlogan);


    }
}
