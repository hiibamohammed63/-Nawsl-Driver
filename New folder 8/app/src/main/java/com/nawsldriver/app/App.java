package com.nawsldriver.app;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.nawsldriver.auth.SignIn;
import com.nawsldriver.configuration.StartApp;
import com.nawsldriver.database.AppDatabase;
import com.nawsldriver.database.RWServices;
import com.nawsldriver.home.Home;

public class App extends AppCompatActivity {
    private String TAG = StartApp.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser != null) {
            // Get User Details From Application Database
            AppDatabase appDatabase = AppDatabase.getDatabase(this);
            RWServices rwServices = new RWServices(appDatabase);
            rwServices.getUserDetails();

            Log.i(TAG, "Signing In User");

            // Go To Home Screen
            Intent intent = new Intent(App.this, Home.class);
            startActivity(intent);
            finish();
        } else{
            // Go To Sign In
            Intent intent = new Intent(App.this, SignIn.class);
            startActivity(intent);
            finish();
        }
    }
}
