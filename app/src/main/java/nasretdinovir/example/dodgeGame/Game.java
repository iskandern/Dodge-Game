package nasretdinovir.example.dodgeGame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nasretdinovir.example.dodgeGame.Activities.GameEndActivity;
import nasretdinovir.example.dodgeGame.GameObjects.EnemyShoot;
import nasretdinovir.example.dodgeGame.GameObjects.Joystick;
import nasretdinovir.example.dodgeGame.GameObjects.Player;
import nasretdinovir.example.dodgeGame.Tools.GameCalculation;
import nasretdinovir.example.dodgeGame.Tools.ScreenPoint;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private List<EnemyShoot> enemyShootList = new ArrayList<EnemyShoot>();
    private GameLoop gameLoop;
    private GameCalculation gameCalculation;

    int loopsCount;
    int textColor;
    int backgroundColor;

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        loopsCount = 0;
        textColor = ContextCompat.getColor(context, R.color.darkBackgroundText);
        backgroundColor = ContextCompat.getColor(context, R.color.appBackground);

        gameCalculation = new GameCalculation(context);
        ScreenPoint joystickPoint = gameCalculation.getJoystickPoint(
                Joystick.BACKGROUND_DEFAULT_RADIUS
        );
        ScreenPoint playerPoint = gameCalculation.getPlayerPoint();

        joystick = new Joystick(
                joystickPoint, Joystick.BACKGROUND_DEFAULT_RADIUS,
                Joystick.JOYSTICK_DEFAULT_RADIUS
        );
        player = new Player(context, playerPoint, Player.PLAYER_DEFAULT_RADIUS);

        gameLoop = new GameLoop(this, surfaceHolder);
    }



    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(backgroundColor);

        drawLoopsCount(canvas);

        joystick.draw(canvas);
        player.draw(canvas);

        for (EnemyShoot enemyShoot: enemyShootList) {
            enemyShoot.draw(canvas);
        }

        if (player.getHealthPoint() <= 0) {
            gameLoop.stopLoop();
            Intent intent = new Intent(getContext(), GameEndActivity.class);
            intent.putExtra("score", loopsCount);
            getContext().startActivity(intent);
        }
    }

    private void drawLoopsCount(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setTextSize(50);
        String livesText = "SCORE: " + loopsCount;
        canvas.drawText(
                livesText, 50, 100, paint
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (joystick.inJoystickArea(event.getX(), event.getY())) {
                    joystick.setPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.isPressed()) {
                    joystick.setMovementTool(event.getX(), event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setPressed(false);
                joystick.resetMovementTool();
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    public boolean update() {
        loopsCount++;
        player.update(joystick);
        joystick.update();

        if (EnemyShoot.isReadyToSpawn()) {
            ScreenPoint randBorderPoint = gameCalculation.getRandBorderPoint();
            float shootDirection = gameCalculation.directionToPlayer(randBorderPoint, player);

            enemyShootList.add(new EnemyShoot(getContext(), randBorderPoint, 100, 30, shootDirection));
        }

        for (EnemyShoot enemyShoot: enemyShootList) {
            enemyShoot.update();
        }

        Iterator<EnemyShoot> iteratorEnemyShoot = enemyShootList.iterator();
        while (iteratorEnemyShoot.hasNext()) {
            if (gameCalculation.collisionDetected(iteratorEnemyShoot.next(), player)) {
                iteratorEnemyShoot.remove();
                player.setHealthPoint(player.getHealthPoint() - 1);
            }
        }

        iteratorEnemyShoot = enemyShootList.iterator();
        while (iteratorEnemyShoot.hasNext()) {
            EnemyShoot enemyShoot = iteratorEnemyShoot.next();
            if (gameCalculation.outOfBorder(enemyShoot) && enemyShoot.getLiveTime() > 0) {
                iteratorEnemyShoot.remove();
            }
        }

        return player.getHealthPoint() > 0;
    }

    public int pause() {
        gameLoop.stopLoop();
        return loopsCount;
    }
}
