package justjava.example.android.project2_scorekeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Start Splash Activity
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        //Close Splash Activity
        finish();
    }
}
