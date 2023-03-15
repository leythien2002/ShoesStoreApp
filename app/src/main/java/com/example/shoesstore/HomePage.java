package com.example.shoesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    private ImageView imgAvatar;
    private TextView tvUserName, tvEmail;
    private Button btnSignOut;
    public static final int MY_REQUEST_CODE=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initUI();
        showUserInformation();
        initListener();
    }

    private void initListener() {
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i=new Intent(HomePage.this,Signin.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void initUI() {
        imgAvatar=findViewById(R.id.imgAvatar);
        tvUserName=findViewById(R.id.tvUserName);
        tvEmail=findViewById(R.id.tvEmail);
        btnSignOut=findViewById(R.id.btnSignOut);
    }
    public void showUserInformation(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        else{
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            if(name==null){
                tvUserName.setVisibility(View.GONE);
            }
            else {
                tvUserName.setVisibility(View.VISIBLE);
                tvUserName.setText(name);
            }

            tvEmail.setText(email);
            Glide.with(this).load(photoUrl).error(R.drawable.ic_light_user).into(imgAvatar);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_REQUEST_CODE){
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void openGallery() {
    }
}