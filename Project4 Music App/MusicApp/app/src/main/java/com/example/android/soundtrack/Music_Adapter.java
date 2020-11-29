package com.example.android.soundtrack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class Music_Adapter extends ArrayAdapter<SongsOrder> {

    Music_Adapter(Context context, ArrayList<SongsOrder> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.music_list, parent, false);
        }

        // Get the SongOrder object located at this position in the list
        SongsOrder ThisItem = getItem(position);

        assert ThisItem != null;
        TextView TitleText = (TextView) listItemView.findViewById(R.id.Artist);

        // Get the Artist and SongName from the current song object and set this list
        TitleText.setText(ThisItem.putTheTitle());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.trackPic);

        // Get the current song
        imageView.setImageResource(ThisItem.putTheImage());

        // Return the whole list item layout (containing 1 TextView and 1 ImageView) to show it in the ListView
        return listItemView;
    }
}
