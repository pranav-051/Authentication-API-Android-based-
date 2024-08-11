package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;

public class welcome_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        ActionBar actionBar = getSupportActionBar();
       actionBar.getElevation();
       actionBar.setTitle("             Welcome to DoGooder");
       actionBar.setElevation(5);
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#ff9817"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        // actionBar.setSubtitle("Design a custom Action Bar");

        //Open Login Activity
        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welcome_page.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Open Register Activity
        Button buttonRegister = findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(welcome_page.this, activity_Register.class);
            startActivity(intent);
        });
    }}

//    @Override
//    public boolean onCreateOptionsMenu( Menu menu ) {
//
//        getMenuInflater().inflate(R.menu.main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    // methods to control the operations that will
//    // happen when user clicks on the action buttons
//    @Override
//    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
//
//        switch (item.getItemId()){
////            case R.id.search:
////                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
////                break;
////            case R.id.refresh:
////                Toast.makeText(this, "Refresh Clicked", Toast.LENGTH_SHORT).show();
////                break;
////            case R.id.copy:
////                Toast.makeText(this, "Copy Clicked", Toast.LENGTH_SHORT).show();
////                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}