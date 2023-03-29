package com.example.shoesstore.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shoesstore.fragment.Cart;
import com.example.shoesstore.fragment.Home;
import com.example.shoesstore.fragment.UserProfile;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Home();
            case 1:
                return new Cart();
            case 2:
                return new UserProfile();
            default:
                return new Home();

        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
