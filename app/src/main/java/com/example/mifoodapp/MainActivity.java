package com.example.mifoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton about = findViewById(R.id.aboutButton);
        ImageButton feedback =findViewById(R.id.feedbackButton);

        about.setOnClickListener(this);
        feedback.setOnClickListener(this);

        Button stall1 = findViewById(R.id.button1);
        Button stall2 = findViewById(R.id.button2);

        stall1.setOnClickListener(this);
        stall2.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button1:
                Intent i = new Intent(MainActivity.this, menu.class);
                MainActivity.this.startActivity(i);
                break;


            case R.id.button2:

                break;
            case R.id.aboutButton:
            case R.id.feedbackButton:

        }



    }
}






