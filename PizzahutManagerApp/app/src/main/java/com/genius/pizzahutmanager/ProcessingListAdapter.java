package com.genius.pizzahutmanager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RaminduS on 5/25/2015.
 */
public class ProcessingListAdapter extends BaseAdapter {
    List<ProcessingItem> items;
    LayoutInflater inflater;
    public ProcessingListAdapter(Activity context, List<ProcessingItem> items) {
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
            view=inflater.inflate(R.layout.list_item_processing,null);
        TextView pcname = (TextView) view.findViewById(R.id.p_c_name);
        TextView pclocation = (TextView) view.findViewById(R.id.p_c_location);
        TextView pridername = (TextView) view.findViewById(R.id.p_r_name);

        pcname.setText(items.get(position).cname);
        pclocation.setText(items.get(position).location);
        pridername.setText(items.get(position).riderName);
        return view;

    }

}