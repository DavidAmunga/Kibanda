package com.labs.tatu.kibanda.common;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by amush on 12-Oct-17.
 */

public class Persist extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
