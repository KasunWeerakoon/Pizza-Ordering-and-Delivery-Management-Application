package com.genius.pizzadelivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class LoginActivity extends ActionBarActivity {

    private EditText password,username;
    Button login;
    String user,pass,u,p;
    public static String USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=(EditText)findViewById(R.id.etLoginUsername);
        password=(EditText)findViewById(R.id.etLoginPassword);
        login=(Button)findViewById(R.id.btnLogin);


//        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "DA8vnWMxaXd4bNQuGjGIjOtEVlO7EHE8uKHX9KFn", "tbNZjN96BHRHKpibeU6iN1VB8lZ71f5Eh3F0Beec");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user=username.getText().toString();
                pass=password.getText().toString();

                Log.v("input un", user);
                Log.v("input pass",pass);

                boolean validationError=false;
                StringBuilder validationErrorMessage=new StringBuilder("Please ");
                if(username.getText().length()==0){
                    validationError=true;
                    validationErrorMessage.append("enter a Username");
                }
                if(password.getText().length()==0){
                    if(validationError){
                        validationErrorMessage.append(", and ");
                    }
                    validationError=true;
                    validationErrorMessage.append("enter a password");
                }
                validationErrorMessage.append(".");

                if(validationError){
                    Toast.makeText(LoginActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                }
                final ProgressDialog dlg=new ProgressDialog(LoginActivity.this);
                dlg.setTitle("Please Wait!");
                dlg.setMessage("Logging in. Please Wait!");
//                dlg.show();

                final ParseQuery<ParseObject> query = ParseQuery.getQuery("Rider");

                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> ride, ParseException e) {
                        if (e == null) {

                            for (int i = 0; i < ride.size(); i++) {

                                ParseObject object = ride.get(i);
                                u = ((ParseObject) object).getString("Username");
                                p = ((ParseObject) object).getString("Password");
                                USER=((ParseObject) object).getString("RiderID");



                                Log.v("username: ",u);
                                Log.v("ddddd",USER);

                                if (user.equals(u) && pass.equals(p)) {

                                    ((ParseObject) object).put("Status", "in");
                                    object.saveInBackground();


                                    Intent intent = new Intent(LoginActivity.this, DetailsActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    break;

                                }
                            }

                        } else {
                            // Log.d("score", "Error: " + e.getMessage());
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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


