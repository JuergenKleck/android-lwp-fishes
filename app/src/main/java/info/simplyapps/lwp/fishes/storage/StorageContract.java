package info.simplyapps.lwp.fishes.storage;

import android.provider.BaseColumns;

public class StorageContract extends
        info.simplyapps.appengine.storage.StorageContract {

    public static abstract class TableInventory implements BaseColumns {
        public static final String TABLE_NAME = "inventory";
        public static final String COLUMN_MIGRATED = "migrated";
    }

}
