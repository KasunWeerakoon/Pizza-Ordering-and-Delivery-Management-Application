package com.genious.pizza.pizzaorder;

/**
 * Created by Kasun on 5/25/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public CartAdapter(){
    }
    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.price.setText(Cart.sPrice.get(position));
        holder.itemName.setText(Cart.sItemName.get(position));
        holder.qty.setText(Cart.sQty.get(position));
        holder.img.setBackgroundResource(Integer.parseInt(Cart.sImageID.get(position)));
    }

    @Override
    public int getItemCount() {
        return Cart.sItemName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView price;
        protected ImageView img;
        protected TextView qty;
        protected TextView itemName;


        public ViewHolder(View itemView) {
            super(itemView);
            price =  (TextView) itemView.findViewById(R.id.mPrice);
            img=(ImageView)itemView.findViewById(R.id.imageView);
            itemName=(TextView)itemView.findViewById(R.id.pNameTxt);
            qty=(TextView)itemView.findViewById(R.id.mQty);

        }


    }
}
