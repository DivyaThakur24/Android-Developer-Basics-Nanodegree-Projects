package com.example.csaba.inventoryappstage1v2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csaba.inventoryappstage1v2.data.Contract;
import com.example.csaba.inventoryappstage1v2.data.DbHelper;

import com.example.csaba.inventoryappstage1v2.data.Contract.Entry;


public class CatalogActivity extends AppCompatActivity {

    private DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new DbHelper(this);
    }


    /**
     * when we come back to the catalog activity from EditorActivity we refresh the page
     */
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the database.
     */
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.

        // Create and/or open a database to read from it
        //like in sqlite: .open shelter.db
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        ////Cursor cursor = db.rawQuery("SELECT * FROM " + PetEntry.TABLE_NAME, null);


        String[] projection = {
                Contract.Entry._ID,
                Contract.Entry.COLUMN_PRODUCT_NAME,
                Contract.Entry.COLUMN_PRICE,

        };

        Cursor cursor = db.query(Contract.Entry.TABLE_NAME, projection, null, null, null, null, null);


        TextView displayView = (TextView) findViewById(R.id.list);


        try {
            // Create a header in the Text View that looks like this:
            //
            // The table contains <number of rows in Cursor> pets.
            // _id - name -
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The table contains " + cursor.getCount() + " products.\n\n");
            displayView.append(Entry._ID + " - " +
                    Entry.COLUMN_PRODUCT_NAME + " - " +
                    Entry.COLUMN_PRICE + " - " + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(Entry._ID);
            int nameColumnIndex = cursor.getColumnIndex(Entry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(Entry.COLUMN_PRICE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " + "$" + currentPrice));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        /**res/menu items are inflated here*/
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.refresh:
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.delete:
                // Do nothing for now
                Toast.makeText(this, "currently not working", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
