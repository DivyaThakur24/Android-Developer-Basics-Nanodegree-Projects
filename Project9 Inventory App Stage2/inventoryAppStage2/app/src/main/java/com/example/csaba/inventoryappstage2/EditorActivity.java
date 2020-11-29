package com.example.csaba.inventoryappstage2;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csaba.inventoryappstage2.data.InventoryContract;

public class EditorActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_INVENTORY_LOADER = 0;

    private Uri mCurrentInventoryUri;

    private EditText mProductName;
    private EditText mPrice;
    private EditText mQuantity;
    private EditText mSupplierName;
    private EditText mPhoneNumber;

    private String nameProduct;
    private String priceString;
    private String quantityString;
    private String nameSupplier;
    private String phoneString;

    int quantity;

    /**
     * Boolean flag that keeps track of whether the pet has been edited (true) or not (false)
     */
    private boolean mItemHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mItemHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        setTitle(getString(R.string.edit_item));


        //get data from Catalog activity
        Intent intent = getIntent();
        mCurrentInventoryUri = intent.getData();

        //if currentPetUri does not contain any data than we are coming from the create new
        //pet. if the intent contains data than we are in editor mode.
        if (mCurrentInventoryUri == null) {
            setTitle("Add a new item");
            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a pet that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {
            setTitle(getString(R.string.edit_item));

            // Initialize a loader to read the data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_INVENTORY_LOADER, null, this);
        }

        // Find all relevant views that we will need to read user input from
        mProductName = (EditText) findViewById(R.id.edit_product_name);
        mPrice = (EditText) findViewById(R.id.edit_price);
        mQuantity = (EditText) findViewById(R.id.edit_quantity);
        mSupplierName = (EditText) findViewById(R.id.edit_supplier_name);
        mPhoneNumber = (EditText) findViewById(R.id.edit_phone_number);

        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mProductName.setOnTouchListener(mTouchListener);
        mPrice.setOnTouchListener(mTouchListener);
        mQuantity.setOnTouchListener(mTouchListener);
        mSupplierName.setOnTouchListener(mTouchListener);
        mPhoneNumber.setOnTouchListener(mTouchListener);


        /**minus button*/
        Button minus = findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantityString = mQuantity.getText().toString().trim();

                /**if quantity textfield is empty make quantity 0 and send a toast message below */
                if (TextUtils.isEmpty(quantityString)) {
                    quantity = 0;
                } else {
                    quantity = Integer.parseInt(quantityString);
                }

                if (quantity > 0) {
                    quantity = Integer.parseInt(quantityString);
                } else {
                    Toast.makeText(EditorActivity.this, "quantity must be a valid number", Toast.LENGTH_SHORT).show();
                    return;
                }
                mQuantity.setText(String.valueOf(quantity - 1));
            }
        });
        /**plus button*/
        Button plus = findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantityString = mQuantity.getText().toString().trim();

                /**checking if quantity textfield is empty */
                if (TextUtils.isEmpty(quantityString)) {
                    quantity = 0;
                } else {
                    quantity = Integer.parseInt(quantityString);
                }
                mQuantity.setText(String.valueOf(quantity + 1));
            }
        });

        /**call supplier button*/
        Button call = findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = mPhoneNumber.getText().toString().trim();
                /**valid phone number definition
                 * "+" is optional "?" indicate the optionality
                 * must be 0-9 characters only
                 * min number is 8, max number is 15*/
                String validNumber = "^[+]?[0-9]{8,15}$";

                if (number.matches(validNumber)) {
                    Uri call = Uri.parse("tel:" + number);
                    Intent intent = new Intent(Intent.ACTION_DIAL, call);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    return;
                } else {
                    Toast.makeText(EditorActivity.this, "no phone number available", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void saveItem() {

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        nameProduct = mProductName.getText().toString().trim();
        priceString = mPrice.getText().toString().trim();
        quantityString = mQuantity.getText().toString().trim();
        nameSupplier = mSupplierName.getText().toString().trim();
        phoneString = mPhoneNumber.getText().toString().trim();


        // Check if this is supposed to be a new item
        // and check if all the fields in the editor are blank
        if (mCurrentInventoryUri == null &&
                TextUtils.isEmpty(nameProduct) && TextUtils.isEmpty(priceString) &&
                TextUtils.isEmpty(quantityString) && TextUtils.isEmpty(nameSupplier) && TextUtils.isEmpty(phoneString)) {
            // Since no fields were modified, we can return early without creating a new item.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }


        // Create a ContentValues object where column names are the keys,
        // and item attributes from the editor are the values.
        int price = 0;
        int quantity = 0;
        int phone = 0;

        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME, nameProduct);

        if (!TextUtils.isEmpty(priceString)) {
            price = Integer.parseInt(priceString);
        }
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, price);

        if (!TextUtils.isEmpty(quantityString)) {
            quantity = Integer.parseInt(quantityString);
        }
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, quantity);

        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME, nameSupplier);

        if (!TextUtils.isEmpty(phoneString)) {
            phone = Integer.parseInt(phoneString);
        }
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE, phone);


        /**Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not*/
        if (mCurrentInventoryUri == null) {
            // This is a NEW item, so insert a new item into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, values);


            if (newUri == null) {
                Toast.makeText(this, R.string.insert_item_failed, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.insert_item_successful, Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING item, so update the item with content URI: mCurrentInventoryUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentInventoryUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentInventoryUri, values, null, null);

            if (rowsAffected == 0) {
                Toast.makeText(this, R.string.insert_item_failed, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.insert_item_successful, Toast.LENGTH_SHORT).show();
            }
        }

    }


    /**
     * This adds menu items to the app bar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (mCurrentInventoryUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    /**
     * User clicked on a menu option in the app bar overflow menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:

                /**check if item has all the fields*/
                // Read from input fields
                // Use trim to eliminate leading or trailing white space
                nameProduct = mProductName.getText().toString().trim();
                priceString = mPrice.getText().toString().trim();
                quantityString = mQuantity.getText().toString().trim();
                nameSupplier = mSupplierName.getText().toString().trim();
                phoneString = mPhoneNumber.getText().toString().trim();

                if ((TextUtils.isEmpty(nameProduct) ||
                        TextUtils.isEmpty(priceString) ||
                        TextUtils.isEmpty(quantityString) ||
                        TextUtils.isEmpty(nameSupplier) ||
                        TextUtils.isEmpty(phoneString)))
                {
                    DialogInterface.OnClickListener discardButtonClickListener =
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                                }
                            };

                    showUnsavedChangesDialog(discardButtonClickListener);
                    return true;
                } else {
                saveItem();
                /**exit activity and jump back to catalog activity*/
                finish();
                return true;}
            case R.id.action_delete:
                /** Pop up confirmation dialog for deletion*/
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                if (!mItemHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // If the item hasn't changed, continue with handling back button press
        if (!mItemHasChanged) {
            super.onBackPressed();
            return;
        }
        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };
        showUnsavedChangesDialog(discardButtonClickListener);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the editor shows all item attributes, define a projection that contains
        // all columns from the inventory table
        String[] projection = {
                InventoryContract.InventoryEntry._ID,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryContract.InventoryEntry.COLUMN_PRICE,
                InventoryContract.InventoryEntry.COLUMN_QUANTITY,
                InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME,
                InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE};

        /**This loader will execute the ContentProvider's query method on a background thread*/
        return new CursorLoader(this,
                mCurrentInventoryUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME);
            int phoneColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            int price = cursor.getInt(priceColumnIndex);
            int quantity = cursor.getInt(quantityColumnIndex);
            String supplier = cursor.getString(supplierColumnIndex);
            int phone = cursor.getInt(phoneColumnIndex);

            // Update the views on the screen with the values from the database
            mProductName.setText(name);
            mPrice.setText(Integer.toString(price));
            mQuantity.setText(Integer.toString(quantity));
            mSupplierName.setText(supplier);
            mPhoneNumber.setText(Integer.toString(phone));

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mProductName.setText("");
        mPrice.setText("");
        mQuantity.setText("");
        mSupplierName.setText("");
        mPhoneNumber.setText("");
    }


    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     */
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_item);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the item.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the item
                deleteItem();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     * Perform the deletion of the pet in the database.
     */
    private void deleteItem() {
        // Only perform the delete if this is an existing pet.
        if (mCurrentInventoryUri != null) {
            // Call the ContentResolver to delete the item at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentInventoryUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentInventoryUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                Toast.makeText(this, R.string.delete_failed, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.deleted, Toast.LENGTH_SHORT).show();
            }
        }
        // Close the activity
        finish();
    }


}






