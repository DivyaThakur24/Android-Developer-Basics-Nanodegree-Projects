package com.example.divya.project3_quizapp;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuestionsActivity extends AppCompatActivity {

    private int scores = 0;
    CheckBox checkOne, checkTwo, checkThree, checkFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        //Receiving name from the Intent and Displaying it
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        TextView greeting = (TextView) findViewById(R.id.greeting);
        greeting.setText("Hi " + name + "!");

        //Working with First Radio Group || First Question
        final RadioGroup firstGroup = (RadioGroup) findViewById(R.id.firstGroup);
        final RadioButton first = (RadioButton) findViewById(R.id.first);
        final RadioButton default_first = (RadioButton) findViewById(R.id.default_first);

        firstGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                default_first.setChecked(false);
                if (i == R.id.first) {
                    scores++;
                }
            }
        });

        //Working with Second Radio Group || Second Question
        final RadioGroup secondGroup = (RadioGroup) findViewById(R.id.secondGroup);
        final RadioButton second = (RadioButton) findViewById(R.id.second);
        final RadioButton default_second = (RadioButton) findViewById(R.id.default_second);

        secondGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                default_second.setChecked(false);
                if (i == R.id.second) {
                    scores++;
                }
            }
        });

        //Working with Third Radio Group || Third Question
        final RadioGroup thirdGroup = (RadioGroup) findViewById(R.id.thirdGroup);
        final RadioButton third = (RadioButton) findViewById(R.id.third);
        final RadioButton default_third = (RadioButton) findViewById(R.id.default_third);

        thirdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                default_third.setChecked(false);
                if (i == R.id.third) {
                    scores++;
                }
            }
        });


        //Working with Fourth Radio Group || Fourth Question
        final RadioGroup fourthGroup = (RadioGroup) findViewById(R.id.fourthGroup);
        final RadioButton fourth = (RadioButton) findViewById(R.id.fourth);
        final RadioButton default_fourth = (RadioButton) findViewById(R.id.default_fourth);

        fourthGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                default_fourth.setChecked(false);
                if (i == R.id.fourth) {
                    scores++;
                }
            }
        });

        //Working with the Edit Text
        final EditText editText = (EditText) findViewById(R.id.sixth);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sixth = editText.getText().toString().trim();
                if (sixth.equalsIgnoreCase("1939")) {
                    scores++;
                }
            }
        });

        //Defining Global Variables || CheckBox Group || Fifth Question || onClick Method Used at the Last
        checkOne = (CheckBox) findViewById(R.id.checkOne);
        checkTwo = (CheckBox) findViewById(R.id.checkTwo);
        checkThree = (CheckBox) findViewById(R.id.checkThree);
        checkFour = (CheckBox) findViewById(R.id.checkFour);

        //Working with the Submit Button
        final Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) findViewById(R.id.score);
                textView.setVisibility(View.VISIBLE);
                textView.setText("You Scored: " + scores + " / 6");

            }
        });

    }

    //Working with Fifth Group of CheckBoxes || Fifth Question
    public void onCheckBoxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked || Increment scores when both checkTwo and checkThree have been checked
        if (!checkOne.isChecked() && checkTwo.isChecked() && checkThree.isChecked() && !checkFour.isChecked()) {
            scores++;
        }
    }
}
