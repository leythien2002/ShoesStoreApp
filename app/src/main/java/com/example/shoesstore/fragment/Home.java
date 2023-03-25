package com.example.shoesstore.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoesstore.R;
import com.example.shoesstore.Signin;
import com.example.shoesstore.adapter.CategoryAdapter;
import com.example.shoesstore.adapter.ImageSlideHomeAdapter;
import com.example.shoesstore.models.Category;
import com.example.shoesstore.models.Photo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class Home extends Fragment {

    private View mView;
    private ImageView imgAvatar;
    private TextView tvUserName, tvEmail;
    private Button btnSignOut;
    //Home
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private List<Photo> mListPhoto;
    private Handler mHandler=new Handler();
    private Runnable mRunnable;
    //Category recyclerview
    RecyclerView catRecyclerview;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;



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
        controlImageSlide();
        showCategory();

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
        //Store position for resume
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable,2000);
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
        //slideNewImages
        mRunnable=new Runnable() {
            @Override
            public void run() {
                if(mViewPager2.getCurrentItem()==mListPhoto.size()-1){
                    mViewPager2.setCurrentItem(0);
                }
                else{
                    mViewPager2.setCurrentItem(mViewPager2.getCurrentItem()+1);
                }
            }
        };
        mViewPager2=mView.findViewById(R.id.view_pager2);
        mCircleIndicator3=mView.findViewById(R.id.circle_indicator3);
        mListPhoto=getListPhoto();
        ImageSlideHomeAdapter adapter=new ImageSlideHomeAdapter(mListPhoto);
        mViewPager2.setAdapter(adapter);
        mCircleIndicator3.setViewPager(mViewPager2);
        //CatImage
        catRecyclerview=mView.findViewById(R.id.rec_category);
        catRecyclerview.setHasFixedSize(true);
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList=new ArrayList<>(); //nguon
        categoryAdapter=new CategoryAdapter(getContext(),categoryList); //set nguon va man hinh hien thi cho adapter
        catRecyclerview.setAdapter(categoryAdapter);


    }

    private void controlImageSlide(){
        //control image Slider
        mHandler.postDelayed(mRunnable,2000);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable,2000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
    private List<Photo> getListPhoto(){
        List<Photo> list =new ArrayList<>();
        list.add(new Photo(R.drawable.discount1));
        list.add(new Photo(R.drawable.discount2));
        list.add(new Photo(R.drawable.discount3));
        return list;
    }
    private void showCategory(){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("CatImages");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Category category=dataSnapshot.getValue(Category.class);
                    categoryList.add(category);
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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