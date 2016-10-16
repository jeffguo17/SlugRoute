package com.example.jeffr_000.slugroute;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by jeffr_000 on 10/14/2016.
 */
public class BusStops {
    private ArrayList<BusStop> innerLoopList;
    private ArrayList<BusStop> outerLoopList;

    BusStops() {
        innerLoopList = new ArrayList();
        outerLoopList = new ArrayList();

        // Populate the inner loop stops
        innerLoopList.add(new BusStop(5, new LatLng( 36.9999313354492, -122.062049865723), "McLaughlin & Science Hill"));
        innerLoopList.add(new BusStop(2, new LatLng( 36.9967041015625, -122.063583374023), "Heller & Kerr Hall"));
        innerLoopList.add(new BusStop(3, new LatLng( 36.999210357666, -122.064338684082), "Heller & Kresge College"));
        innerLoopList.add(new BusStop(5, new LatLng( 36.9999313354492, -122.062049865723), "McLaughlin & Science Hill"));
        innerLoopList.add(new BusStop(6, new LatLng( 36.9997062683105, -122.05834197998), "McLaughlin & College 9 & 10 - Health Center"));
        innerLoopList.add(new BusStop(10, new LatLng( 36.9966621398926, -122.055480957031), "Hagar & Bookstore"));
        innerLoopList.add(new BusStop(13, new LatLng( 36.9912567138672, -122.054962158203), "Hagar & East Remote"));
        innerLoopList.add(new BusStop(15, new LatLng( 36.985523223877, -122.053588867188), "Hagar & Lower Quarry Rd"));
        innerLoopList.add(new BusStop(17, new LatLng( 36.9815368652344, -122.052131652832), "Coolidge & Hagar"));
        innerLoopList.add(new BusStop(18, new LatLng( 36.9787902832031, -122.057762145996), "High & Western Dr"));
        innerLoopList.add(new BusStop(20, new LatLng( 36.9773025512695, -122.054328918457), "High & Barn Theater"));
        innerLoopList.add(new BusStop(23, new LatLng( 36.9826698303223, -122.062492370605), "Empire Grade & Arboretum"));
        innerLoopList.add(new BusStop(26, new LatLng( 36.9905776977539, -122.066116333008), "Heller & Oakes College"));
        innerLoopList.add(new BusStop(29, new LatLng( 36.9927787780762, -122.064880371094), "Heller & College 8 & Porter"));

        // Outer loop stops
        outerLoopList.add(new BusStop(1, new LatLng( 36.9992790222168, -122.064552307129), "Heller & Kresge College"));
        outerLoopList.add(new BusStop(4, new LatLng( 37.0000228881836, -122.062339782715), "McLaughlin & Science Hill"));
        outerLoopList.add(new BusStop(7, new LatLng( 36.9999389648438, -122.058349609375), "McLaughlin & College 9 & 10 - Health Center"));
        outerLoopList.add(new BusStop(8, new LatLng( 36.9990234375, -122.055229187012), "McLaughlin & Crown College"));
        outerLoopList.add(new BusStop(9, new LatLng( 36.9974822998047, -122.055030822754), "Hagar & Bookstore-Stevenson College"));
        outerLoopList.add(new BusStop(11, new LatLng( 36.9942474365234, -122.055511474609), "Hagar & Field House East"));
        outerLoopList.add(new BusStop(12, new LatLng( 36.9912986755371, -122.054656982422), "Hagar & East Remote"));
        outerLoopList.add(new BusStop(14, new LatLng( 36.985912322998, -122.053520202637), "Hagar & Lower Quarry Rd"));
        outerLoopList.add(new BusStop(16, new LatLng( 36.9813537597656, -122.051971435547), "Coolidge & Hagar"));
        outerLoopList.add(new BusStop(19, new LatLng( 36.9776763916016, -122.053558349609), "Coolidge & Main Entrance"));
        outerLoopList.add(new BusStop(21, new LatLng( 36.9786148071289, -122.05785369873), "High & Western Dr"));
        outerLoopList.add(new BusStop(22, new LatLng( 36.9798469543457, -122.059257507324), "Empire Grade & Tosca Terrace"));
        outerLoopList.add(new BusStop(24, new LatLng( 36.9836616516113, -122.064964294434), "Empire Grade & Arboretum"));
        outerLoopList.add(new BusStop(25, new LatLng( 36.989917755127, -122.067230224609), "Heller & Oakes College"));
        outerLoopList.add(new BusStop(27, new LatLng( 36.991828918457, -122.066833496094), "Heller & Family Student Housing"));
        outerLoopList.add(new BusStop(28, new LatLng( 36.992977142334, -122.065223693848), "Heller & College 8 & Porter"));
    }

    public ArrayList<BusStop> getInnerLoopList() {
        return innerLoopList;
    }

    public ArrayList<BusStop> getOuterLoopList() {
        return outerLoopList;
    }

}