package com.yoctopuce.yoctopucetoolbox.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yoctopuce.yoctopucetoolbox.hub.Hub;

public class YoctoDbHelpere extends SQLiteOpenHelper
{
    private static final int VERSION_NO_NAME = 1;
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "yocto.db";

    public YoctoDbHelpere(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + DbSchemas.HubsTable.NAME + "(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENt," +
                DbSchemas.HubsTable.Cols.UUID + " TEXT, " +
                DbSchemas.HubsTable.Cols.SERIAL + " TEXT, " +
                DbSchemas.HubsTable.Cols.NAME + " TEXT, " +
                DbSchemas.HubsTable.Cols.HOST + " TEXT, " +
                DbSchemas.HubsTable.Cols.PORT + " INTEGER, " +
                DbSchemas.HubsTable.Cols.USER + " TEXT, " +
                DbSchemas.HubsTable.Cols.PASS + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (newVersion == VERSION && oldVersion == VERSION_NO_NAME) {
            db.execSQL("ALTER TABLE " + DbSchemas.HubsTable.NAME + " ADD COLUMN " + DbSchemas.HubsTable.Cols.NAME + " TEXT");
        }
    }


    public static ContentValues getContentValue(Hub hub)
    {
        ContentValues values = new ContentValues();
        values.put(DbSchemas.HubsTable.Cols.UUID, hub.getUuid().toString());
        values.put(DbSchemas.HubsTable.Cols.SERIAL, hub.getSerial());
        values.put(DbSchemas.HubsTable.Cols.NAME, hub.getName());
        values.put(DbSchemas.HubsTable.Cols.HOST, hub.getHost());
        values.put(DbSchemas.HubsTable.Cols.PORT, hub.getPort());
        values.put(DbSchemas.HubsTable.Cols.USER, hub.getUser());
        values.put(DbSchemas.HubsTable.Cols.PASS, hub.getPass());
        return values;
    }

}
