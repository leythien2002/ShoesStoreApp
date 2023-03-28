package com.example.shoesstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoesstore.models.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailedProduct extends AppCompatActivity {
    ImageView imgDetail,imgPlus,imgMinus;
    TextView name,description,price;
    RatingBar rating;
    Button btnAddToCart;
    Product product=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_product);

        initUI();
        showDetailProduct();

    }
    private void initUI(){
        imgDetail=findViewById(R.id.imgProduct);

        rating=findViewById(R.id.rbRating);
        name=findViewById(R.id.tvProductName);
        description=findViewById(R.id.tvContextOfDescription);
        price=findViewById(R.id.tvTotalPrice);
        //button
        btnAddToCart=findViewById(R.id.btnAddToCart);
        imgPlus=findViewById(R.id.imgPlus);
        imgMinus=findViewById(R.id.imgMinus);

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
        }
    }
}