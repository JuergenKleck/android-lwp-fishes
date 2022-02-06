package com.juergenkleck.android.lwp.fishes.rendering.kits;

import com.juergenkleck.android.lwp.fishes.rendering.objects.FishAnimation;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class AnimationKit extends com.juergenkleck.android.gameengine.rendering.kits.AnimationKit {

    public static void addFishAnimation(FishAnimation animation, int gReference, int delay, int fishNumber, int schoolNumber) {
        addAnimation(animation, gReference, delay);
        animation.fishNumber = fishNumber;
        animation.schoolNumber = schoolNumber;
    }

}
