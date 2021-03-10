package nasretdinovir.example.dodgeGame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import nasretdinovir.example.dodgeGame.Activities.GameActivity;
import nasretdinovir.example.dodgeGame.Tools.Cache;

public class MainActivity extends AppCompatActivity {

    private Cache cache;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        cache = new Cache(getCacheDir());
        score = cache.readScoreFromCache();
        int newScore = getIntent().getIntExtra("score", 0);
        if (newScore > score) {
            score = newScore;
            cache.writeScoreToCache(score);
        }

        TextView scoreView = findViewById(R.id.scoreCountView);
        scoreView.setText(String.valueOf(score));

        findViewById(R.id.startButtonView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        findViewById(R.id.rulesButtonView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder rulesDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                rulesDialogBuilder.setTitle("RULES");
                rulesDialogBuilder.setMessage("In the game you have to dodge red shoots");
                rulesDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });
                rulesDialogBuilder.show();
            }
        });
    }
}