package com.example.shoesstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoesstore.DetailedProduct;
import com.example.shoesstore.R;
import com.example.shoesstore.models.ItemsCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private Context context;
    private List<ItemsCart> listItems;
    private Double totalCost=0.0;
    private FirebaseUser mAuth;
    private DatabaseReference databaseReference;
//    SendTotalPrice sendData;

    public CartAdapter(Context context, List<ItemsCart> listItems) {
        this.context = context;
        this.listItems = listItems;
//        this.sendData=sendData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(listItems.get(position).getImgUrl()).into(holder.productImg);
        holder.productName.setText(listItems.get(position).getProductName());
        holder.productTotalPrice.setText("$ "+listItems.get(position).getTotalPrice());
        holder.quantity.setText(String.valueOf(listItems.get(position).getTotalQuantity()));
        //send Total Price in Cart
//        totalCost+=listItems.get(position).getTotalPrice();
//        sendData.setTotalPrice(totalCost);
        int index=holder.getAdapterPosition();
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEventPlusAndMinus(0,index);

            }
        });
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEventPlusAndMinus(1,index);

            }
        });



    }

    private void handleEventPlusAndMinus(int a,int position){
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        String id = mAuth.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart/" + id);
        HashMap<String, Object> cartMap = new HashMap<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String idProduct = data.getKey();//id of product on firebase
                    if (idProduct.equals(String.valueOf(listItems.get(position).getId()))) {
                        int number = data.child("totalQuantity").getValue(Integer.class);
                        if(a==0){
                            if(number>1){
                                number--;
                                sendTotalCartToCartActivivty(0);
                            }
                        }
                        else{
                            //how to determine the limit of number when increased ?
                            number++;
                            sendTotalCartToCartActivivty(1);
                        }
                        Double totalCost = Double.valueOf(number * listItems.get(position).getProductPrice());
                        cartMap.put("totalQuantity", number);
                        cartMap.put("totalPrice", totalCost);
                        databaseReference.child(idProduct).updateChildren(cartMap);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void sendTotalCartToCartActivivty(int a){
        Intent i=new Intent("send_totalPrice");
        Bundle bundle=new Bundle();
        bundle.putInt("checkChange",a);
        i.putExtras(bundle);
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);

    }
    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImg;
        TextView productName,quantity;
        TextView productTotalPrice;
        ImageView btnMinus,btnPlus;
//        SendTotalPrice sendTotalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            this.sendTotalPrice=sendData;
            productImg=itemView.findViewById(R.id.imgProduct);
            productName=itemView.findViewById(R.id.tvProductName);
            productTotalPrice=itemView.findViewById(R.id.tvProducTotalPrice);
            quantity=itemView.findViewById(R.id.tvQuantityProduct);
            btnMinus=itemView.findViewById(R.id.imgMinus);
            btnPlus=itemView.findViewById(R.id.imgPlus);
        }
    }
//    public interface SendTotalPrice{
//        void setTotalPrice(Double a);
//    }

}
