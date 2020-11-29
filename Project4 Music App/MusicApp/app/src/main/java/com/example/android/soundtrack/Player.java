package com.example.android.soundtrack;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Player extends AppCompatActivity {

    ImageView imageStart;
    ImageView forward;
    ImageView back;
    ImageView mix;
    ImageView like;
    ImageView playlist;
    Toolbar toolbar;
    LayoutInflater inflater;
    Toast toast;
    View view;
    TextView textPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

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
        imageStart = (ImageView) findViewById(R.id.start);
        forward = (ImageView) findViewById(R.id.forward);
        back = (ImageView) findViewById(R.id.back);
        mix = (ImageView) findViewById(R.id.mix);
        like = (ImageView) findViewById(R.id.like);
        playlist = (ImageView) findViewById(R.id.playlist);
        imageStart.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_for_player));
        forward.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_for_player));
        back.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_for_player));
        mix.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_for_player));
        like.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_for_player));
        playlist.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_for_player));

        inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.toast_message, (ViewGroup) findViewById(R.id.toastPlayer));// Inflate the Layout
        view.setBackgroundResource(R.drawable.toast); // Set shape for Toast Message
        textPlayer = (TextView) view.findViewById(R.id.textDicrp3); // Set the Text to show in TextView
        textPlayer.setText(getResources().getText(R.string.text_3));
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0); // Set Toast on the Center of the screen
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }
}
