package com.genious.pizza.pizzaorder;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;


public class LoginActivity extends ActionBarActivity {
    ProgressDialog mProgressDialog;
    Button button;
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = (Button) findViewById(R.id.btnSingIn);
        mPrefs = getSharedPreferences("ManagerData", MODE_PRIVATE);
        prefsEditor = mPrefs.edit();
        Parse.initialize(this, "DA8vnWMxaXd4bNQuGjGIjOtEVlO7EHE8uKHX9KFn", "tbNZjN96BHRHKpibeU6iN1VB8lZ71f5Eh3F0Beec");



    }
    public void login(View view){
        InputMethodManager inputManager =
                (InputMethodManager) this.
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        new RemoteDataTask().execute();
    }


    public void loginFail(){
        new AlertDialog.Builder(this)
                .setTitle("Login Failed")
                .setMessage("Username or Password error. Contact the administrator.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        button.setEnabled(true);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        int logged=0;
        ParseObject item;
        int branchId;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            button.setEnabled(false);
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Signing In");
            // Set progressdialog message
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            try {

                TextView user= (TextView) findViewById(R.id.etUserName);
                TextView pass= (TextView) findViewById(R.id.etPass);

                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Customer").whereEqualTo("UName",user.getText().toString());
                // Locate the column named "ranknum" in Parse.com and order list
                // by ascending

                    item=query.getFirst();
                Log.v("Pass",""+item.get("Password"));

                if(item.get("Password").equals(pass.getText().toString())) {
                    logged=1;
                }else{
                    logged=0;
                }



            } catch (com.parse.ParseException e) {
                logged=0;
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //button.setEnabled(true);
            if(logged==1){
                Intent intent=new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
                assignUser();
                finish();
            }else {
                loginFail();

            }
            mProgressDialog.dismiss();
        }
    public void assignUser(){
        MainActivity.nic= (String) item.get("NIC");
    }
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
