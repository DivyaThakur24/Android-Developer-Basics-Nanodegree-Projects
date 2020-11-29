package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class NewsArticleLoader extends AsyncTaskLoader<List<NewsArticle>> {

    private String mUrl;

    private List<NewsArticle> mData;

    private static final String LOG_TAG = NewsArticleLoader.class.getName();

    public NewsArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {

        if (mData != null) {
            deliverResult(mData);
        }
        else {
            forceLoad();
        }

    }

    @Override
    public List<NewsArticle> loadInBackground() {

        if (mUrl == null) {
            return null;
        }

        return QueryUtils.fetchNewsData(mUrl);

    }

    @Override
    public void deliverResult(List<NewsArticle> data) {
        mData = data;
        super.deliverResult(data);
    }
}
