package com.genius.pizzahutmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RaminduS on 5/24/2015.
 */
public class SingleOrderActivity extends Activity{
    ProgressDialog mProgressDialog;
    Spinner spinner;
    List<ParseObject> parse_item_list;
    List<ParseObject> parse_rider_list;

    ArrayList<OrderItem> orderItemList;
    ArrayList<Rider> riderlist;
    ArrayList<String> riderStringList;

    OrderItemListAdapter orderListAdapter;
    String cname;
    Integer cphone;
    ListView single_order_list_view;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_order);
        spinner= (Spinner) findViewById(R.id.spinner);
        orderItemList=new ArrayList<OrderItem>();
        riderlist=new ArrayList<Rider>();
        riderStringList=new ArrayList<String>();
        new RemoteDataTask().execute();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void fillTitles(String name,Integer phone){
        TextView cname= (TextView) findViewById(R.id.cname);
        TextView cphone= (TextView) findViewById(R.id.cphone);
        TextView caddress= (TextView) findViewById(R.id.caddress);

        cname.setText(name.toString());
        cphone.setText(phone.toString());
        caddress.setText(MainActivity.selectedOrder.getDesname().toString());
    }
    public void fillBody(){


        orderListAdapter=new OrderItemListAdapter(this,orderItemList);
        single_order_list_view=(ListView)findViewById(R.id.single_order);
        single_order_list_view.setAdapter(orderListAdapter);
    }
    public void orderComplete(View view){

        new SaveProcessingTask().execute();
    }
    public void goBack(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void fillspinner(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, riderStringList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        if(riderStringList.size()==0){
            new AlertDialog.Builder(this)
                    .setTitle("Unable to Accept Order")
                    .setMessage("Riders are unavailable. Wait untill riders are getting back.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            goBack();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        int logged=0;
        ParseObject item;
        int branchId;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SingleOrderActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Loading Order Details");
            // Set progressdialog message
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            try {


                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Customer").whereEqualTo("NIC",MainActivity.selectedOrder.getNic());
                // Locate the column named "ranknum" in Parse.com and order list
                // by ascending
                item=query.getFirst();
//                Toast.makeText(getBaseContext(),(String) item.get("Name"),Toast.LENGTH_SHORT).show();
                cname=(String) item.get("Name");
                cphone=(Integer)item.get("Phone");

                ParseQuery<ParseObject> array = new ParseQuery<ParseObject>(
                        "OrderItem").whereEqualTo("OrderID",MainActivity.selectedOrder.getOrderId());
                // Locate the column named "ranknum" in Parse.com and order list
                // by ascending
                count=array.count();
                query.orderByAscending("createdAt");
                parse_item_list = array.find();
                for (ParseObject newItem : parse_item_list) {
                    // Locate images in flag column
                    String newItemName= (String) newItem.get("ItemName");
                    Integer newItemQuantity= (Integer) newItem.get("quantity");
                    String newItemAdditional=(String) newItem.get("Additional");

                    OrderItem item = new OrderItem(
                            newItemName,
                            newItemQuantity,
                            newItemAdditional);
                    orderItemList.add(item);
                }
                ParseQuery<ParseObject> riders = new ParseQuery<ParseObject>(
                        "Rider").whereEqualTo("Status","in").whereEqualTo("ShopID",MainActivity.branchId);
                // Locate the column named "ranknum" in Parse.com and order list
                // by ascending
                riders.orderByAscending("createdAt");
                parse_rider_list = riders.find();
                for (ParseObject newItem : parse_rider_list) {
                    // Locate images in flag column
                    String riderName= (String) newItem.get("Name");
                    String riderId= (String) newItem.get("RiderID");
                    String riderPhone= (String) newItem.get("Phone");
                    Double riderRatings= (Double) newItem.get("ratings");
                    Integer riderDel= (Integer) newItem.get("deliveries");

                    Rider rider = new Rider();
                            rider.riderName=riderName;
                            rider.riderId=riderId;
                            rider.phone=riderPhone;
                            rider.ratings=riderRatings;
                            rider.del=riderDel;
                    riderlist.add(rider);
                    riderStringList.add(riderName);
                }

            } catch (ParseException e) {
                logged=0;
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //button.setEnabled(true);
            fillTitles(cname,cphone);
            fillBody();
            fillspinner();
            showArraysize();
            mProgressDialog.dismiss();
        }
    }
    private class SaveProcessingTask extends AsyncTask<Void, Void, Void> {
        int logged=0;
        ParseObject item;
        int branchId;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SingleOrderActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Assigning Rider");
            // Set progressdialog message
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array

            ParseObject newPro = new ParseObject("ProcessingOrders");

            newPro.put("latitude", MainActivity.selectedOrder.getLatitude());
            newPro.put("longitude", MainActivity.selectedOrder.getLongitude());
            newPro.put("ShopID", MainActivity.branchId);
            newPro.put("RiderID", riderlist.get(spinner.getSelectedItemPosition()).riderId);
            newPro.put("RiderName", riderlist.get(spinner.getSelectedItemPosition()).riderName);
            newPro.put("Address", MainActivity.selectedOrder.getDesname());
            newPro.put("CustomerName",cname);
            newPro.put("CustomerPhone", cphone.toString());
            newPro.put("OrderID",MainActivity.selectedOrder.getOrderId());
            newPro.put("pizza",MainActivity.selectedOrder.getPizza());
            newPro.put("app",MainActivity.selectedOrder.getApp());
            newPro.put("pasta",MainActivity.selectedOrder.getPasta());
            newPro.put("des",MainActivity.selectedOrder.getDes());
            newPro.put("bev",MainActivity.selectedOrder.getBev());
            try {
                newPro.save();
            } catch (ParseException e) {
                e.printStackTrace();
            }


            ParseQuery<ParseObject> riderquery = ParseQuery.getQuery("Rider");
            try {
                ParseObject riderO=riderquery.get(riderlist.get(spinner.getSelectedItemPosition()).riderId);
                riderO.put("Status","out");
                riderO.save();
            } catch (ParseException e) {
                e.printStackTrace();
            }

                    /*
            InBackground(riderlist.get(spinner.getSelectedItemPosition()).riderId, new GetCallback<ParseObject>() {
                public void done(ParseObject rider, ParseException e) {
                    if (e == null) {
                        // Now let's update it with some new data. In this case, only cheatMode and score
                        // will get sent to the Parse Cloud. playerName hasn't changed.
                        rider.put("Status","out");
                        rider.saveInBackground();
                    }
                    else{
                        errorinrider();
                    }
                }
            });*/


            ParseQuery<ParseObject> deleteOrder = ParseQuery.getQuery("NewOrders");
            try {
                ParseObject dlt=deleteOrder.get(MainActivity.selectedOrder.getOrderId());
                dlt.delete();
            } catch (ParseException e) {
                e.printStackTrace();
            }


            /*InBackground(MainActivity.selectedOrder.getOrderId(), new GetCallback<ParseObject>() {
                public void done(ParseObject dlt, ParseException e) {
                    if (e == null) {
                        // Now let's update it with some new data. In this case, only cheatMode and score
                        // will get sent to the Parse Cloud. playerName hasn't changed.
                        dlt.deleteInBackground();
                    }
                    else{
                        errorindelete();
                    }
                }
            });

*/
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //button.setEnabled(true);
            doneSave();
            ParsePush push = new ParsePush();
            String message = "PAPA";
            push.setChannel("com.genius.pizzahutmanager.MANAGER");
            push.setMessage(message);
            push.sendInBackground();
            mProgressDialog.dismiss();
        }
    }
    public void errorindelete(){
        Toast.makeText(this,"DeleteError",Toast.LENGTH_SHORT).show();
    }
    public void errorinrider(){
        Toast.makeText(this,"RiderAssignError",Toast.LENGTH_SHORT).show();
    }
    public void doneSave(){
        Intent intent=new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);

        finish();
    }
    private void showArraysize() {
        Toast.makeText(this, ""+count,Toast.LENGTH_SHORT).show();
    }
}

