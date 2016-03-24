package com.genius.pizzadelivery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class ProfileActivity extends ActionBarActivity {

    TextView fName,lName,tp,ratings,dele;
    String strFName,strLName,strRatings,strTP,strDel,riderID;
    int delNum;
    float ratingsNum;
    String USERB=LoginActivity.USER;
    ImageView proPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fName=(TextView)findViewById(R.id.tvDelFirstName);
        lName=(TextView)findViewById(R.id.tvDelLasttName);
        ratings=(TextView)findViewById(R.id.tvRating);
        tp=(TextView)findViewById(R.id.tvTP);
        dele=(TextView)findViewById(R.id.tvDeliveries);
        proPic=(ImageView)findViewById(R.id.ivproPic);

        Parse.initialize(this, "DA8vnWMxaXd4bNQuGjGIjOtEVlO7EHE8uKHX9KFn", "tbNZjN96BHRHKpibeU6iN1VB8lZ71f5Eh3F0Beec");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Rider");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> detail, ParseException e) {
                if (e == null) {

                    for (int i = 0; i < detail.size(); i++) {

                        ParseObject object = detail.get(i);

                       riderID=((ParseObject)object).getString("RiderID");

                        Log.v("dd0",riderID);

                        if(riderID.equals(USERB))  {


                        strFName = ((ParseObject) object).getString("FirstName");
                        strLName = ((ParseObject) object).getString("LastName");
                        strTP = ((ParseObject) object).getString("Phone");
                        delNum = ((ParseObject) object).getInt("deliveries");
                        ratingsNum = ((ParseObject) object).getLong("ratings");

                        ParseFile image = (ParseFile) object.getParseFile("RiderPhoto");

                            displayImage(image, proPic);


                        strDel = Integer.toString(delNum);
                        strRatings = Float.toString(ratingsNum);

                        Log.v("", strFName);
                        Log.v("", strLName);
                        Log.v("", strRatings);
                        Log.v("", strTP);
                            Log.v("", strDel);

                        fName.setText(strFName);
                        lName.setText(strLName);
                        tp.setText(strTP);
                        ratings.setText(strRatings);
                            dele.setText(strDel);


                    } }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

    }


    private void displayImage(ParseFile thumbnail, final ImageView img) {

        if (thumbnail != null) {
            thumbnail.getDataInBackground(new GetDataCallback() {

                @Override
                public void done(byte[] data, ParseException e) {

                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                                data.length);

                        if (bmp != null) {

                            Log.e("parse file ok", " null");
                            // img.setImageBitmap(Bitmap.createScaledBitmap(bmp,
                            // (display.getWidth() / 5),
                            // (display.getWidth() /50), false));
                            img.setImageBitmap(bmp);
                            // img.setPadding(10, 10, 0, 0);



                        }
                    } else {
                        Log.e("paser after downloade", " null");
                    }

                }
            });
        } else {

            Log.e("parse file", " null");

            // img.setImageResource(R.drawable.ic_launcher);

            img.setPadding(10, 10, 10, 10);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
