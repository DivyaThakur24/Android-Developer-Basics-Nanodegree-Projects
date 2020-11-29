package com.example.csaba.newsappstage2;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class EventLoader extends AsyncTaskLoader<List<Event>> {

    /** Tag for log messages */
    private static final String LOG_TAG = EventLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link EventLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public EventLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Event> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Event> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }

}
