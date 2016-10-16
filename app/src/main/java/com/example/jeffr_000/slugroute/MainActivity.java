package com.example.jeffr_000.slugroute;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Property;
import android.view.Display;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private BroadcastReceiver mMessageReceiver;
    private GoogleMap mGoogleMap;
    private BusStops mBusStops = new BusStops();
    private ArrayList<Bus> mBusList = new ArrayList();
    private ArrayList<BusMarker> mBusMarkerList = new ArrayList<>();
    private boolean firstRun = true;
    private Marker selectMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkGoogleService()) {
            setContentView(R.layout.activity_main);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
            mapFragment.getMapAsync(this);
        }

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra(getResources().getString(R.string.bus_data_string));
                setBusList(msg);
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

        firstRun = true;

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!checkNetwork()) {
            showDialog("Please check your internet connection", "OK");
        }

        if (checkGoogleService()) {
            MyApplication.StartService();
            LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(
                    mMessageReceiver, new IntentFilter(getResources().getString(R.string.bus_data))
            );
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        LatLngBounds bounds = new LatLngBounds(new LatLng(36.976343, -122.072109), new LatLng(37.004803, -122.041124));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, 0));
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(selectMarker != null) {
                    selectMarker.hideInfoWindow();
                    if(selectMarker.equals(marker)) {
                        selectMarker = null;
                        return true;
                    }
                }
                marker.showInfoWindow();
                selectMarker = marker;
                return true;
            }
        });
        drawBusStops();
    }

    private boolean checkGoogleService() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            showDialog("Please update your google play services", "OK", true);
        }
        return false;
    }

    //http://stackoverflow.com/questions/28168867/check-internet-status-from-the-main-activity
    private boolean checkNetwork() {
        // Check all connectivities whether available or not
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

    private void drawBusStops() {
        int height = 20, width = 20;

        for (BusStop b : mBusStops.getInnerLoopList()) {
            BitmapDrawable bitmapdraw = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.orange_stop, getTheme());
            Bitmap bit = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(bit, width, height, false);

            Marker m = mGoogleMap.addMarker(new MarkerOptions()
                    .position(b.getLoc())
                    .title(b.getName())
                    .anchor(0.5f,0.5f)
                    .snippet("Inner Loop")
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        }

        for (BusStop b : mBusStops.getOuterLoopList()) {
            BitmapDrawable bitmapdraw = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.blue_stop, getTheme());
            Bitmap bit = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(bit, width, height, false);

            Marker m = mGoogleMap.addMarker(new MarkerOptions()
                    .position(b.getLoc())
                    .title(b.getName())
                    .anchor(0.5f,0.5f)
                    .snippet("Outer Loop")
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        }
    }

    private void setBusList(String result) {
        mBusList.clear();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                mBusList.add(new Bus(jsonObject.getInt("id"), jsonObject.getDouble("lon"),
                        jsonObject.getDouble("lat"), jsonObject.getString("type")));
            }
        } catch (Exception e) {

        }
        updateBusMarkers();
    }

    private void updateBusMarkers() {
        if (mBusList.size() == 0) {
            if (firstRun) {
                showDialog("No busses are currently in service!", "OK");
                firstRun = false;
            }

            if (mBusMarkerList.size() != 0) {
                for (BusMarker m : mBusMarkerList) {
                    m.getMarker().remove();
                }
                mBusMarkerList.clear();
            }
            return;
        }

        //Create, remove, animate the latest bus markers
        for (Bus b : mBusList) {
            BusMarker mBusMarker = null;

            //Check if current bus already has a marker
            for (BusMarker m : mBusMarkerList) {
                if (m.getId() == b.getId()) {
                    //There is a existing marker for this bus
                    mBusMarker = m;

                    //Redraw the marker if the bus has a different type than its marker
                    if (!mBusMarker.getType().equals(b.getType())) {
                        mBusMarkerList.remove(mBusMarker);
                        mBusMarker.getMarker().remove();
                        mBusMarker = null;
                    }

                    break;
                }
            }

            //Check if the current bus marker exists
            if (mBusMarker != null) {
                //Yes, the current bus marker exists
                // Animate the marker to a new position
                mBusMarker.setLoc(b.getLoc());
                animateBusMarker(mBusMarker.getMarker(), mBusMarker.getLoc(), new LatLngInterpolator.Linear());
            } else {
                //No, the current bus marker does not exist
                //Create a new bus marker
                int icon;

                switch (b.getType().toUpperCase()) {
                    case "LOOP":
                        icon = R.drawable.loop;
                        break;
                    case "UPPER CAMPUS":
                        icon = R.drawable.uppercampus;
                        break;
                    case "NIGHT OWL":
                        icon = R.drawable.nightowl;
                        break;
                    case "LOOP OUT OF SERVICE AT BARN THEATER":
                    case "OUT OF SERVICE/SORRY":
                        icon = R.drawable.outofservice;
                        break;
                    default:
                        icon = R.drawable.special;
                        break;
                }

                Marker mMarker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(b.getLoc())
                        .title(b.getType())
                        .zIndex(0.5f)
                        .anchor(0.5f,0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(icon)));

                mBusMarkerList.add(new BusMarker(b.getId(), mMarker, b.getLoc(), b.getType()));
            }

        }

        //Remove off-line bus markers
        //To avoid java.util.ConcurrentModificationException, we need to use iterator.
        for (Iterator<BusMarker> iterator = mBusMarkerList.iterator(); iterator.hasNext();) {
            //Remove the current bus marker, unless the bus marker's id is in the bus list.
            boolean removeCurrBusMarker = true;
            BusMarker m = iterator.next();
            //Check if the current bus marker's id is in the bus list
            //If so, don't remove the current bus marker.
            for (Bus b : mBusList) {
                if (b.getId() == m.getId()) {
                    removeCurrBusMarker = false;
                    break;
                }
            }

            //Remove the current bus marker
            if (removeCurrBusMarker) {
                m.getMarker().remove();
                iterator.remove();
            }
        }

    }

    private void animateBusMarker(final Marker marker, LatLng finalPosition, final LatLngInterpolator latLngInterpolator) {
        TypeEvaluator<LatLng> typeEvaluator = new TypeEvaluator<LatLng>() {
            @Override
            public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
                return latLngInterpolator.interpolate(fraction, startValue, endValue);
            }
        };
        Property<Marker, LatLng> property = Property.of(Marker.class, LatLng.class, "position");
        ObjectAnimator animator = ObjectAnimator.ofObject(marker, property, typeEvaluator, finalPosition);
        animator.setDuration(500);
        animator.start();
    }

    private void showDialog(String msg, String confirmMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(confirmMsg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showDialog(String msg, String confirmMsg, final boolean finishActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(confirmMsg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (finishActivity) {
                            finish();
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
