package com.juergenkleck.android.lwp.fishes.rendering.objects;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class FishObject {

    // the click event
    public boolean init;
    // what happens on click
    public int clickAction;

    // the view angle in the 3d space
    // needed for rotation and transformation
//	public float angle;
    // the rotation of the object
//	public float rotation;

    public int[] gReferences;

    public FishObject() {
    }

}
