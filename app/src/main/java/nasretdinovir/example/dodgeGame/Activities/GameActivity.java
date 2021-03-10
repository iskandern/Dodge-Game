package nasretdinovir.example.dodgeGame.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import nasretdinovir.example.dodgeGame.Game;

public class GameActivity extends AppCompatActivity {

    private Game game;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("GameActivity.java", "onCreate()");
        super.onCreate(savedInstanceState);

        game = new Game(this);
        score = 0;

        setContentView(game);
    }

    @Override
    protected void onPause() {
        Log.d("GameActivity.java", "onPause()");
        score = game.pause();

        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("GameActivity.java", "onResume()");
        super.onResume();

        if (score == 0) {
            return;
        }

        Intent intent = new Intent(this, GameEndActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }
}