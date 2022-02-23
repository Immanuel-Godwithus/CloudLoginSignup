package com.example.mycloudapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.mycloudapplication.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    // actionbar
    private ActionBar actionBar;

    // progress dialogue
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private String email = "" , password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // configure action bar
        actionBar = getSupportActionBar();
        actionBar.setTitle("Log in");

        // configure progress dialogue
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Logging in");
        progressDialog.setCanceledOnTouchOutside(false);

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        // if have an account click to go to signup
        binding.haveAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , SignUpActivity.class));
            }
        });

        binding.LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate data
                valiadateData();
            }
        });
    }

    private void checkUser() {
        // check if user is already logged in
        // if already logged in open profile activity

        // get current user
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); //**
        if (firebaseUser != null)
        {
            // user is already logged in
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        }
    }

    private void valiadateData() {
        // get data
        email = binding.emailEt.getText().toString().trim();
        password = binding.passwordEt.getText().toString().trim();

        // validate data
        // validate data
        if (TextUtils.isEmpty(password))
        {
            // no password is entered
            binding.passwordEt.setError("Enter Password");
        }
        else
        {
            // data is valid so continue with firebase login
            firebaseLogin();
        }
    }

    private void firebaseLogin()
    {
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // login success
                        // get user info
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String email = firebaseUser.getEmail();
                        Toast.makeText(MainActivity.this, "Logged in\n"+email,Toast.LENGTH_SHORT).show();
                        // open profile activity
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // login failed, get and show error message
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}