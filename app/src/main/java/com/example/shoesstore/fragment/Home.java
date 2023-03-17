package com.example.shoesstore.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoesstore.R;
import com.example.shoesstore.Signin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Fragment {

    private View mView;
    private ImageView imgAvatar;
    private TextView tvUserName, tvEmail;
    private Button btnSignOut;
    public static final int MY_REQUEST_CODE=10;

    public Home(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_home,container,false);
        initUI();
        showUserInformation();
        initListener();

        return mView;
    }

    @Override
    public void onPause() {
        super.onPause();
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported

                showUserInformation();
                // Do something with the result
            }
        });
    }

    private void initListener() {
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i=new Intent(getActivity(),Signin.class);
                startActivity(i);

            }
        });
    }

    private void initUI() {
        imgAvatar=mView.findViewById(R.id.imgAvatar);
        tvUserName=mView.findViewById(R.id.tvUserName);
        tvEmail=mView.findViewById(R.id.tvEmail);
        btnSignOut=mView.findViewById(R.id.btnSignOut);
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
}