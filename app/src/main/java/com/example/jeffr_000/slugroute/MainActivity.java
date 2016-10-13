package com.example.jeffr_000.slugroute;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mMessageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra(getResources().getString(R.string.bus_data_string));
                Log.d("test1",msg);
            }
        };

    }

    @Override
    protected void onStop() {
        super.onStop();

        MyApplication.StopService();
        MyService.StopService();
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(
                mMessageReceiver
        );

    }

    @Override
    protected void onResume() {
        super.onResume();

        MyApplication.StartService();
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(
                mMessageReceiver, new IntentFilter(getResources().getString(R.string.bus_data))
        );
    }
}
