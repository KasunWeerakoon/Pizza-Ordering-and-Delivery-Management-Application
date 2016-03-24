package com.genius.pizzahutmanager;

        import java.util.List;

        import android.app.Activity;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;


public class CustomRiderListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<RiderDetails> riderItems;


    public CustomRiderListAdapter(Activity activity, List<RiderDetails> items) {
        this.activity = activity;
        this.riderItems = items;
    }

    @Override
    public int getCount() {
        return riderItems.size();
    }

    @Override
    public Object getItem(int location) {
        return riderItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.rider_list_row, null);


        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView nic = (TextView) convertView.findViewById(R.id.nic);
        TextView phone = (TextView) convertView.findViewById(R.id.phone);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView delivery = (TextView) convertView.findViewById(R.id.delivery);


        // getting rider data for the row
        final RiderDetails m = riderItems.get(position);

        name.setText(m.getRiderName());
        nic.setText(m.getRiderNic());
        phone.setText(m.getRiderPhone());
        rating.setText(m.getRiderRating()+" ratings");
        delivery.setText(m.getRiderDelivery()+" deliveries");

        return convertView;
    }


}