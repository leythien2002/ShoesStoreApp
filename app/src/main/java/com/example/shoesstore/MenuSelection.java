package com.example.shoesstore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.shoesstore.adapter.MyViewPagerAdapter;
import com.example.shoesstore.fragment.Home;
import com.example.shoesstore.fragment.UserProfile;
import com.example.shoesstore.tranformer.ZoomOutTransformer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MenuSelection extends AppCompatActivity {

    private final int ID_HOME=1;
    private final int ID_CART=2;
    private final int ID_NOTIFICATION=3;
    private final int ID_ACCOUNT=4;

    public static final int MY_REQUEST_CODE=10;
    private ViewPager2 viewPager2;

    private MeowBottomNavigation bottomNavigation;

    //test fragment
    final private UserProfile userProfile=new UserProfile();
    //toolbar
    Toolbar toolbar;
    //notification badge
    private View menuView;
    private TextView badge;
    private RelativeLayout cartLayout;
    public static int total=0;
    //runnable support for counting items in cart
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_selection);
        viewPager2=findViewById(R.id.view_pager2);
        bottomNavigation=findViewById(R.id.bottomNav);
        //push view up when keyboard appears
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getQuantityInCart();
        initNavBar();
        initUI();



    }



    private void initUI() {

        MyViewPagerAdapter myViewPagerAdapter=new MyViewPagerAdapter(this);
        viewPager2.setAdapter(myViewPagerAdapter);

        //setTransformer (animation page)
        viewPager2.setPageTransformer(new ZoomOutTransformer());
        //disable swipe to change view (tat cai chuc nang vuot man hinh)
        viewPager2.setUserInputEnabled(false);
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
                        bottomNavigation.show(2,true);
                        break;
                    case 2:
                        bottomNavigation.show(4,true);
                        break;
                    default:
                        bottomNavigation.show(1,true);
                        break;
                }
            }
        });

        toolbar=findViewById(R.id.tb_menu);
        setSupportActionBar(toolbar);
//        mRunnable=new Runnable() {
//            @Override
//            public void run() {
//                if(badge==null){
//                    return;
//                }
//                getQuantityInCart();
//                if(total==0){
//                    badge.setVisibility(View.INVISIBLE);
//                }
//                else{
//                    badge.setVisibility(View.VISIBLE);
//                    badge.setText(String.valueOf(total));
//                }
//            }
//        };



//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_home); dung de add icon o dau toolbar


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       getMenuInflater().inflate(R.menu.home_menu,menu);
       menuView=menu.findItem(R.id.menu_cart).getActionView();
       badge=menuView.findViewById(R.id.badge);
       cartLayout=menuView.findViewById(R.id.cart_layout);

//       updateCartCount();

       MenuItem menuItem=menu.findItem(R.id.menu_cart);
       cartLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onOptionsItemSelected(menuItem);
           }
       });
        Toast.makeText(this, "dang hoat dong", Toast.LENGTH_SHORT).show();

       return true;

    }

    private void updateCartCount() {
        if(badge==null){
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getQuantityInCart();
//                if(total==0){
//                    badge.setVisibility(View.INVISIBLE);
//                }
//                else{
//                    badge.setVisibility(View.VISIBLE);
//                    badge.setText(String.valueOf(total));
//                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.menu_cart){
//            updateCartCount();
            startActivity(new Intent(MenuSelection.this,MyCart.class));

        }
        return true;
    }

    private void initNavBar() {
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME,R.drawable.ic_nav_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_CART,R.drawable.ic_nav_home));
//        bottomNavigation.add(new MeowBottomNavigation.Model(ID_NOTIFICATION,R.drawable.ic_nav_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT,R.drawable.ic_nav_home));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case ID_HOME:
                        break;
                    case ID_CART:
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
                        toolbar.setTitle("Home");
                        break;
                    case ID_CART:
                        viewPager2.setCurrentItem(1);
                        toolbar.setTitle("Cart");
                        break;
//                    case ID_NOTIFICATION:
//                        break;
                    case ID_ACCOUNT:
                        viewPager2.setCurrentItem(2);
                        toolbar.setTitle("Account");
                        break;
                }
                return null;
            }
        });
    }

    private void getQuantityInCart(){

        FirebaseUser mAuth= FirebaseAuth.getInstance().getCurrentUser();
        String id=mAuth.getUid();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Cart/"+id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                total= (int) snapshot.getChildrenCount();
                if(total==0){
                    badge.setVisibility(View.INVISIBLE);
                }
                else{
                    badge.setVisibility(View.VISIBLE);
                    badge.setText(String.valueOf(total));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
//        updateCartCount();
        super.onStart();
    }
    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==MY_REQUEST_CODE){
//            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                openGallery();
//            }
//        }
//    }
}