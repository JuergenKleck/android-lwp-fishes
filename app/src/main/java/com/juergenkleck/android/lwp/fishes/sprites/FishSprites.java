package com.juergenkleck.android.lwp.fishes.sprites;

import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import com.juergenkleck.android.gameengine.rendering.objects.Graphic;
import com.juergenkleck.android.gameengine.sprites.ViewSprites;
import com.juergenkleck.android.lwp.fishes.rendering.objects.FishAnimation;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class FishSprites implements ViewSprites {

    // main
    public Graphic gBackground;
    public Paint pBackground;
    public Rect rBackground;

    public Graphic[] gFish;
    public List<FishAnimation> aFish;

    public FishSprites() {
        aFish = new ArrayList<FishAnimation>();
    }

    @Override
    public void init() {
    }

    @Override
    public void clean() {
    }

}
