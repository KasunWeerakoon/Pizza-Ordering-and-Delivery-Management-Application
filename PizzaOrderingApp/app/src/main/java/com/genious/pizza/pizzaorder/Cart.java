package com.genious.pizza.pizzaorder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;


public class Cart extends ActionBarActivity  {
    public static ArrayList<String> sItemName=new ArrayList<String>();
    public static ArrayList<String> sQty=new ArrayList<String>();
    public static ArrayList<String> sSize=new ArrayList<String>();
    public static ArrayList<String> sPrice=new ArrayList<String>();
    public static ArrayList<String> sImageID=new ArrayList<String>();
    public static ArrayList<String> sItemID=new ArrayList<String>();
    public static ArrayList<String> sExtra=new ArrayList<String>();
    public static int sPizza=0,sPasta=0,sBeverages=0,sDessert=0;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ImageView check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = (RecyclerView) findViewById(R.id.cart_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        final LinearLayoutManager layoutManagerr = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerr);
        adapter=new CartAdapter();
        recyclerView.setAdapter(adapter);
        check=(ImageView)findViewById(R.id.checkout);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Cart.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Cart.this,MainActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
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
