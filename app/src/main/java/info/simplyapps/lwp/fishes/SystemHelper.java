package info.simplyapps.lwp.fishes;

import info.simplyapps.lwp.fishes.storage.StoreData;
import info.simplyapps.lwp.fishes.storage.dto.Inventory;

public class SystemHelper extends info.simplyapps.appengine.SystemHelper {

    public synchronized static final Inventory getInventory() {
        return StoreData.getInstance().inventory;
    }

}
