package com.example.jeffr_000.slugroute;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by jeffr_000 on 10/14/2016.
 */
public class BusMarker {
    private int id;
    private Marker marker;
    private LatLng loc;
    private String type;

    public BusMarker(int id, Marker marker, LatLng loc, String type) {
        this.id = id;
        this.marker = marker;
        this.loc = loc;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public LatLng getLoc() {
        return loc;
    }

    public void setLoc(LatLng loc) {
        this.loc = loc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
