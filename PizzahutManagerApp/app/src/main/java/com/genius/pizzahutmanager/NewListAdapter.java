package com.genius.pizzahutmanager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
        import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewListAdapter extends BaseAdapter {
    List<NewList> items;
    LayoutInflater inflater;
    public NewListAdapter(Activity context, List<NewList> items) {
        super();
        this.items=items;
        this.inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // TODO Auto-generated constructor stub

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(convertView==null)
            view=inflater.inflate(R.layout.list_item_new,null);
            TextView imagePizza = (TextView) view.findViewById(R.id.pizza_count_tag);
            TextView imageApp = (TextView) view.findViewById(R.id.app_count_tag);
            TextView imagePasta = (TextView) view.findViewById(R.id.pasta_count_tag);
            TextView imageDes = (TextView) view.findViewById(R.id.des_count_tag);
            TextView imageBev = (TextView) view.findViewById(R.id.bev_count_tag);
            TextView txtDes = (TextView) view.findViewById(R.id.text_destination);
            TextView txtTime = (TextView) view.findViewById(R.id.text_time);

        if(items.get(position).getPizza()>0)
            imagePizza.setText(""+items.get(position).getPizza());
        else
            imagePizza.setVisibility(View.GONE);
        if(items.get(position).getPasta()>0)
            imagePasta.setText(""+items.get(position).getPasta());
        else
            imagePasta.setVisibility(View.GONE);
        if(items.get(position).getApp()>0)
            imageApp.setText(""+items.get(position).getApp());
        else
            imageApp.setVisibility(View.GONE);
        if(items.get(position).getDes()>0)
            imageDes.setText(""+items.get(position).getDes());
        else
            imageDes.setVisibility(View.GONE);
        if(items.get(position).getBev()>0)
            imageBev.setText(""+items.get(position).getBev());
        else
            imageBev.setVisibility(View.GONE);

        txtDes.setText(items.get(position).getDesname());
            txtTime.setText("Distance : "+items.get(position).getDistance());
        return view;
    }

    /*public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_item_new, null,true);



        //imagePizza.setImageResource(R.drawable.pizza);
        //imageApp.setImageResource(R.drawable.app);
        //imagePasta.setImageResource(R.drawable.pasta);
        //imageDes.setImageResource(R.drawable.dessert);
        //imageBev.setImageResource(R.drawable.bev);

/*
        LinearLayout lp=new LinearLayout(this);
        lp.addView(new  Button(this));
        lp.addView(new ImageButton(this));
//Now remove them
        lp.removeViewAt(0);  // and so on

        lp.removeViewAt(0);


        txtTime.setText("Distance : "+distance[position]+" km");
        return rowView;

    }*/
}