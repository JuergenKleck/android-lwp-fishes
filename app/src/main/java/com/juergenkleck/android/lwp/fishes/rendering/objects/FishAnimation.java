package com.juergenkleck.android.lwp.fishes.rendering.objects;

import com.juergenkleck.android.lwp.fishes.preference.FishesEnum;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class FishAnimation extends com.juergenkleck.android.gameengine.rendering.objects.Animation {

    public int fishNumber;
    // build a fish school
    public int schoolNumber;
    public long fishSpeed;
    // updated value
    public long moveTime;

    public int waypointLayer;
    public int waypoint;

    public float angle;
    public double distance;
    public float maxAngle;

    public int yMod;

    // swim in schools
    public long layertime;

    public FishesEnum type;

    public FishAnimation() {
        super();
    }

}
