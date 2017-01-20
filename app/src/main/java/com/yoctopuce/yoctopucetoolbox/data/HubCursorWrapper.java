package com.yoctopuce.yoctopucetoolbox.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.yoctopuce.yoctopucetoolbox.hub.Hub;

import java.util.UUID;

/**
 * Created by Seb on 25.04.2016.
 */
public class HubCursorWrapper extends CursorWrapper
{
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public HubCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Hub getHub()
    {
        String uuidString = getString(getColumnIndex(DbSchemas.HubsTable.Cols.UUID));
        String serial = getString(getColumnIndex(DbSchemas.HubsTable.Cols.SERIAL));
        final int columnIndex = getColumnIndex(DbSchemas.HubsTable.Cols.NAME);
        String name = getString(columnIndex);
        if (name == null) {
            // dirty fix for db update from v1 to v2
            name = "";
        }
        String host = getString(getColumnIndex(DbSchemas.HubsTable.Cols.HOST));
        int port = getInt(getColumnIndex(DbSchemas.HubsTable.Cols.PORT));
        String user = getString(getColumnIndex(DbSchemas.HubsTable.Cols.USER));
        String pass = getString(getColumnIndex(DbSchemas.HubsTable.Cols.PASS));
        return new Hub(UUID.fromString(uuidString), serial, name, host, port, user, pass);
    }
}
