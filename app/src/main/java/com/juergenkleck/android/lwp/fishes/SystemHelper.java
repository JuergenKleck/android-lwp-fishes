package com.juergenkleck.android.lwp.fishes;

import com.juergenkleck.android.lwp.fishes.storage.StoreData;
import com.juergenkleck.android.lwp.fishes.storage.dto.Inventory;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class SystemHelper extends com.juergenkleck.android.appengine.SystemHelper {

    public synchronized static final Inventory getInventory() {
        return StoreData.getInstance().inventory;
    }

}
