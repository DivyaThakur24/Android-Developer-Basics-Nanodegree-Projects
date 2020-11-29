package com.example.csaba.newsappstage2;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    /**
     * Return a list of objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Event> extractNewsFromJson (String newsJson) {
        if (TextUtils.isEmpty(newsJson)) {
            return null;
        }

        List<Event> news = new ArrayList<>();

        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(newsJson);

            JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");

            // Extract the JSONArray associated with the key called "results"
            JSONArray resultsArray = responseJSONObject.getJSONArray("results");

            int length = resultsArray.length();

            // If there are results in the features array
            if (resultsArray.length() > 0) {

                for (int i = 0; i < length; i++) {

                    String author = "";

                    // Get a single article at position i within the list of articles
                    JSONObject currentArticle = resultsArray.getJSONObject(i);

                    // Extract out the title, time, and values
                    String webTitle = currentArticle.getString("webTitle");
                    String webUrl = currentArticle.getString("webUrl");
                    String sectionName = currentArticle.getString("sectionName");
                    String date = currentArticle.getString("webPublicationDate");
                    /**split date string to data and time. I am using date only*/
                    String[] parts = date.split("T");
                    /**new date is the part before the "T" */
                    date = parts[0];

                    /**get tags array and author object*/
                    if (currentArticle.has("tags")) {
                        JSONArray tagsArray = currentArticle.getJSONArray("tags");
                        if (tagsArray.length() > 0) {
                            JSONObject newsTag = tagsArray.getJSONObject(0);
                            if (newsTag.has("webTitle")) {
                                author = newsTag.getString("webTitle");
                            }
                        }
                    }

                    news.add(new Event(webTitle, webUrl, sectionName, date, author));

                }
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return news;
    }

    /**
     * create URL
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            return null;
        }
        return url;
    }

    /**
     * make Http request
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                //Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * read from stream
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Query the USGS dataset and return a list of objects.
     *
     *
     * Add in the fetchEarthquakeData() helper method that ties all the steps together -
     * creating a URL, sending the request, processing the response.
     * Since this is the only “public” QueryUtils method that the EarthquakeAsyncTask needs to interact with,
     * make all other helper methods in QueryUtils “private”.
     */
    public static List<Event> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            // Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Event}s
        List<Event> news = extractNewsFromJson(jsonResponse);

        // Return the list of {@link Event}s
        return news;
    }

}
