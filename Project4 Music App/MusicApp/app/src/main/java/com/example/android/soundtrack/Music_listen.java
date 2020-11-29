package com.example.android.soundtrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Music_listen extends AppCompatActivity implements SearchView.OnQueryTextListener {

    Toolbar toolbar;
    Intent myIntent;
    Music_Adapter adapter;
    SearchView search;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_items);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

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

        final ArrayList<SongsOrder> orders = new ArrayList<>();
        orders.add(new SongsOrder(R.drawable.notes, R.string.artist_song));
        orders.add(new SongsOrder(R.drawable.notes, R.string.artist_song));
        orders.add(new SongsOrder(R.drawable.notes, R.string.artist_song));
        orders.add(new SongsOrder(R.drawable.notes, R.string.artist_song));
        orders.add(new SongsOrder(R.drawable.notes, R.string.artist_song));
        orders.add(new SongsOrder(R.drawable.notes, R.string.artist_song));
        orders.add(new SongsOrder(R.drawable.notes, R.string.artist_song));
        orders.add(new SongsOrder(R.drawable.notes, R.string.artist_song));
        orders.add(new SongsOrder(R.drawable.notes, R.string.artist_song));
        orders.add(new SongsOrder(R.drawable.notes, R.string.artist_song));
        orders.add(new SongsOrder(R.drawable.notes, R.string.artist_song));

        adapter = new Music_Adapter(this, orders);

        listView = (ListView) findViewById(R.id.list_categories);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.getItemAtPosition(position);
                myIntent = new Intent(Music_listen.this, Player.class);
                startActivity(myIntent);
            }
        });
    }

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

