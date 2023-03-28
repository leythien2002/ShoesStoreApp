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
import com.example.shoesstore.models.Product;

import java.util.List;

public class ShowAllProductAdapter extends RecyclerView.Adapter<ShowAllProductAdapter.ViewHolder> {
    private Context context;
    private List<Product> listProduct;

    public ShowAllProductAdapter(Context context, List<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(listProduct.get(position).getImageUrl()).into(holder.productImg);
        holder.productName.setText(listProduct.get(position).getName());
        holder.productPrice.setText("$ "+listProduct.get(position).getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImg;
        TextView productName;
        TextView productPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg=itemView.findViewById(R.id.imgProduct);
            productName=itemView.findViewById(R.id.tvProductName);
            productPrice=itemView.findViewById(R.id.tvProducPrice);
        }
    }
}
