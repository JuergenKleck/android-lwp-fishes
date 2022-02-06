package com.juergenkleck.android.lwp.fishes.storage;

import android.provider.BaseColumns;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class StorageContract extends
        com.juergenkleck.android.appengine.storage.StorageContract {

    public static abstract class TableInventory implements BaseColumns {
        public static final String TABLE_NAME = "inventory";
        public static final String COLUMN_MIGRATED = "migrated";
    }

}
