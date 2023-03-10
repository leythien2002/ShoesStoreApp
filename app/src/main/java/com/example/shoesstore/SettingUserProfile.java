package com.example.shoesstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingUserProfile extends AppCompatActivity {

    private EditText edEmail,edUserName,edPassword;
    private ImageView imgAvatar;
    private Button btnUpdateProfile;
    public static final int MY_REQUEST_CODE=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_user_profile);

        initUI();
        setUserInfomation();
        initListener();
    }



    private void initUI() {
        edEmail=findViewById(R.id.edEmail);
        edUserName=findViewById(R.id.edUserName);
        edPassword=findViewById(R.id.edPassword);
        imgAvatar=findViewById(R.id.imgAvatar);
        btnUpdateProfile=findViewById(R.id.btnUpdateProfile);
    }
    private void setUserInfomation() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        edEmail.setText(user.getEmail());
        edUserName.setText(user.getDisplayName());
        Glide.with(this).load(user.getPhotoUrl()).error(R.drawable.ic_light_user).into(imgAvatar);
    }
    private void initListener() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();

            }
        });
    }

    private void onClickRequestPermission() {


        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            openGallery();
            return;
        }

        if(SettingUserProfile.this.checkSelfPermission(Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION)
        ==PackageManager.PERMISSION_GRANTED){
            openGallery();
        }
        else{
            String [] permissions={Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION};
            SettingUserProfile.this.requestPermissions(permissions,MY_REQUEST_CODE);
        }
    }

    private void openGallery() {
    }

}