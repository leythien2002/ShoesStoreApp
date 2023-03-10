package com.example.shoesstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.shoesstore.adapter.MyViewPagerAdapter;
import com.example.shoesstore.tranformer.ZoomOutTransformer;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MenuSelection extends AppCompatActivity {

    private final int ID_HOME=1;
    private final int ID_MESSAGE=2;
    private final int ID_NOTIFICATION=3;
    private final int ID_ACCOUNT=4;

    private ViewPager2 viewPager2;

    private MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_selection);
        viewPager2=findViewById(R.id.view_pager2);
        bottomNavigation=findViewById(R.id.bottomNav);
        initNavBar();
        initUI();


    }

    private void initUI() {

        MyViewPagerAdapter myViewPagerAdapter=new MyViewPagerAdapter(this);
        viewPager2.setAdapter(myViewPagerAdapter);

        //setTransformer (animation page)
        viewPager2.setPageTransformer(new ZoomOutTransformer());
        //show icon on nav when transform page
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigation.show(1,true);
                        break;
                    case 1:
                        bottomNavigation.show(4,true);
                        break;
                    default:
                        bottomNavigation.show(1,true);
                        break;
                }
            }
        });
    }

    private void initNavBar() {
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME,R.drawable.ic_nav_home));
//        bottomNavigation.add(new MeowBottomNavigation.Model(ID_MESSAGE,R.drawable.ic_nav_home));
//        bottomNavigation.add(new MeowBottomNavigation.Model(ID_NOTIFICATION,R.drawable.ic_nav_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT,R.drawable.ic_nav_home));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case ID_HOME:
                        break;
//                    case ID_MESSAGE:
//                        break;
//                    case ID_NOTIFICATION:
//                        break;
                    case ID_ACCOUNT:
                        break;
                }
                return null;
            }
        });
        //show information view on ViewPager
        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case ID_HOME:
                        viewPager2.setCurrentItem(0);
                        break;
//                    case ID_MESSAGE:
//                        break;
//                    case ID_NOTIFICATION:
//                        break;
                    case ID_ACCOUNT:
                        viewPager2.setCurrentItem(1);
                        break;
                }
                return null;
            }
        });
    }
}