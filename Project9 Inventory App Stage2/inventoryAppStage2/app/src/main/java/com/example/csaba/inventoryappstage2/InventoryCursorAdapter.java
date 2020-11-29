package com.example.csaba.inventoryappstage2;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csaba.inventoryappstage2.data.InventoryContract;


public class InventoryCursorAdapter extends CursorAdapter {




    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {


        TextView nameTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);
        TextView quantityTextView = view.findViewById(R.id.quantity);

        int idColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_QUANTITY);

        // Read the pet attributes from the Cursor for the current pet
        final int id = cursor.getInt(idColumnIndex);
        String itemName = cursor.getString(nameColumnIndex);
        int price = cursor.getInt(priceColumnIndex);
        final int quantity = cursor.getInt(quantityColumnIndex);

        StringBuilder priceBuilder = new StringBuilder();
        priceBuilder.append("£ ").append(String.valueOf(price));

        if (TextUtils.isEmpty(itemName)) {
            itemName = context.getString(R.string.unknown);
        }

        nameTextView.setText(itemName);
        priceTextView.setText(priceBuilder.toString());
        quantityTextView.setText(String.valueOf(quantity));


        /**sold button onclick listener*/
        final Button sold = (Button) view.findViewById(R.id.sold);

        sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0) {
                    ContentValues values = new ContentValues();
                    Uri uri = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI, id);
                    values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, quantity - 1);

                    view.getContext().getContentResolver().update(uri, values, null, null);
                } else {
                    Toast.makeText(context, R.string.decrease_error, Toast.LENGTH_SHORT).show();
            }
        }

    });

    }
}
