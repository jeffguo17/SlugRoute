package com.example.jeffr_000.slugroute;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jeffr_000 on 10/12/2016.
 */
public class MyApplication extends Application {
    private static MyApplication mMyApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mMyApplication = this;
    }

    public static synchronized MyApplication getInstance() {
        return mMyApplication;
    }

    public static Context getContext()  {
        return mMyApplication.getApplicationContext();
    }

    public static void StartService() {
        mMyApplication.startService(new Intent(getContext(),MyService.class));
    }

    public static void StopService() {
        mMyApplication.stopService(new Intent(getContext(),MyService.class));
    }
}
