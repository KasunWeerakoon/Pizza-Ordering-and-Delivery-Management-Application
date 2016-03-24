package com.genious.pizza.pizzaorder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddExtra extends ActionBarActivity {
    ImageView img,cart;
    String details,name,sPrice,mPrice, lPrice,id,category,select,orderID;
    String[] oDetails=new String[3];
    TextView orderName,price;
    EditText quantity;
    float base,top1,top2,qty,oldVlue=1;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_extra);
        context=getApplicationContext();


        Intent intent=getIntent();
        Bundle extrss=intent.getExtras();
        if(extrss!=null) {
            details=extrss.getString("Details");
            select=extrss.getString("Select");
            Log.v("Select",select);
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(details);
            while (m.find()) {
                int i=0;
                System.out.println(m.group(1));
                oDetails[i++]=m.group(1);

            }
            Log.v("Select2", select);
            StringTokenizer stk = new StringTokenizer(details, "\"");
            stk.nextToken();
            name = stk.nextToken();
            sPrice=stk.nextToken();
            sPrice=sPrice.replace(",","");
            mPrice=stk.nextToken();
            mPrice=mPrice.replace(",","");
            lPrice = stk.nextToken();
            lPrice=lPrice.replace(",","");
            id=stk.nextToken();
            orderID=stk.nextToken();
            Cart.sPizza++;
            Log.v("details",name+sPrice+mPrice+ lPrice +id+"order"+orderID);
           // price.setText(mPrice);
        }
        img=(ImageView)findViewById(R.id.selectedIage);
        cart=(ImageView)findViewById(R.id.addCart);
        orderName=(TextView)findViewById(R.id.itemTxt);
        price=(TextView)findViewById(R.id.priceTxt);
        quantity=(EditText)findViewById(R.id.editText);
        price.setText(sPrice);
        orderName.setText(name);
        img.setBackgroundResource(Integer.parseInt(id));
        quantity.addTextChangedListener(new TextWatcher() {
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
                    qty = Float.parseFloat(quantity.getText().toString());
                }catch (NumberFormatException e){
                    Toast.makeText(getBaseContext(),"Enter a quantity",Toast.LENGTH_SHORT);
                    qty=1;
                }

                    price.setText(base*qty+"");

                oldVlue=base;
            }
        });


            final Spinner spinner = (Spinner) findViewById(R.id.spinnerSize);
            final Spinner spinner2 = (Spinner) findViewById(R.id.spinnerTop1);
            final Spinner spinner3 = (Spinner) findViewById(R.id.spinnerTop2);

            ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.sizes, android.R.layout.simple_spinner_item);
            ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(this, R.array.topping1, android.R.layout.simple_spinner_item);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
            spinner2.setAdapter(arrayAdapter1);
            spinner3.setAdapter(arrayAdapter1);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    Object item = parent.getItemAtPosition(pos);
                    String s = String.valueOf(spinner.getSelectedItem());
                    Log.v("Spinner1", s);
                    if (s.equals("Small")) {
                        //base = Float.parseFloat(sPrice);
                        price.setText(sPrice);
                    }
                    if (s.equals("Medium")) {
                        //base = Float.parseFloat(mPrice);
                        price.setText(mPrice);
                    }
                    if (s.equals("Large")){
                       // base=Float.parseFloat(lPrice);
                        price.setText(lPrice);
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                String s = String.valueOf(spinner2.getSelectedItem());
                Log.v("Spinner1", s);
                if(!s.equals("-Please select-")) {
                    base= Float.parseFloat("" + price.getText());
                    top1=20;
                    price.setText(base+top1+"");

                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                String s = String.valueOf(spinner3.getSelectedItem());
                Log.v("Spinner1", s);
                if (!(s.equals("-Please select-"))) {
                    base = Float.parseFloat("" + price.getText());
                    top2 = 50;
                    price.setText(base + top2 + "");
                    Log.v("TOp1", base + 2 + "");

                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
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
                Cart.sQty.add(size,""+quantity.getText());
                Cart.sItemID.add(size,orderID);
                Cart.sSize.add(size,""+spinner.getSelectedItem());
                Cart.sExtra.add(size,""+spinner2.getSelectedItem()+","+spinner3.getSelectedItem());
                Intent intent=new Intent(context,Cart.class);
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
