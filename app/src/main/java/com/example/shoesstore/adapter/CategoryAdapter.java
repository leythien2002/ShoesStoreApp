package com.example.shoesstore.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoesstore.R;
import com.example.shoesstore.models.Category;

import java.util.List;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
   private Context context;
   private List<Category> listCat;

    public CategoryAdapter(Context context, List<Category> listCat) {
        this.context = context;
        this.listCat = listCat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(listCat.get(position).getImageUrl()).into(holder.catImg);
        holder.catName.setText(listCat.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listCat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catImg;
        TextView catName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImg=itemView.findViewById(R.id.cat_img);
            catName=itemView.findViewById(R.id.cat_name);
        }
    }
}
