package com.example.shoesstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoesstore.R;
import com.example.shoesstore.models.ItemsCart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private Context context;
    private List<ItemsCart> listItems;
    private Double totalCost=0.0;
    SendTotalPrice sendData;

    public CartAdapter(Context context, List<ItemsCart> listItems,SendTotalPrice sendData) {
        this.context = context;
        this.listItems = listItems;
        this.sendData=sendData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_cart,parent,false);
        return new ViewHolder(view,sendData);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(listItems.get(position).getImgUrl()).into(holder.productImg);
        holder.productName.setText(listItems.get(position).getProductName());
        holder.productTotalPrice.setText("$ "+listItems.get(position).getTotalPrice());
        holder.quantity.setText(String.valueOf(listItems.get(position).getTotalQuantity()));
        //send Total Price in Cart
        totalCost+=listItems.get(position).getTotalPrice();
        sendData.setTotalPrice(totalCost);


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImg;
        TextView productName,quantity;
        TextView productTotalPrice;
        SendTotalPrice sendTotalPrice;

        public ViewHolder(@NonNull View itemView, SendTotalPrice sendData) {
            super(itemView);
            this.sendTotalPrice=sendData;
            productImg=itemView.findViewById(R.id.imgProduct);
            productName=itemView.findViewById(R.id.tvProductName);
            productTotalPrice=itemView.findViewById(R.id.tvProducTotalPrice);
            quantity=itemView.findViewById(R.id.tvQuantityProduct);
        }
    }
    public interface SendTotalPrice{
        void setTotalPrice(Double a);
    }
}
