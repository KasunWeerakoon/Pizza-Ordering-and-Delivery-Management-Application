package com.genius.pizzahutmanager;

import android.app.Application;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;

/**
 * Created by RaminduS on 5/23/2015.
 */
public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "DA8vnWMxaXd4bNQuGjGIjOtEVlO7EHE8uKHX9KFn", "tbNZjN96BHRHKpibeU6iN1VB8lZ71f5Eh3F0Beec");
        //PushService.setDefaultPushCallback(this, MainActivity.class);
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //Toast.makeText(getBaseContext(),"success",Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getBaseContext(),"fail",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
