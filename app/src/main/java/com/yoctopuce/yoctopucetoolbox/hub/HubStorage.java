package com.yoctopuce.yoctopucetoolbox.hub;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yoctopuce.yoctopucetoolbox.data.DbSchemas;
import com.yoctopuce.yoctopucetoolbox.data.HubCursorWrapper;
import com.yoctopuce.yoctopucetoolbox.data.YoctoDbHelpere;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HubStorage {
    private static HubStorage __instance;
    private final SQLiteDatabase _db;

    public static HubStorage get(Context context) {
        if (__instance == null) {
            __instance = new HubStorage(context);
        }
        return __instance;
    }


    private HubStorage(Context context) {
        _db = new YoctoDbHelpere(context).getWritableDatabase();
    }

    public List<Hub> getHubs() {
        ArrayList<Hub> hubs = new ArrayList<>();

        HubCursorWrapper cursor = queryHubs(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            hubs.add(cursor.getHub());
            cursor.moveToNext();
        }
        cursor.close();
        return hubs;
    }

    //todo: migrate db related stuff to db helper
    private ContentValues getContentValue(Hub hub) {
        ContentValues values = new ContentValues();
        values.put(DbSchemas.HubsTable.Cols.UUID, hub.getUuid().toString());
        values.put(DbSchemas.HubsTable.Cols.SERIAL, hub.getSerial());
        values.put(DbSchemas.HubsTable.Cols.HOST, hub.getHost());
        values.put(DbSchemas.HubsTable.Cols.PORT, hub.getPort());
        values.put(DbSchemas.HubsTable.Cols.USER, hub.getUser());
        values.put(DbSchemas.HubsTable.Cols.PASS, hub.getPass());
        return values;
    }

    private HubCursorWrapper queryHubs(String whereClause, String[] whereArgs) {
        @SuppressLint("Recycle") Cursor cursor = _db.query(
                DbSchemas.HubsTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new HubCursorWrapper(cursor);
    }


    public void addNewHub(Hub hub) {
        ContentValues contentValue = getContentValue(hub);
        _db.insert(DbSchemas.HubsTable.NAME, null, contentValue);
    }

    public boolean contain(String serial) {
        HubCursorWrapper cursor = queryHubs(
                DbSchemas.HubsTable.Cols.SERIAL + " = ?",
                new String[]{serial}
        );

        try {
            if (cursor.getCount() == 0) {
                return false;
            }

            cursor.moveToFirst();
            return true;
        } finally {
            cursor.close();
        }
    }

    public void remove(UUID uuid) {
        String whereClause = DbSchemas.HubsTable.Cols.UUID + " = ?";
        _db.delete(DbSchemas.HubsTable.NAME, whereClause, new String[]{uuid.toString()});
    }
}
