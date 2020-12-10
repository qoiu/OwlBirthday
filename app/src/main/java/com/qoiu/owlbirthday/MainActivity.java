package com.qoiu.owlbirthday;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.FacebookSdk;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.facebook.applinks.AppLinkData;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("deeplink", MODE_PRIVATE);
        if (!sharedPreferences.getString("link", "").equals("")) {
            Toast.makeText(this, "Activity 3", Toast.LENGTH_LONG).show();
            //start activity #3
        } else {
            getDeepLink();
        }
    }

    private void getDeepLink() {
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();
        AppLinkData.fetchDeferredAppLinkData(this,
                new AppLinkData.CompletionHandler() {
                    @Override
                    public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                        if (appLinkData != null) {
                            sharedPreferences
                                    .edit()
                                    .putString("link", appLinkData.getTargetUri().toString())
                                    .apply();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //start activity #3
                                }});
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //start activity #2
                                }});
                        }
                    }
                }
        );
    }


    public void checkPreference() {
        if (!sharedPreferences.getString("link", "").equals("")) {
            Toast.makeText(this, "Activity 3", Toast.LENGTH_LONG).show();
            //start activity #3
        } else {
            Toast.makeText(this, "Activity 2", Toast.LENGTH_LONG).show();
            //start activity #2
        }
    }
}