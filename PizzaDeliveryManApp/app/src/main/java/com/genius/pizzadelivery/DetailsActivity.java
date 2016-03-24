package com.genius.pizzadelivery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class DetailsActivity extends ActionBarActivity {

    ImageButton btnNav,btnCall;
    TextView cusName,cusAdd,pizza,bev,app,pasta,des;
    String strCusName,strCusAdd,strTP,strPizza,strApp,strBev,strPasta,strDes,str;
    int pizzaNum,appNum,bevNum,pastaNum,desNum;
    String USERB=LoginActivity.USER;


   // String USERB="HcKSs0iJdS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        cusName=(TextView)findViewById(R.id.tvCusName);
        cusAdd=(TextView)findViewById(R.id.tvCusAddress);
        btnNav=(ImageButton)findViewById(R.id.ibNav1);
        btnCall=(ImageButton)findViewById(R.id.ibCall1);
        pizza=(TextView)findViewById(R.id.tvPizza);
        pasta=(TextView)findViewById(R.id.tvPasta);
        bev=(TextView)findViewById(R.id.tvBev);
        des=(TextView)findViewById(R.id.tvDes);
        app=(TextView)findViewById(R.id.tvApp);

        Parse.initialize(this, "DA8vnWMxaXd4bNQuGjGIjOtEVlO7EHE8uKHX9KFn", "tbNZjN96BHRHKpibeU6iN1VB8lZ71f5Eh3F0Beec");



        ParseQuery<ParseObject> query = ParseQuery.getQuery("ProcessingOrders");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> detail, ParseException e) {
                if (e == null) {

                    for (int i = detail.size()-1; i >0 ; i--) {

                        ParseObject object = detail.get(i);

                        str = ((ParseObject) object).getString("RiderID");

                        Log.v("USER", USERB);
                        Log.v("RIDER", str);

                        if(((ParseObject) object).getString("RiderID").equals(USERB))  {








                        strCusName = ((ParseObject) object).getString("CustomerName");
                        strCusAdd = ((ParseObject) object).getString("Address");
                        strTP = ((ParseObject) object).getString("CustomerPhone");
                        pizzaNum = ((ParseObject) object).getInt("pizza");
                        appNum = ((ParseObject) object).getInt("app");
                        pastaNum = ((ParseObject) object).getInt("pasta");
                        desNum = ((ParseObject) object).getInt("des");
                        bevNum = ((ParseObject) object).getInt("bev");

                            strDes = Integer.toString(desNum);
                            strPizza = Integer.toString(pizzaNum);
                            strApp = Integer.toString(appNum);
                            strBev = Integer.toString(bevNum);
                            strPasta = Integer.toString(pastaNum);

                            Log.v("Cus Name: ", strCusName);
                            Log.v("Cus Add: ", strCusAdd);
                            Log.v("telephone: ", strTP);
                            Log.v("appetizer: ", strDes);


                            cusName.setText(strCusName);
                            cusAdd.setText(strCusAdd);

                            pizza.setText(strPizza);
                            app.setText(strApp);
                            pasta.setText(strPasta);
                            des.setText(strDes);
                            bev.setText(strBev);

                            btnCall.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);


                                    callIntent.setData(Uri.parse("tel:+94" + strTP));
                                    startActivity(callIntent);
                                }
                            });

                            btnNav.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(DetailsActivity.this, MapsActivity.class);
                                    startActivity(intent);
                                }
                            });


                            break;




                  }

                }




                //   }
            }

            else

            {
                Log.d("score", "Error: " + e.getMessage());
            }
        }
    });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        //return true;
        return super.onCreateOptionsMenu(menu);
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


        if(id==R.id.action_profile){
            Intent i=new Intent(DetailsActivity.this,ProfileActivity.class);
            startActivity(i);
        }



        return super.onOptionsItemSelected(item);
    }
}
