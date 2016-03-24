package com.genius.pizzadelivery;


//public class MapsActivity extends FragmentActivity {
//
//    private GoogleMap mMap;
//
//    double x1,y1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Parse.enableLocalDatastore(this);
//
//        Parse.initialize(this, "DA8vnWMxaXd4bNQuGjGIjOtEVlO7EHE8uKHX9KFn", "tbNZjN96BHRHKpibeU6iN1VB8lZ71f5Eh3F0Beec");
//
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("NewOrders");
//
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> loc, ParseException e) {
//                if (e == null) {
//
//                    for (int i = 0; i < loc.size(); i++) {
//
//                        ParseObject object = loc.get(loc.size() - 1);
//                        x1 = ((ParseObject) object).getDouble("latitude");
//                        y1 = ((ParseObject) object).getDouble("longitude");
//
//                    }
//
//                    String sx1 = Double.toString(x1);
//                    String sy1 = Double.toString(y1);
//
//                    Log.v("latitude: ", sx1);
//                    Log.v("longitude: ", sy1);
//
//                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + x1 + "," + y1 + "");
//                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                    mapIntent.setPackage("com.google.android.apps.maps");
//
//                    setContentView(R.layout.activity_maps);
//                    startActivity(mapIntent);
//
//                } else {
//                    Log.d("score", "Error: " + e.getMessage());
//                }
//            }
//        });
//
//
//
//    }
//
//}


/////****************************************************??///////////////////


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private float lat,lang;
    LocationListener locationListener;
    LocationManager locationManager;
    double x1,y1;
    String sx1,sy1;

    protected static final String TAG = "geofence-cordova-plugin-add-remove-geofences";

    public static final String PACKAGE_NAME = "com.ram.Geofence";
    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES_NAME";
    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =24 * 60 * 60 * 1000;

    protected GoogleApiClient mGoogleApiClient;
    protected ArrayList<Geofence> mGeofenceList;
    private boolean mGeofencesAdded;
    private PendingIntent mGeofencePendingIntent;
    private SharedPreferences mSharedPreferences;

    // Buttons for kicking off the process of adding or removing geofences.
    private Button mAddGeofencesButton;
    private Button mRemoveGeofencesButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//        mAddGeofencesButton = (Button) findViewById(R.id.add_geofences_button);
//        mRemoveGeofencesButton = (Button) findViewById(R.id.remove_geofences_button);

                Parse.initialize(this, "DA8vnWMxaXd4bNQuGjGIjOtEVlO7EHE8uKHX9KFn", "tbNZjN96BHRHKpibeU6iN1VB8lZ71f5Eh3F0Beec");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("NewOrders");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> loc, ParseException e) {
                if (e == null) {

                    for (int i = 0; i < loc.size(); i++) {

                        ParseObject object = loc.get(loc.size() - 1);
                        x1 = ((ParseObject) object).getDouble("latitude");
                        y1 = ((ParseObject) object).getDouble("longitude");

                    }

                    sx1 = Double.toString(x1);
                    sy1 = Double.toString(y1);

                    Log.v("latitude: ", sx1);
                    Log.v("longitude: ", sy1);

                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + x1 + "," + y1 + "");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    setContentView(R.layout.activity_maps);
                    startActivity(mapIntent);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });




        mGeofenceList = new ArrayList<Geofence>();
        mGeofencePendingIntent = null;

        mSharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME,
                MODE_PRIVATE);
        mGeofencesAdded = mSharedPreferences.getBoolean(GEOFENCES_ADDED_KEY, false);
        buildGoogleApiClient();
    }

    public void locationRequestEnable(long updateSeconds,int updateDistance){
        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.i(TAG, "Location Changed");
                lat= (float) location.getLatitude();
                lang= (float) location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,updateSeconds,updateDistance,locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,updateSeconds,updateDistance,locationListener);
    }

    public void locationRequestDisable(){
        locationManager.removeUpdates(locationListener);
        locationManager=null;
        locationListener=null;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        //  Log.i(TAG, "Connected to GoogleApiClient");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        //Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    public void addGeofencesButtonHandler(View view) {

        float lat1,lan1;

        lat1=Float.parseFloat(sx1);
        lan1=Float.parseFloat(sy1);

        addOrUpdateGeofence("Reached the customenr location", lat1, lan1, 100, GEOFENCE_EXPIRATION_IN_MILLISECONDS, 3);
        locationRequestEnable(0,10);
    }

    public void removeGeofencesButtonHandler(View view) {
        ArrayList rmlist=new ArrayList<String>();
        rmlist.add("Reached the customenr location");
        removeGeofences(rmlist);
        locationRequestDisable();
    }


    private void logSecurityException(SecurityException securityException) {
        //   Log.e(TAG, "Invalid location permission. " +
        //         "You need to use ACCESS_FINE_LOCATION with geofences", securityException);
    }

    public void onResult(Status status) {
        if (status.isSuccess()) {
            // Update state and save in shared preferences.
            mGeofencesAdded = true;
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(GEOFENCES_ADDED_KEY, mGeofencesAdded);
            editor.commit();

            Toast.makeText(
                    this,
                    getString(mGeofencesAdded ? R.string.geofences_added :
                            R.string.geofences_removed),
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            String errorMessage = GeofenceError.getErrorString(this,
                    status.getStatusCode());
            // Log.e(TAG, errorMessage);
        }
    }

    private PendingIntent getGeofencePendingIntent() {
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionMonitorService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void createList(String key,float latitude,float longitude,float radius,long expireDuration,int event){
        mGeofenceList.add(new Geofence.Builder()
                .setRequestId(key)
                .setCircularRegion(
                        latitude,
                        longitude,
                        radius
                )
                .setExpirationDuration(expireDuration)
                .setTransitionTypes(event)
                .build());
    }



    public void addOrUpdateGeofence(String key,float latitude,float longitude,float radius,long expireDuration,int event){

        createList(key,latitude,longitude,radius,expireDuration,event);

        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    getGeofencingRequest(),
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            logSecurityException(securityException);
        }
    }

    public void removeGeofences(List<String> id){
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            LocationServices.GeofencingApi.removeGeofences(
                    mGoogleApiClient,
                    id
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            logSecurityException(securityException);
        }
    }


    public boolean serviceOK() {
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {

            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Toast.makeText(this,"User Recoverble Error in Geofence Plugin",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Can't Connect to Google Play Services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}