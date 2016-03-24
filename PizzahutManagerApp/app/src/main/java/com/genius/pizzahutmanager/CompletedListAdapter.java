package com.genius.pizzahutmanager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RaminduS on 5/25/2015.
 */
public class CompletedListAdapter extends BaseAdapter {
    List<CompletedItem> items;
    LayoutInflater inflater;
    public CompletedListAdapter(Activity context, List<CompletedItem> items) {
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
            view=inflater.inflate(R.layout.list_item_completed,null);
        TextView ccname = (TextView) view.findViewById(R.id.c_c_name);
        TextView cclocation = (TextView) view.findViewById(R.id.c_c_location);
        TextView ccphone = (TextView) view.findViewById(R.id.c_c_phone);
        TextView crname = (TextView) view.findViewById(R.id.c_r_name);
        TextView crrating = (TextView) view.findViewById(R.id.c_r_rating);

        ccname.setText(items.get(position).name);
        cclocation.setText(items.get(position).location);
        ccphone.setText(items.get(position).phone.toString());
        crname.setText(items.get(position).riderName);
        crrating.setText(items.get(position).rating.toString());

        return view;
    }

}