package com.example.divya.project3_quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Declaring EditText(s) and Button
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);
        final Button login = (Button) findViewById(R.id.login);

        //Setting OnclickListener on the Login Button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateName(name.getText().toString())){
                    name.setError("Only uppercase and lowercase alphabets allowed!");
                    name.requestFocus();
                }
                else if(!validateEmail(email.getText().toString())){
                    email.setError("Email must have @ and . symbols. No special characters allowed before @!");
                    email.requestFocus();
                }
                else if(!validatePassword(password.getText().toString())){
                    password.setError("Password must be at least 5 characters long!");
                    password.requestFocus();
                }
                else {
                    Toast.makeText(QuizActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(QuizActivity.this, QuestionsActivity.class);
                    myIntent.putExtra("name", name.getText().toString()); //Optional parameters
                    QuizActivity.this.startActivity(myIntent);
                }
            }
        });
    }

    //Returns true if name is Valid, Returns false if name is Invalid
    private boolean validateName(String name) {
        String namePattern = "^[A-Za-z._-]+$";
        if(name!=null && name.matches(namePattern)){
            return true;
        }
        return false;
    }

    //Returns true if email is Valid, Returns false if email is Invalid
    private boolean validateEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(email!=null && email.matches(emailPattern)){
            return true;
        }
        return false;
    }

    //Returns true if password is Valid , Returns false if password is Invalid
    private boolean validatePassword(String password) {
        if(password!=null && password.length()>=5){
            return true;
        }
        return false;
    }

}
