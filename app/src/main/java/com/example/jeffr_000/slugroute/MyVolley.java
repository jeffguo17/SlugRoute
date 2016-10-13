package com.example.jeffr_000.slugroute;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by jeffr_000 on 10/12/2016.
 */
public class MyVolley {
    private static MyVolley mInstance;
    private RequestQueue mRequestQueue;

    private MyVolley() {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getContext());
    }

    public static synchronized MyVolley getInstance(){
        if (mInstance == null) {
            mInstance = new MyVolley();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

}