class OrderItem{
    private String itemName;
    private int quantity;
    private String additional;

    public OrderItem(String itemName, int quantity,String additional){
        this.additional=additional;
        this.itemName=itemName;
        this.quantity=quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getAdditional() {
        return additional;
    }

    public String getItemName() {
        return itemName;
    }
}

class OrderItemListAdapter extends BaseAdapter {
    List<OrderItem> items;
    LayoutInflater inflater;
    public OrderItemListAdapter(Activity context, List<OrderItem> items) {
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
            view=inflater.inflate(R.layout.list_item_order,null);

        TextView itemname = (TextView) view.findViewById(R.id.order_item_name);
        TextView itemquantity = (TextView) view.findViewById(R.id.order_item_quantity);
        TextView itemt = (TextView) view.findViewById(R.id.order_item_t);
        TextView itemtt = (TextView) view.findViewById(R.id.order_item_tt);


        if (items.get(position).getAdditional()!=null) {
            String[] toppings = items.get(position).getAdditional().split(",");
            if (toppings.length > 0)
                itemt.setText(toppings[0]);
            if (toppings.length > 1)
                itemtt.setText(toppings[1]);
        }

        itemquantity.setText(""+items.get(position).getQuantity());
        itemname.setText(items.get(position).getItemName());
        return view;
    }

}
class Rider{
    public String riderName;
    public String riderId;
    public String phone;
    public Integer del;
    public Double ratings;
}