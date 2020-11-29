package com.example.csaba.newsappstage2;

public class Event {

    public final String title;
    public final String url;
    public final String section;
    public final String date;
    public final String author;


    public Event(String eventTitle, String eventUrl, String eventSection, String eventDate, String eventAuthor) {
        title = eventTitle;
        url = eventUrl;
        section = eventSection;
        date = eventDate;
        author = eventAuthor;
    }

    public String getTitle() {
        return title;
    }
    public String getUrl() {
        return url;
    }
    public String getSection () {return section; }
    public String getDate() {return date;}
    public String getAuthor() {return author;}


}