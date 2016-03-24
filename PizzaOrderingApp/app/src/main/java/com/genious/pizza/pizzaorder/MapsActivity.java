package com.genious.pizza.pizzaorder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public static String nic;

    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setMarkers();
        Parse.initialize(this, "DA8vnWMxaXd4bNQuGjGIjOtEVlO7EHE8uKHX9KFn", "tbNZjN96BHRHKpibeU6iN1VB8lZ71f5Eh3F0Beec");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        setUpMapIfNeeded();
        mMap.setMyLocationEnabled(true);
        final Context context=getApplicationContext();
//        Location myLocation = mMap.getMyLocation();

        CameraPosition cameraPosition=new CameraPosition.Builder()
                .target(new LatLng(6.795219, 79.901290))
                .zoom(13)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(6.795219, 79.901290))
                .draggable(true)
                .title("Select Location"));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng position = marker.getPosition();
                Log.v("position",""+position.latitude+position.longitude);
                markerDialog(position.latitude,position.longitude);
            }
        });



    }

    private void setMarkers() {
        Parse.initialize(this, "DA8vnWMxaXd4bNQuGjGIjOtEVlO7EHE8uKHX9KFn", "tbNZjN96BHRHKpibeU6iN1VB8lZ71f5Eh3F0Beec");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Shop");

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, com.parse.ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    ArrayList<String> lat=new ArrayList<String>();
                    ArrayList<String> lang=new ArrayList<String>();
                    float min=3000;
                    for (int i = 0; i < scoreList.size(); i++) {

                        ParseObject object = scoreList.get(i);
                        String pLat = ((ParseObject) object).get("Latitude").toString();
                        String pLang = ((ParseObject) object).get("Longitude").toString();
                        lat.add(i,pLat);
                        lang.add(i,pLang);
                            Log.v("Longi",""+pLat+""+pLang);
                        float[] results = new float[1];
                        Location.distanceBetween(6.790720, 79.886579,Double.parseDouble(pLat),Double.parseDouble(pLang), results);
                        Log.v("Distance", Math.round(results[0]/1000)+"");

                        if(min>results[0])
                            min=results[0];
                        Log.v("Min",""+min);







                    }




                    ;

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }


        });


    }

    private void markerDialog(final Double lat, final Double lag) {
//        Context context = ;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Location")
                .setMessage("Do you want to confirm the order")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmed(lat,lag);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void confirmed(Double lat,Double lang) {

        ParseObject newUpdate=new ParseObject("NewOrders");
        String objectId;
        newUpdate.put("LocName","Katubedda");
        newUpdate.put("latitude",lat);
        newUpdate.put("longitude",lang);
        float[] results = new float[1];
        Location.distanceBetween(6.790720, 79.886579,
                lat,lang, results);
        Log.v("Distance", Math.round(results[0]/1000)+"");
        newUpdate.put("Distance", results[0]);

        Random random=new Random();
        int ran = random.nextInt();
        newUpdate.put("OrderID",""+ran);
         newUpdate.put("NIC",MainActivity.nic  );


        newUpdate.put("pizza",Cart.sPizza);

        newUpdate.put("app",0);
        newUpdate.put("ShopID","E1szrCwdXk");
        newUpdate.put("pasta",Cart.sPasta);
        newUpdate.put("des",Cart.sDessert);
        newUpdate.put("bev",Cart.sBeverages);

        try {
            newUpdate.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        objectId=newUpdate.getObjectId();
        Log.v("Obj",""+ran);

        Log.v("size",""+Cart.sItemName.size());
        for(int i=0; i<Cart.sItemName.size();i++){
            ParseObject orderItem=new ParseObject("OrderItem");
            orderItem.put("OrderID",""+ran);

            orderItem.put("ItemID",Integer.parseInt(Cart.sItemID.get(i)));
            orderItem.put("ItemName",Cart.sItemName.get(i));
            orderItem.put("quantity",Integer.parseInt(Cart.sQty.get(i)));
            orderItem.put("Size",Cart.sSize.get(i));
            orderItem.put("MainCategory", "PIZZA");
            orderItem.put("Additional",Cart.sExtra.get(i));
            orderItem.saveInBackground();

        }

        try {
            JSONObject jobj = new JSONObject("{\n" +
                    "\"action\":\"com.genius.pizzahutmanager.MANAGER\", \n" +
                    "\"p\": \"new order\"\n" +
                    "}");
            ParsePush push = new ParsePush();


            push.setData(jobj);
            push.sendInBackground();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        Intent intent=new Intent(MapsActivity.this,MainActivity.class);
        startActivity(intent);

    }

    private void setObjectId() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }



    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    private void setUpMap() {
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }}

        private void buildAlertMessageNoGps() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }

    }



    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
