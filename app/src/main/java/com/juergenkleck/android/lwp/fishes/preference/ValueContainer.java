package com.juergenkleck.android.lwp.fishes.preference;

import java.util.HashMap;
import java.util.Map;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class ValueContainer extends com.juergenkleck.android.gameengine.rendering.data.ValueContainer {

    public String wallpaper;
    public Map<String, Boolean> fishes;
    public boolean swimSchool;
    public boolean swimRealistic;
    public int swimSpeed;

    public ValueContainer() {
        fishes = new HashMap<String, Boolean>();
    }

}
