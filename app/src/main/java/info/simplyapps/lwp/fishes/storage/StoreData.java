package info.simplyapps.lwp.fishes.storage;

import info.simplyapps.appengine.AppEngineConstants;
import info.simplyapps.appengine.storage.dto.Configuration;
import info.simplyapps.lwp.fishes.SystemHelper;
import info.simplyapps.lwp.fishes.storage.dto.Inventory;

public class StoreData extends info.simplyapps.appengine.storage.StoreData {

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
        return (StoreData) info.simplyapps.appengine.storage.StoreData.getInstance();
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
