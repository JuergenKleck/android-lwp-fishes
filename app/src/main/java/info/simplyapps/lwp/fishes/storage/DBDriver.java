package info.simplyapps.lwp.fishes.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import info.simplyapps.appengine.storage.dto.BasicTable;
import info.simplyapps.lwp.fishes.storage.dto.Inventory;

public class DBDriver extends info.simplyapps.appengine.storage.DBDriver {

    private static final String SQL_CREATE_INVENTORY =
            "CREATE TABLE " + StorageContract.TableInventory.TABLE_NAME + " (" +
                    StorageContract.TableInventory._ID + " INTEGER PRIMARY KEY," +
                    StorageContract.TableInventory.COLUMN_MIGRATED + TYPE_INT +
                    " );";
    private static final String SQL_DELETE_INVENTORY =
            "DROP TABLE IF EXISTS " + StorageContract.TableInventory.TABLE_NAME;

    public DBDriver(String dataBaseName, int dataBaseVersion, Context context) {
        super(dataBaseName, dataBaseVersion, context);
    }

    public static DBDriver getInstance() {
        return (DBDriver) info.simplyapps.appengine.storage.DBDriver.getInstance();
    }

    @Override
    public void createTables(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_INVENTORY);
    }


    @Override
    public void upgradeTables(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(SQL_DELETE_INVENTORY);
    }

    @Override
    public String getExtendedTable(BasicTable data) {
        return Inventory.class.isInstance(data) ? StorageContract.TableInventory.TABLE_NAME : null;
    }

    @Override
    public void storeExtended(info.simplyapps.appengine.storage.StoreData data) {
        store(StoreData.class.cast(data).inventory);
    }

    @Override
    public void readExtended(info.simplyapps.appengine.storage.StoreData data, SQLiteDatabase db) {
        readInventory(StoreData.class.cast(data), db);
    }

    @Override
    public info.simplyapps.appengine.storage.StoreData createStoreData() {
        return new StoreData();
    }

    public boolean store(Inventory data) {
        ContentValues values = new ContentValues();
        values.put(StorageContract.TableInventory.COLUMN_MIGRATED, data.migrated);
        return persist(data, values, StorageContract.TableInventory.TABLE_NAME);
    }

    private void readInventory(StoreData data, SQLiteDatabase db) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StorageContract.TableInventory._ID,
                StorageContract.TableInventory.COLUMN_MIGRATED
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = null;
        String selection = null;
        String[] selectionArgs = null;
        Cursor c = db.query(
                StorageContract.TableInventory.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        boolean hasResults = c.moveToFirst();
        while (hasResults) {
            Inventory i = new Inventory();
            i.id = c.getLong(c.getColumnIndexOrThrow(StorageContract.TableInventory._ID));
            i.migrated = c.getInt(c.getColumnIndexOrThrow(StorageContract.TableInventory.COLUMN_MIGRATED)) == 1;
            data.inventory = i;
            hasResults = c.moveToNext();
        }
        c.close();
    }

}
