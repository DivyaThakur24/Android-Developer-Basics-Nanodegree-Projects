package justjava.example.android.project2_scorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    int pointsTeamA = 0;
    int pointsTeamB = 0;
    TextView teamA, teamB, desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        teamA = (TextView) findViewById(R.id.teamA);
        teamB = (TextView) findViewById(R.id.teamB);
        desc = (TextView) findViewById(R.id.desc);
    }

    /**
     * This method is called when Team A hits a Volley.
     */
    public void volleyA(View view) {
        pointsTeamA++;
        teamA.setText(pointsTeamA + "");
        displayText();
    }

    /**
     * This method is called when Team B hits a Volley.
     */
    public void volleyB(View view) {
        pointsTeamB++;
        teamB.setText(pointsTeamB + "");
        displayText();
    }

    /**
     * This method is called when Team A Misses hitting the Ping Pong Ball.
     */
    public void missA(View view) {
        pointsTeamB++;
        teamB.setText(pointsTeamB + "");
        displayText();
    }

    /**
     * This method is called when Team A Misses hitting the Ping Pong Ball.
     */
    public void missB(View view) {
        pointsTeamA++;
        teamA.setText(pointsTeamA + "");
        displayText();
    }

    /**
     * This method is called when the Game is Reset.
     */
    public void resetGame(View view) {
        pointsTeamA = 0;
        pointsTeamB = 0;
        teamA.setText(pointsTeamA + "");
        teamB.setText(pointsTeamB + "");
        desc.setText("Let's   Start");

    }

    /**
     * This method is called to Dynamically Display the Status of the Game.
     */
    public void displayText() {
        if(pointsTeamA == pointsTeamB && (pointsTeamA < 10 && pointsTeamB < 10)) {
            desc.setText("Scores Levelled");
        }
        else if(pointsTeamA == 11 && pointsTeamB < 11) {
            desc.setText("Team A has Won!");
        }
        else if(pointsTeamB == 11 && pointsTeamA < 11) {
            desc.setText("Team B has Won!");
        }
        else if(pointsTeamA == 10 && pointsTeamB == 10) {
            desc.setText("Deuce");
        }
        else if(pointsTeamA <12 && pointsTeamB <12) {
            if (pointsTeamA > pointsTeamB) {
                desc.setText("Team A is Ahead");
            } else if (pointsTeamB > pointsTeamA) {
                desc.setText("Team B is Ahead");
            }
        }
        else {
            desc.setText("Game Over");
            pointsTeamB = 0;
            pointsTeamA = 0;
            teamA.setText(pointsTeamA + "");
            teamB.setText(pointsTeamB + "");
        }
    }
}
