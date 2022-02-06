package com.juergenkleck.android.lwp.fishes.rendering.kits;

import com.juergenkleck.android.lwp.fishes.rendering.objects.FishObject;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class Renderkit extends com.juergenkleck.android.gameengine.rendering.kits.Renderkit {

    public static FishObject loadFishObject(int clickAction) {
        FishObject obj = new FishObject();
        obj.clickAction = clickAction;
        return obj;
    }

}
