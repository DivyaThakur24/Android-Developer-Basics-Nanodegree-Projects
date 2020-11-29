package com.example.android.soundtrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Movies extends AppCompatActivity implements SearchView.OnQueryTextListener {

    Toolbar toolbar;
    Intent myIntent;
    Items_Adapter adapter;
    ActionBar actionBar;
    SearchView search;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_items);
        listView = (ListView) findViewById(R.id.list_categories);

        // Setting ToolBar as an ActionBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Creating BackUp Navigation
        actionBar = getSupportActionBar();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Create list of items
        final ArrayList<SongsOrder> orders = new ArrayList<>();
        orders.add(new SongsOrder(R.drawable.movies, R.string.movies_name_1));
        orders.add(new SongsOrder(R.drawable.movies, R.string.movies_name_2));
        orders.add(new SongsOrder(R.drawable.movies, R.string.movies_name_3));
        orders.add(new SongsOrder(R.drawable.movies, R.string.movies_name_4));
        orders.add(new SongsOrder(R.drawable.movies, R.string.movies_name_5));
        orders.add(new SongsOrder(R.drawable.movies, R.string.movies_name_6));
        orders.add(new SongsOrder(R.drawable.movies, R.string.movies_name_7));
        orders.add(new SongsOrder(R.drawable.movies, R.string.movies_name_8));
        orders.add(new SongsOrder(R.drawable.movies, R.string.movies_name_9));
        orders.add(new SongsOrder(R.drawable.movies, R.string.movies_name_10));
        orders.add(new SongsOrder(R.drawable.movies, R.string.movies_name_11));

        /** Create an Items_Adapter, whose data source is a list of (SongOrder).
         * Adapter creates list items for each item in the list.**/
        adapter = new Items_Adapter(this, orders);

        /** Finding ListView with the view ID called list_categories,
         * which is declared in the all_items.xml layout file.**/
        listView = (ListView) findViewById(R.id.list_categories);

        /** Make the  ListView use the  Items_Adapter, that was created above, so that the ListView
         * will display list of items for each SongOrder in the list.**/
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.getItemAtPosition(position);
                myIntent = new Intent(Movies.this, Music_listen.class);
                startActivity(myIntent);

            }
        });
    }

    /**
     * Tutorial used: https://www.youtube.com/watch?v=-H-I6v-mig8
     * to implement SearchView to the Toolbar we should implement SearchView.OnQueryTextListener to out class
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        search = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        search.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
