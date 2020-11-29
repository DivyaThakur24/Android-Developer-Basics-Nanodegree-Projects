package com.example.csaba.inventoryappstage2;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.csaba.inventoryappstage2.data.InventoryContract;
import com.example.csaba.inventoryappstage2.data.InventoryContract.InventoryEntry;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ITEM_LOADER = 0;
    InventoryCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.main_title));

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });


        ListView inventoryListView = findViewById(R.id.list);

        /**Find and set empty view on the ListView, so that it only shows when the list has 0 items.*/
        View emptyView = findViewById(R.id.empty_view);
        inventoryListView.setEmptyView(emptyView);

        /**setup an adapter*/
        mCursorAdapter = new InventoryCursorAdapter(this, null);
        inventoryListView.setAdapter(mCursorAdapter);

        /**set up item click listener, it directs us to editor activity to change the selected pet data*/
        inventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                /**add the clicked pet uri to the intent*/
                Uri currentInventoryUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);
                intent.setData(currentInventoryUri);

                startActivity(intent);
            }
        });


        /**kick off the loader*/
        getLoaderManager().initLoader(ITEM_LOADER, null, this);

    }


    /**
     * delete all item menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_entries:
                deleteAllItems();
                return true;
            case R.id.action_insert_dummy_data:
                insertDummy();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllItems() {
        int rowsDeleted = getContentResolver().delete(InventoryEntry.CONTENT_URI, null, null);
        Toast.makeText(this, "all pets deleted", Toast.LENGTH_LONG).show();
    }


    /**
     * Helper method to insert hardcoded dummy data into the database.
     */
    private void insertDummy() {

        // Create a ContentValues object where column names are the keys,
        // and dummy data is the attributes are the values.
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME, "chocolate bar");
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, "5");
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, 10);
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME, "choco factory");
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE, 55547854);

        /** Insert a new row into the provider using the ContentResolver.*/
        Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);
    }


    /**
     * Inflate the menu options from the res/menu/menu_catalog.xml file. This adds menu items to the app bar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                InventoryContract.InventoryEntry._ID,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryContract.InventoryEntry.COLUMN_PRICE,
                InventoryContract.InventoryEntry.COLUMN_QUANTITY
        };

        return new CursorLoader(this,                     // Parent activity context
                InventoryContract.InventoryEntry.CONTENT_URI,   // Provider content URI to query
                projection,                                     // Columns to include in the resulting Cursor
                null,                                   // No selection clause
                null,                                   // No selection arguments
                null);                                    // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}















