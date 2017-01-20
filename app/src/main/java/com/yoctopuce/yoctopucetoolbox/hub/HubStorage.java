package com.yoctopuce.yoctopucetoolbox.hub;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yoctopuce.yoctopucetoolbox.data.DbSchemas;
import com.yoctopuce.yoctopucetoolbox.data.HubCursorWrapper;
import com.yoctopuce.yoctopucetoolbox.data.YoctoDbHelpere;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HubStorage
{
    private static HubStorage __instance;
    private final SQLiteDatabase _db;


    private final Hub _usbPseudoHub;

    public static HubStorage get(Context context)
    {
        if (__instance == null) {
            __instance = new HubStorage(context);
        }
        return __instance;
    }


    private HubStorage(Context context)
    {
        _db = new YoctoDbHelpere(context).getWritableDatabase();
        PackageManager packageManager = context.getPackageManager();
        boolean hasUSBHostSupport = packageManager != null && packageManager.hasSystemFeature(PackageManager.FEATURE_USB_HOST);
        if (hasUSBHostSupport) {
            _usbPseudoHub = new Hub(true);
        } else {
            _usbPseudoHub = null;
        }
    }

    public Hub getUsbPseudoHub()
    {
        return _usbPseudoHub;
    }

    public List<Hub> getHubs()
    {
        ArrayList<Hub> hubs = new ArrayList<>();

        HubCursorWrapper cursor = queryHubs(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            hubs.add(cursor.getHub());
            cursor.moveToNext();
        }
        if (_usbPseudoHub != null) {
            hubs.add(_usbPseudoHub);
        }

        cursor.close();
        return hubs;
    }

    private HubCursorWrapper queryHubs(String whereClause, String[] whereArgs)
    {
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


    public void addNewHub(Hub hub)
    {
        ContentValues contentValue = YoctoDbHelpere.getContentValue(hub);
        _db.insert(DbSchemas.HubsTable.NAME, null, contentValue);
    }

    public boolean contain(String serial)
    {
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

    public void remove(UUID uuid)
    {
        String whereClause = DbSchemas.HubsTable.Cols.UUID + " = ?";
        _db.delete(DbSchemas.HubsTable.NAME, whereClause, new String[]{uuid.toString()});
    }

    public Hub getHub(UUID uuid)
    {
        if (uuid.equals(_usbPseudoHub.getUuid())){
            return _usbPseudoHub;
        }

        HubCursorWrapper cursor = queryHubs(DbSchemas.HubsTable.Cols.UUID + " = ?", new String[]{uuid.toString()});
        final boolean moveToFirst = cursor.moveToFirst();
        if (!moveToFirst) {
            return null;
        }
        final Hub cursorHub = cursor.getHub();
        cursor.close();
        return cursorHub;
    }

    public int updateHub(Hub hub)
    {
        ContentValues values = YoctoDbHelpere.getContentValue(hub);
        // Which row to update, based on the title
        String selection = DbSchemas.HubsTable.Cols.UUID + " = ?";
        String[] selectionArgs = {hub.getUuid().toString()};
        return _db.update(
                DbSchemas.HubsTable.NAME,
                values,
                selection,
                selectionArgs);
    }
}
