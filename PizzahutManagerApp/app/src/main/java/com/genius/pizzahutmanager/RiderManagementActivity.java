package com.genius.pizzahutmanager;

         import java.util.ArrayList;
        import java.util.List;

        import android.app.Activity;
         import android.app.ProgressDialog;
         import android.content.Intent;
         import android.os.AsyncTask;
         import android.os.Bundle;
         import android.support.v7.widget.Toolbar;
         import android.util.Log;
         import android.view.View;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ListView;

         import com.parse.ParseException;
         import com.parse.ParseObject;
         import com.parse.ParseQuery;

public class RiderManagementActivity extends Activity {
    ProgressDialog mProgressDialog;
    private ListView listView;
    private static CustomRiderListAdapter riderListAdapter;
    public  List<RiderDetails> riderListdata = new ArrayList<RiderDetails>();
    public  List<ParseObject> parse_riderListdata;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_management);
        toolbar= (Toolbar) findViewById(R.id.toolbarrider);
        toolbar.setTitle("Rider Management");
        toolbar.setSubtitle("All Riders");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.inflateMenu(R.menu.rider_main);
        listView = (ListView) findViewById(R.id.riderlist);

        ///////////////////////////////////////////////////
        //////HARDCODED DATA//////////////////////////////
        new RiderDataTask().execute();
        riderListAdapter = new CustomRiderListAdapter(this, riderListdata);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                RiderDetails temp=	riderListdata.get(position);
                Intent parse=new Intent(RiderManagementActivity.this,RiderCpanelActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("rider", temp);
                parse.putExtras(mBundle);
                startActivity(parse);
            }

        });
    }


    private class RiderDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(RiderManagementActivity.this);
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
            riderListdata = new ArrayList<RiderDetails>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Rider").whereEqualTo("ShopID",MainActivity.branchId);
                // Locate the column named "ranknum" in Parse.com and order list
                // by ascending
                query.orderByDescending("createdAt");
               parse_riderListdata = query.find();
                for (ParseObject newItem : parse_riderListdata) {
                    // Locate images in flag column
                    Double ratings=(Double)newItem.get("ratings");
                    Integer del=(Integer)newItem.get("deliveries");
                    RiderDetails r=new RiderDetails();

                    r.setRiderName((String)newItem.get("Name"));
                    r.setRiderUname((String)newItem.get("Username"));
                    r.setRiderNic((String)newItem.get("NIC"));
                    r.setRiderPhone((String)newItem.get("Phone"));
                    r.setRiderRating(ratings.toString());
                    r.setRiderStatus((String)newItem.get("Status"));
                    r.setRiderDelivery(del.toString());
                    riderListdata.add(r);
                    //////////////////////////////////////////////////




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
            listView = (ListView) findViewById(R.id.riderlist);
            // Pass the results into ListViewAdapter.java
            riderListAdapter = new CustomRiderListAdapter(RiderManagementActivity.this,
                    riderListdata);
            // Binds the Adapter to the ListView
            listView.setAdapter(riderListAdapter);
            // Close the progressdialog
            //completedListAdapter.notifyDataSetChanged();
            mProgressDialog.dismiss();
        }
    }
}