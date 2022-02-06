package com.juergenkleck.android.lwp.fishes.storage;

import com.juergenkleck.android.appengine.AppEngineConstants;
import com.juergenkleck.android.appengine.storage.dto.Configuration;
import com.juergenkleck.android.lwp.fishes.SystemHelper;
import com.juergenkleck.android.lwp.fishes.storage.dto.Inventory;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class StoreData extends com.juergenkleck.android.appengine.storage.StoreData {

    public Inventory inventory;

    /**
     *
     */
    private static final long serialVersionUID = 2982830586304674266L;

    public StoreData() {
        super();
        if (inventory == null) {
            inventory = new Inventory();
        }
    }

    public static StoreData getInstance() {
        return (StoreData) com.juergenkleck.android.appengine.storage.StoreData.getInstance();
    }

    @Override
    public boolean update() {
        boolean persist = false;

        // For initial data creation
        if (migration < 1) {
            persist = true;
        }

        // Release 5 - 1.1.0
        if (migration < 5) {
            SystemHelper.setConfiguration(new Configuration(AppEngineConstants.CONFIG_ON_SERVER, AppEngineConstants.DEFAULT_CONFIG_ON_SERVER));
            SystemHelper.setConfiguration(new Configuration(AppEngineConstants.CONFIG_FORCE_UPDATE, AppEngineConstants.DEFAULT_CONFIG_FORCE_UPDATE));
            SystemHelper.setConfiguration(new Configuration(AppEngineConstants.CONFIG_LAST_CHECK, AppEngineConstants.DEFAULT_CONFIG_LAST_CHECK));
            persist = true;
        }

        migration = 5;
        return persist;
    }

}
