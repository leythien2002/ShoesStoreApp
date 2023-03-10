package com.example.shoesstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Signin extends AppCompatActivity {

    private TextView tvSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initUI();
        initListener();
    }



    private void initUI() {
        tvSignUp=findViewById(R.id.tvSignUp);
    }
    private void initListener() {
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Signin.this,Signup.class);
                startActivity(i);

            }
        });
    }


}