package com.example.mycloudapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mycloudapplication.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    //view binding
    private ActivityProfileBinding binding;

    //actionbar
    private ActionBar actionBar;

    // firebase auth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // configure action bar
        actionBar = getSupportActionBar();
        actionBar.setTitle("Log in");

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        // loggour user by clicking
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
    }

    private void checkUser() {
        // check if user is not logged in then move to loggin activity
        // get current user
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null)
        {
            // user not logged in
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        else
        {
            // user logged in get info
            String email = firebaseUser.getEmail();
            // set to email tv
            binding.emailTv.setText(email);
        }
    }
}