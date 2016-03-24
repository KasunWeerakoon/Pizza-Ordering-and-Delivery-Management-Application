package com.genious.pizza.pizzaorder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Kasun on 5/21/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<String> dataSource=new ArrayList<String>();
    Context contextA;
    int[] imageList;
    public RecyclerAdapter(int[] scoreList,String cases){



        switch (cases) {
            case "panPizza": {
                imageList=scoreList;
                Log.v("ImageId",""+imageList[1]);
                Log.v("ImageId",""+imageList.length);

            }

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_category_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       // holder.textView.setText(dataSource.get(position));
  holder.imageView.setBackgroundResource(imageList[position]);
//        holder.imageView.setBackgroundResource(imageList[2]);
//        holder.imageView.setBackgroundResource(imageList[3]);
//        Log.v("ImageId",""+imageList[position]);
        //Log.v("ImageId",""+imageList[1]);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context cont=v.getContext();
                Toast.makeText(v.getContext(),"",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(v.getContext(),ListGenerator.class);
                intent.putExtra("Type","panPizza");
                cont.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return imageList.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected ImageView imageView;
        protected CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView =  (ImageView) itemView.findViewById(R.id.imageItem);

           // cardView=(CardView)itemView.findViewById(R.id.card_view);
        }


    }
}
