package com.genious.pizza.pizzaorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kasun on 5/25/2015.
 */
public class Add extends ActionBarActivity {
    ImageView img,cart;
    String details,name,sPrice,mPrice, lPrice,id,item,select;
    String[] oDetails=new String[3];
    TextView orderName,price;
    EditText eQty;
    int qty;
    float base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        img=(ImageView)findViewById(R.id.selectedIage);
        cart=(ImageView)findViewById(R.id.cartImg);
        orderName=(TextView)findViewById(R.id.itemTxt);
        eQty =(EditText)findViewById(R.id.editText);
        price=(TextView)findViewById(R.id.priceTxt);
        Intent intent=getIntent();
        Bundle extrss=intent.getExtras();
        if(extrss!=null) {
            details=extrss.getString("Details");
            select=extrss.getString("Select");
            Log.v("Select", select);
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(details);
            while (m.find()) {
                int i=0;
                System.out.println(m.group(1));
                oDetails[i++]=m.group(1);

            }



                StringTokenizer stk = new StringTokenizer(details, "\"");
                stk.nextToken();
                name = stk.nextToken();
                lPrice = stk.nextToken();
                id=stk.nextToken();
                item=stk.nextToken();
                Log.v("details",name+ lPrice +id);
                price.setText(lPrice);









        }
        orderName.setText(name);
        img.setBackgroundResource(Integer.parseInt(id));
        eQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                base = Float.parseFloat("" + price.getText());
                try {
                    qty =Integer.parseInt(eQty.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getBaseContext(), "Enter a quantity", Toast.LENGTH_SHORT);
                    qty = 1;
                }

                price.setText(base * qty + "");

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context contex=v.getContext();
                int size = Cart.sItemName.size();
                Cart.sItemName.add(size,name);
                Cart.sImageID.add(size,id);
                Cart.sPrice.add(size,""+price.getText());
                Cart.sQty.add(size,""+eQty.getText());
                Cart.sItemID.add(size,item);
                Cart.sSize.add(size,"Reguler");
                Cart.sExtra.add(size," ");
                Intent intent=new Intent(contex,Cart.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contex.startActivity(intent);
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_extra, menu);
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
