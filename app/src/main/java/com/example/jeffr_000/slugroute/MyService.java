package com.example.jeffr_000.slugroute;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

/**
 * Created by jeffr_000 on 10/12/2016.
 */
public class MyService extends Service {
    //Service can only start once!

    private Handler cHandler = new Handler();
    private static boolean isRunning = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!isRunning) {
            isRunning = true;
            Update();
        }
        return START_STICKY;
    }

    private void Update(){
        if(isRunning) { cHandler.postDelayed(GetBusData,1000); }
    }

    //Running a continuous loop of http request from ucsc taps
    private Runnable GetBusData = new Runnable(){
        @Override
        public void run() {
            String url = "http://bts.ucsc.edu:8081/location/get";
            RequestQueue mRequestQueue = MyVolley.getInstance().getRequestQueue();

            JsonArrayRequest mJsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            sendDataToActivity(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.d("test2", String.valueOf(error));
                        }
                    });

            mRequestQueue.add(mJsonArrayRequest);
            Update();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //Converts the JsonArray from http request to a string, and send it to main activity.
    private static void sendDataToActivity(JSONArray mJsonArray) {
        Intent mIntent = new Intent("BusData");
        mIntent.putExtra("BusDataString",String.valueOf(mJsonArray));
        LocalBroadcastManager.getInstance(MyApplication.getContext()).sendBroadcast(mIntent);
    }

    public static void StopService() {
        isRunning = false;
    }

}
