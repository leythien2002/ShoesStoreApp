package com.example.shoesstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoesstore.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class DetailedProduct extends AppCompatActivity {
    ImageView imgDetail,imgPlus,imgMinus;
    TextView name,description,price,quantity;
    RatingBar rating;
    Button btnAddToCart;
    Product product=null;
    int totalQuantity=1;
    Double currProductPrice;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_product);

        initUI();
        initListener();
        showDetailProduct();

    }

    private void initUI(){
        imgDetail=findViewById(R.id.imgProduct);
        rating=findViewById(R.id.rbRating);
        name=findViewById(R.id.tvProductName);
        description=findViewById(R.id.tvContextOfDescription);
        price=findViewById(R.id.tvTotalPrice);
        quantity=findViewById(R.id.tvQuantityProduct);
        //button
        btnAddToCart=findViewById(R.id.btnAddToCart);
        imgPlus=findViewById(R.id.imgPlus);
        imgMinus=findViewById(R.id.imgMinus);
        //toolbar
        toolbar=findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    private void initListener(){
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    if(product!=null){
                        price.setText("$ "+String.valueOf(currProductPrice*totalQuantity));
                    }

                }

            }
        });
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    if(product!=null) {
                        price.setText("$ " + String.valueOf(currProductPrice * totalQuantity));
                    }
                }
            }
        });
    }

    private void addToCart() {
        List<Product> list;
        String saveCurrentTime,saveCurrentDate;
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());


        HashMap<String,Object> cartMap=new HashMap<>();

        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",currProductPrice);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",price.getText().toString());
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("currentTime",saveCurrentTime);
        FirebaseUser mAuth=FirebaseAuth.getInstance().getCurrentUser();
        String id=mAuth.getUid();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Cart/"+id);
        databaseReference.setValue(cartMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(DetailedProduct.this,"Add Completed",Toast.LENGTH_LONG).show();
            }
        });


    }

    private void showDetailProduct(){
        Object obj=getIntent().getSerializableExtra("detail");
        if(obj instanceof Product){
            product= (Product) obj;
        }
        if(product!=null){
            Glide.with(getApplicationContext()).load(product.getImageUrl()).into(imgDetail);
            name.setText(product.getName());
            description.setText(product.getDescription());
            price.setText("$ "+String.valueOf(product.getPrice()));
            //get current Product value--> to calculate total Price
            currProductPrice=product.getPrice();
        }
    }
    //handle back event on toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}