package com.example.csaba.newsappstage2;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(Context context, ArrayList<Event> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent) {
        View listViewItem = currentView;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Event currentEvent = getItem(position);

        /**set title*/
        TextView webTitle = (TextView) listViewItem.findViewById(R.id.webTitle);
        webTitle.setText(currentEvent.getTitle());

        /** set date*/
        TextView date = (TextView) listViewItem.findViewById(R.id.date);
        date.setText(currentEvent.getDate());

        /**set section*/
        TextView webSection = (TextView) listViewItem.findViewById(R.id.section);
        webSection.setText(currentEvent.getSection());

        /**set author*/
        TextView author = (TextView) listViewItem.findViewById(R.id.author);
        author.setText(currentEvent.getAuthor());

        /**set section background color*/
        GradientDrawable sectionCircle = (GradientDrawable) webSection.getBackground();
        /**use a helper method to set color*/
        int sectionColor = getColor(currentEvent.getSection());
        // Set the color on the magnitude circle
        sectionCircle.setColor(sectionColor);



        return listViewItem;

    }

    private int getColor (String eventSection){
        int colorResourceId;
        switch (eventSection) {
            case "Technology":
                colorResourceId = R.color.colorTech;
                break;
            case "World news":
                colorResourceId = R.color.colorWorldnews;
                break;
            case "Business":
                colorResourceId = R.color.colorBusiness;
                break;
            case "Politics":
                colorResourceId = R.color.colorPolitics;
                break;
            case "UK news":
                colorResourceId = R.color.colorUK;
                break;
            case "Life and style":
                colorResourceId = R.color.colorLife;
                break;
            default:
                colorResourceId = R.color.colorAccent;
        }

        return ContextCompat.getColor(getContext(), colorResourceId);
    }


}
