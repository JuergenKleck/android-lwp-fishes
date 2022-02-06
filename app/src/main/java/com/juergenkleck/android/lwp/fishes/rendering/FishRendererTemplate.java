package com.juergenkleck.android.lwp.fishes.rendering;

import android.content.Context;

import java.util.Properties;

import com.juergenkleck.android.gameengine.rendering.GenericRendererTemplate;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public abstract class FishRendererTemplate extends GenericRendererTemplate {

    public FishRendererTemplate(Context context, Properties properties) {
        super(context, properties);
    }

    public boolean logEnabled() {
        return false;
    }

}
