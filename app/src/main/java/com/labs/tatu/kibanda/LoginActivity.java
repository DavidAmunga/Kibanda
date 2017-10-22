package com.labs.tatu.kibanda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.labs.tatu.kibanda.admin.HomeAdmin;
import com.labs.tatu.kibanda.common.Common;
import com.labs.tatu.kibanda.model.User;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    MaterialEditText edtPhone, edtPassword;
    Button signIn;

    DatabaseReference mDatabase;
    CheckBox ckbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();


//        Initialize Firebase
        mDatabase= FirebaseDatabase.getInstance().getReference("User");
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isConnectedToInternet(getBaseContext())) {

//                    Save user and password
                    if (ckbRemember.isChecked()) {
                        Paper.book().write(Common.USER_KEY, edtPhone.getText().toString());
                        Paper.book().write(Common.PWD_KEY, edtPassword.getText().toString());
                    }
                    final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                    mDialog.setMessage("Please wait....");
                    mDialog.setCancelable(false);
                    mDialog.show();

                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Check If user does not exist in Database
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {


                                //Get User Information
                                mDialog.dismiss();
                                User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                user.setPhone(edtPhone.getText().toString());
                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    if (user.getName().equals("admin")) {
                                        Intent homeIntent = new Intent(LoginActivity.this, HomeAdmin.class);
//                                Create Variable to save current user
                                        Common.currentUser = user;
                                        startActivity(homeIntent);
                                        finish();
                                    } else {
                                        Intent homeIntent = new Intent(LoginActivity.this, Home.class);
//                                Create Variable to save current user
                                        Common.currentUser = user;
                                        startActivity(homeIntent);
                                        finish();
                                    }


                                } else {
                                    Toast.makeText(LoginActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Please check your connection !!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    private void initViews() {
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        signIn=(Button)findViewById(R.id.btnSignIn);
        ckbRemember = (CheckBox) findViewById(R.id.ckbRemember);
    }
}
