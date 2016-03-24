package com.genius.pizzahutmanager;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends FragmentActivity {
    public static String branchId;
    public static NewList selectedOrder;
    SharedPreferences mPrefs;
    public static RemoteDataTask remoteTask;
    String TAG ="GCM";
    Toolbar toolbar;

    NewListAdapter newListAdapter;
    ProcessingListAdapter processingListAdapter;
    CompletedListAdapter completedListAdapter;

    ListView newlistview;
    ListView processinglistview;
    ListView completedlistview;

    ArrayList<NewList> newlist;
    ArrayList<ProcessingItem> processinglist;
    ArrayList<CompletedItem> completedlist;


    ActionMenuItemView new_item;
    ActionMenuItemView processing_item;
    ActionMenuItemView completed_item;


    ProgressDialog mProgressDialog;
    List<ParseObject> parse_newlist;
    List<ParseObject> parse_processinglist;
    List<ParseObject> parse_completedlist;

    TextView txtmain;
    ImageView iconmain;
    @Override
    protected void onStart() {
        super.onStart();
    }
    public void logout(){
        SharedPreferences sharedPreferences= getSharedPreferences("ManagerData",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.remove("manager");
        prefsEditor.commit();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = getPreferences(MODE_PRIVATE);
        if(branchId==null){


            Gson gson = new Gson();
            String json = mPrefs.getString("manager", "");
            Manager obj = gson.fromJson(json, Manager.class);
            branchId=obj.BranchID;
        }
        toolbar= (Toolbar) findViewById(R.id.toolbarmain);
        toolbar.setTitleTextColor(getResources().getColor(R.color.abc_background_cache_hint_selector_material_dark));
        //toolbar.setTitle("Pizzahut Manager");
        toolbar.inflateMenu(R.menu.menu_main);
        /*toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                Toast.makeText(getBaseContext(),"sd",Toast.LENGTH_SHORT).show();
            }
        });
        */
        toolbar.setTitle("Pizzahut");
        toolbar.setSubtitle("Manager System");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));

        txtmain= (TextView) findViewById(R.id.txt_main);
        iconmain= (ImageView) findViewById(R.id.icon_main);

        newlist=new ArrayList<NewList>();
        new RemoteDataTask().execute();

        newListAdapter=new NewListAdapter(this,newlist);
        newlistview=(ListView)findViewById(R.id.neworderslist);
        newlistview.setAdapter(newListAdapter);
        newlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                /*String Slecteditem= newlist.get(position).getDesname();
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                newlist.remove(position);
                newListAdapter.notifyDataSetChanged();*/
                Intent intent =new Intent(getBaseContext(),SingleOrderActivity.class);
                selectedOrder=newlist.get(position);
                Toast.makeText(getBaseContext(),""+selectedOrder.getOrderId(),Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
 /*
        processinglist=new ArrayList<ProcessingItem>();
        new ProcessingDataTask().execute();
        processingListAdapter=new ProcessingListAdapter(this,processinglist);
        processinglistview=(ListView)findViewById(R.id.processingorderslist);
        processinglistview.setAdapter(processingListAdapter);


       completedlist=new ArrayList<CompletedItem>();
        new CompletedDataTask().execute();
        completedListAdapter=new CompletedListAdapter(this,completedlist);
        completedlistview=(ListView)findViewById(R.id.completedorderslist);
        completedlistview.setAdapter(completedListAdapter);
*/
        processinglistview= (ListView) findViewById(R.id.processingorderslist);
        completedlistview= (ListView) findViewById(R.id.completedorderslist);
        processinglistview.setVisibility(View.GONE);
        completedlistview.setVisibility(View.GONE);


        new_item=(ActionMenuItemView)findViewById(R.id.new_order_item);
        processing_item=(ActionMenuItemView)findViewById(R.id.processing_order_item);
        completed_item=(ActionMenuItemView)findViewById(R.id.completed_order_item);
        new_item.setVisibility(View.GONE);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.new_order_item:
                        new_item.setVisibility(View.GONE);
                        processing_item.setVisibility(View.VISIBLE);
                        completed_item.setVisibility(View.VISIBLE);
                        newlistview.setVisibility(View.VISIBLE);
                        processinglistview.setVisibility(View.GONE);
                        completedlistview.setVisibility(View.GONE);

                        newlist=new ArrayList<NewList>();
                        new RemoteDataTask().execute();
                        newListAdapter.notifyDataSetChanged();
                        iconmain.setBackgroundResource(R.drawable.ios7email);
                        txtmain.setText("New Orders");
                        break;

                    case R.id.processing_order_item:
                        new_item.setVisibility(View.VISIBLE);
                        processing_item.setVisibility(View.GONE);
                        completed_item.setVisibility(View.VISIBLE);
                        newlistview.setVisibility(View.GONE);
                        processinglistview.setVisibility(View.VISIBLE);
                        completedlistview.setVisibility(View.GONE);

                        processinglist=new ArrayList<ProcessingItem>();
                        new ProcessingDataTask().execute();
                        iconmain.setBackgroundResource(R.drawable.loop);
                        txtmain.setText("Processing Orders");
                        break;

                    case R.id.completed_order_item:
                        new_item.setVisibility(View.VISIBLE);
                        processing_item.setVisibility(View.VISIBLE);
                        completed_item.setVisibility(View.GONE);
                        completedlist=new ArrayList<CompletedItem>();
                        newlistview.setVisibility(View.GONE);
                        processinglistview.setVisibility(View.GONE);
                        completedlistview.setVisibility(View.VISIBLE);

                        new CompletedDataTask().execute();
                        iconmain.setBackgroundResource(R.drawable.clipboard);
                        txtmain.setText("Completed Orders");
                        break;
                    case R.id.action_dilman:
                        Intent intent=new Intent(getBaseContext(),RiderManagementActivity.class);
                        startActivity(intent);

                        break;
                    case R.id.action_settings:
                        //stallListAdapter.notifyDataSetChanged();
                        //gridView.setVisibility(View.GONE);
                        //listView.setVisibility(View.VISIBLE);


                        break;
                    case R.id.logout:
                        //stallListAdapter.notifyDataSetChanged();
                        //gridView.setVisibility(View.GONE);
                        //listView.setVisibility(View.VISIBLE);
logout();

                        break;

                }

                return false;
            }
        });
    }



    public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("New Orders");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            newlist = new ArrayList<NewList>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "NewOrders").whereEqualTo("ShopID",branchId);
                // Locate the column named "ranknum" in Parse.com and order list
                // by ascending
                query.orderByDescending("createdAt");
                parse_newlist = query.find();
                for (ParseObject newItem : parse_newlist) {
                    // Locate images in flag column
                    String location= (String) newItem.get("LocName");
                    Double distance= (Double) newItem.get("Distance");
                    String orderId=(String) newItem.get("OrderID");
                    String nic= (String) newItem.get("NIC");
                    Double latitude=(Double)newItem.get("latitude");
                    Double longitude=(Double)newItem.get("longitude");

                    int pizza=(int)newItem.get("pizza");
                    int app=(int)newItem.get("app");
                    int pasta=(int)newItem.get("pasta");
                    int des=(int)newItem.get("des");
                    int bev=(int)newItem.get("bev");

                    NewList item = new NewList(location,distance,pizza,app,pasta,des,bev,orderId,nic,latitude,longitude);
                    newlist.add(item);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            newlistview = (ListView) findViewById(R.id.neworderslist);
            // Pass the results into ListViewAdapter.java
            newListAdapter = new NewListAdapter(MainActivity.this,
                    newlist);
            // Binds the Adapter to the ListView
            newlistview.setAdapter(newListAdapter);
            remoteTask=new RemoteDataTask();
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }



    private class ProcessingDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Processing Orders");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            processinglist = new ArrayList<ProcessingItem>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "ProcessingOrders").whereEqualTo("ShopID",branchId);
                // Locate the column named "ranknum" in Parse.com and order list
                // by ascending
                query.orderByDescending("createdAt");
                parse_processinglist = query.find();
                for (ParseObject newItem : parse_processinglist) {
                    // Locate images in flag column
                    String location= (String) newItem.get("Address");
                    String cname=(String) newItem.get("CustomerName");
                    String cphone=(String) newItem.get("CustomerPhone");
                    String cnic=(String) newItem.get("CNIC");
                    String orderId=(String) newItem.get("OrderID");
                    Double latitude=(Double)newItem.get("latitude");
                    Double longitude=(Double)newItem.get("longitude");
                    String riderName=(String)newItem.get("RiderName");
                    String riderId=(String)newItem.get("RiderID");

                    ProcessingItem item = new ProcessingItem(cname,location,cphone,cnic,orderId,latitude,longitude,riderName,riderId);
                    processinglist.add(item);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            processinglistview = (ListView) findViewById(R.id.processingorderslist);
            // Pass the results into ListViewAdapter.java
            processingListAdapter = new ProcessingListAdapter(MainActivity.this,
                    processinglist);
            // Binds the Adapter to the ListView
            processinglistview.setAdapter(processingListAdapter);
            // Close the progressdialog
            //processingListAdapter.notifyDataSetChanged();
            mProgressDialog.dismiss();
        }
    }




    private class CompletedDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Processing Orders");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            completedlist = new ArrayList<CompletedItem>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "CompletedOrders").whereEqualTo("ShopID",branchId);
                // Locate the column named "ranknum" in Parse.com and order list
                // by ascending
                query.orderByDescending("createdAt");
                parse_completedlist = query.find();
                for (ParseObject newItem : parse_completedlist) {
                    // Locate images in flag column
                    String ccname= (String) newItem.get("CustomerName");
                    String cclocation= (String) newItem.get("LocName");
                    Integer ccphone=(Integer) newItem.get("Phone");
                    String rname= (String) newItem.get("RiderName");
                    Double rrating=(Double)newItem.get("Rating");

                    CompletedItem item = new CompletedItem(ccname,cclocation,ccphone,rname,rrating);
                    completedlist.add(item);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            completedlistview = (ListView) findViewById(R.id.completedorderslist);
            // Pass the results into ListViewAdapter.java
            completedListAdapter = new CompletedListAdapter(MainActivity.this,
                    completedlist);
            // Binds the Adapter to the ListView
            completedlistview.setAdapter(completedListAdapter);
            // Close the progressdialog
            //completedListAdapter.notifyDataSetChanged();
            mProgressDialog.dismiss();
        }
    }
}







