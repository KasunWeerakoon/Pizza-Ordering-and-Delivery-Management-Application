package com.genious.pizza.pizzaorder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class ListGenerator extends ActionBarActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    String[] words;
    ArrayList<String> pizzaCat =new ArrayList<String>();
    Context context;
    int[] panPizzaImg={R.drawable.pan1,R.drawable.pan2,R.drawable.pan3,R.drawable.pan4, R.drawable.pan5,R.drawable.pan6,R.drawable.pan7,R.drawable.pan8,R.drawable.pan9};
    int[] pastaImg={R.drawable.pasta1,R.drawable.pasta2,R.drawable.pasta3,R.drawable.pasta4,R.drawable.pasta5,R.drawable.pasta6,R.drawable.pasta7,R.drawable.pasta8,R.drawable.pasta9};
    int[] softImg={R.drawable.soft1,R.drawable.soft2,R.drawable.soft3};
    int[] dessertImg={R.drawable.dessert1,R.drawable.dessert2,R.drawable.dessert3};
    String type;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_generator);
        context=getApplicationContext();
        Log.v("ImageID",""+softImg[0]);

        Intent intent=getIntent();
        Bundle extras=intent.getExtras();

        if(extras!=null)
            type=extras.getString("Type");
        else
            Log.v("type","null");
        Log.v("type",type);


        recyclerView = (RecyclerView) findViewById(R.id.listgen_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        final LinearLayoutManager layoutManagerr = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerr);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);



        Parse.initialize(this, "DA8vnWMxaXd4bNQuGjGIjOtEVlO7EHE8uKHX9KFn", "tbNZjN96BHRHKpibeU6iN1VB8lZ71f5Eh3F0Beec");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Items");
                switch (type){
                    case "panPizza":
                        query.whereEqualTo("CatID", 1);
                        break;
                    case "pasta":
                        query.whereEqualTo("CatID",8);
                        break;
                    case "beverage":
                        query.whereEqualTo("CatID",12);
                        break;
                    case "desert":
                        query.whereEqualTo("CatID",9);
                        break;


                }


        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, com.parse.ParseException e) {
                if (e == null) {
                    //SuccsessParseCallBack(scoreList);
                    switch (type){
                        case "panPizza":
                            adapter = new DDRecyclerAdaptor(panPizzaImg, scoreList, "main_menu");
                            break;
                        case "pasta":
                            adapter = new DRecyclerAdapter(pastaImg, scoreList, "main_menu");
                            break;
                        case "beverage":
                            adapter = new DRecyclerAdapter(softImg, scoreList, "main_menu");
                            break;
                        case "desert":
                            adapter = new DRecyclerAdapter(dessertImg, scoreList, "main_menu");
                            break;
                    }

                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }


        });
    }


    private void SuccsessParseCallBack(List<ParseObject> scoreList) {

        Log.d("score", "Retrieved " + scoreList.size() + " scores");

        for (int i = 0; i < scoreList.size(); i++) {

            ParseObject object = scoreList.get(i);
            String name = ((ParseObject) object).get("CategoryType").toString();
            pizzaCat.add(i, name);
            setWordList(name,scoreList.size(),i);
            Log.v("Data",name);

        }
        ;

        Log.v("Final2", pizzaCat.get(2));


    }

    public void setWordList(String wordList,int size,int i) {

        words = new String[size];
        words[i] = wordList;

        Log.d("score", "Retrieved " + words[i] + " scores");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_generator, menu);
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
