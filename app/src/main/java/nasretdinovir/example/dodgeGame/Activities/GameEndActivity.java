package nasretdinovir.example.dodgeGame.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import nasretdinovir.example.dodgeGame.MainActivity;
import nasretdinovir.example.dodgeGame.R;
import nasretdinovir.example.dodgeGame.Tools.Cache;

public class GameEndActivity extends AppCompatActivity {

    private Cache cache;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("GameActivity.java", "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        cache = new Cache(getCacheDir());
        score = cache.readScoreFromCache();
        int newScore = getIntent().getIntExtra("score", 0);
        if (newScore > score) {
            score = newScore;
            cache.writeScoreToCache(score);
        }

        TextView scoreView = findViewById(R.id.endScoreView);
        scoreView.setText(String.valueOf(newScore));

        findViewById(R.id.endMenuButtonView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameEndActivity.this, MainActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
            }
        });

        findViewById(R.id.endRestartButtonView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameEndActivity.this, GameActivity.class));
            }
        });

    }
}