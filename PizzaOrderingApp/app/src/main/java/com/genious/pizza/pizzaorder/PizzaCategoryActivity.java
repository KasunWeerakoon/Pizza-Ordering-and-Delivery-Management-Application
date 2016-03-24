package com.genious.pizza.pizzaorder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class PizzaCategoryActivity extends ActionBarActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    int[] img={R.drawable.pan_pizza,R.drawable.sausage_crust,R.drawable.stuffed_crust,R.drawable.mayo_magic_section,R.drawable.the_big_pizza};
    ImageView imageView;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizza_category);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras!=null)
            type=extras.getString("Type");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManagerr = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManagerr);
        adapter=new RecyclerAdapter(img,type);
        recyclerView.setAdapter(adapter);




//        imageView=(ImageView)findViewById(R.id.imageItem);
//        imageView.setBackgroundResource(img[1]);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pizza_category, menu);
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
