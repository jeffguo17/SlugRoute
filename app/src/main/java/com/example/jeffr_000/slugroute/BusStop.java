package com.example.jeffr_000.slugroute;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by jeffr_000 on 10/14/2016.
 */
public class BusStop {
    private int id;
    private LatLng loc;
    private String name;


    BusStop(int id, LatLng loc, String name)
    {
        this.id = id;
        this.loc = loc;
        this.name = name;
    }

    BusStop(int id, double lat, double lon, String name)
    {
        this(id, new LatLng(lat, lon), name);
    }

    public int getID()
    {
        return id;
    }

    public void setID(int id)
    {
        this.id = id;
    }

    public LatLng getLoc()
    {
        return loc;
    }

    public void setLoc(LatLng loc)
    {
        this.loc = loc;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
