package com.example.android.soundtrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MelodyFragment extends Fragment {

    Intent myIntent;
    View view;
    ImageView movie;
    ImageView cartoon;
    ImageView series;
    ImageView anime;

    public MelodyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_melody, container, false);
        movie = (ImageView) view.findViewById(R.id.movies);
        cartoon = (ImageView) view.findViewById(R.id.cartoons);
        series = (ImageView) view.findViewById(R.id.series);
        anime = (ImageView) view.findViewById(R.id.anime);
        //return view;

        movie.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the Movies category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the Movies Activity
                myIntent = new Intent(getContext(), Movies.class);

                // Start the new activity
               startActivity(myIntent);
            }

        });
        //return view;

        cartoon.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the Cartoons category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the Cartoons Activity
                myIntent = new Intent(getContext(), Cartoons.class);

                // Start the new activity
                startActivity(myIntent);
            }

        });

        series.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the Series category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the Series Activity
                myIntent = new Intent(getContext(), Series.class);

                // Start the new activity
                startActivity(myIntent);
            }

        });

        anime.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the Anime category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the Anime Activity
                myIntent = new Intent(getContext(), Anime.class);

                // Start the new activity
                startActivity(myIntent);
            }

        });

        return view;
    }
}


