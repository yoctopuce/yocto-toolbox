package com.yoctopuce.yoctopucetoolbox.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class YoctoDbHelpere extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "yocto.db";

    public YoctoDbHelpere(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + DbSchemas.HubsTable.NAME + "(" +
                " _id integer primary key autoincrement," +
                DbSchemas.HubsTable.Cols.UUID + ", " +
                DbSchemas.HubsTable.Cols.SERIAL + ", " +
                DbSchemas.HubsTable.Cols.HOST + ", " +
                DbSchemas.HubsTable.Cols.PORT + ", " +
                DbSchemas.HubsTable.Cols.USER + ", " +
                DbSchemas.HubsTable.Cols.PASS + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }


}
