package com.example.jeffr_000.slugroute;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by jeffr_000 on 10/14/2016.
 */
public class Bus {
    private int id;
    private LatLng loc;
    private String type;

    public Bus(int id, double lon, double lat, String type) {
        this.id = id;
        loc = new LatLng(lat,lon);
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
