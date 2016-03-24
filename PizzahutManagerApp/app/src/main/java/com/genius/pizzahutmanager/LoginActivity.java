package com.genius.pizzahutmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

/**
 * Created by RaminduS on 5/24/2015.
 */
public class LoginActivity extends Activity{
    ProgressDialog mProgressDialog;
    Button button;
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button= (Button) findViewById(R.id.btnSingIn);
        mPrefs = getSharedPreferences("ManagerData",MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        Gson gson = new Gson();
        String json = mPrefs.getString("manager", "");
        Manager obj = gson.fromJson(json, Manager.class);
        if((obj!=null)&&(obj.BranchID!="")){
            Intent intent=new Intent(getBaseContext(),MainActivity.class);
            MainActivity.branchId=obj.BranchID;
            startActivity(intent);
            finish();
        }

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
                            "Manager").whereEqualTo("Username",user.getText().toString());
                    // Locate the column named "ranknum" in Parse.com and order list
                    // by ascending
                    item=query.getFirst();
                    if(item.get("Password").equals(pass.getText().toString())) {
                        logged=1;
                    }else{
                        logged=0;
                    }


            } catch (ParseException e) {
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
                MainActivity.branchId=(String)item.get("ShopID");

                Gson gson = new Gson();
                Manager manager=new Manager();
                manager.BranchID= (String) item.get("ShopID");
                String json = gson.toJson(manager);
                prefsEditor.putString("manager", json);
                prefsEditor.commit();

                startActivity(intent);
                finish();
            }else {
                loginFail();

            }
            mProgressDialog.dismiss();
        }
    }
}

class Manager{
    public String BranchID;
}