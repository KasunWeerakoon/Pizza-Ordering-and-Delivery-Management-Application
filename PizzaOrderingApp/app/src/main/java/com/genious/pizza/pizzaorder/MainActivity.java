package com.genious.pizza.pizzaorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;


public class MainActivity extends Activity {
    public static String nic;
    ImageView panPizza;
    ImageView pasta;
    ImageView beverage;
    ImageView desert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        panPizza=(ImageView)findViewById(R.id.image1);
        pasta=(ImageView)findViewById(R.id.image2);
        beverage=(ImageView)findViewById(R.id.image3);
        desert=(ImageView)findViewById(R.id.image4);
        setClickListner(panPizza,"panPizza");
        setClickListner(pasta,"pasta");
        setClickListner(beverage,"beverage");
        setClickListner(desert,"desert");
        try {
            int v = getPackageManager().getPackageInfo("com.google.android.gms", 0 ).versionCode;
            Log.v("GMS",""+v);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


//        ParsePush.subscribeInBackground("", new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
//                } else {
//                    Log.e("com.parse.push", "failed to subscribe for push", e);
//                }
//            }
//        });

    }

    private void setClickListner(ImageView image,final String type) {
        Log.v("String",""+image);
        if(type=="panPizza") {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context cont = v.getContext();
                    Intent intent = new Intent(cont, PizzaCategoryActivity.class);
                    intent.putExtra("Type", type);
                    startActivity(intent);

                }
            });
        }else {

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context cont = v.getContext();
                    Intent intent = new Intent(cont, ListGenerator.class);
                    intent.putExtra("Type", type);
                    startActivity(intent);

                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
