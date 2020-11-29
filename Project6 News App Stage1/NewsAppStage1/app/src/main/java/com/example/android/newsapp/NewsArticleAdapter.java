package com.example.android.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsArticleAdapter extends ArrayAdapter<NewsArticle> {

    private static final String DATE_SEPARATOR = "T";

    private static final String LOG_TAG = NewsArticleAdapter.class.getName();

    public NewsArticleAdapter(Activity context, List<NewsArticle> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                R.layout.news_list_item, parent, false);
        }

        NewsArticle currentArticle = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);

        titleView.setText(currentArticle.getTitle());

        TextView authorView = (TextView) listItemView.findViewById(R.id.author);

        authorView.setText(currentArticle.getAuthor());

        TextView sectionView = (TextView) listItemView.findViewById(R.id.section);

        sectionView.setText(currentArticle.getSection());

        String originalDate = currentArticle.getDate();

        String[] parts = originalDate.split(DATE_SEPARATOR);

        String date = parts[0];

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        dateView.setText(date);

        return listItemView;
    }
}
