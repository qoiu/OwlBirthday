package com.qoiu.owlbirthday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.FacebookSdk;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.applinks.AppLinkData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private String TAG="MainActivity";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("deeplink", MODE_PRIVATE);
        if (!sharedPreferences.getString("link", "").equals("")) {
            //start activity #3
            Log.d(TAG,"activity 3 from SharedPreference");
            Intent intent = new Intent(MainActivity.this,WebActivity.class);
            startActivity(intent);
        } else {
            getDeepLink();
        }

        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d("TAG", msg);
                    }
                });
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
                                    Log.d(TAG,"activity 3 from deeplink");
                                    Intent intent = new Intent(MainActivity.this,WebActivity.class);
                                    startActivity(intent);
                                }});
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //start activity #2
                                    Log.d(TAG,"activity 2 no deeplink");
                                    Intent intent = new Intent(MainActivity.this,GameActivity.class);
                                    startActivity(intent);
                                }});
                        }
                    }
                }
        );
    }
}