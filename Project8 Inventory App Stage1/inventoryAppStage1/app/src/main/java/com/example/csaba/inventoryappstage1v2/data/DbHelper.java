package com.example.csaba.inventoryappstage1v2.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.csaba.inventoryappstage1v2.data.Contract.Entry;


/**
 * Database helper for the app. Manages database creation and version management.
 */

public class DbHelper extends SQLiteOpenHelper {


    /** Name of the database file */
    private static final String DATABASE_NAME = "inventory.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link DbHelper}.
     *
     * @param context of the app
     */
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_TABLE =  "CREATE TABLE " + Contract.Entry.TABLE_NAME + " ("
                + Contract.Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.Entry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + Contract.Entry.COLUMN_PRICE + " INTEGER NOT NULL DEFAULT 1, "
                + Contract.Entry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + Contract.Entry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL,"
                + Contract.Entry.COLUMN_SUPPLIER_PHONE + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TABLE);
        //https://www.youtube.com/watch?time_continue=57&v=xux7pxDHqBs
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
