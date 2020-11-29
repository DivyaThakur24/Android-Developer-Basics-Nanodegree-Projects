package com.example.android.newsapp;

public class NewsArticle {

    private String mTitle;

    private String mAuthor;

    private String mSection;

    private String mDate;

    private String mUrl;

    public NewsArticle(String title, String author, String section, String date, String url) {

        mTitle = title;
        mAuthor = author;
        mSection = section;
        mDate = date;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() { return mAuthor; }

    public String getSection() {
        return mSection;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
