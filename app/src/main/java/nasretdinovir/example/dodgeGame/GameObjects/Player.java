package nasretdinovir.example.dodgeGame.GameObjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import nasretdinovir.example.dodgeGame.R;
import nasretdinovir.example.dodgeGame.Tools.GameCalculation;
import nasretdinovir.example.dodgeGame.Tools.ScreenPoint;


public class Player {
    private static final float PLAYER_MAX_SPEED = 10;
    private static final int PLAYER_LIVES = 3;
    public static final float PLAYER_DEFAULT_RADIUS = 30;

    GameCalculation gameCalculation;

    private ScreenPoint centerPoint;
    private float radius;
    private Paint paint;

    private int playerColor;
    private int healthPointColor;
    private int healthPoint;


    public Player(Context context, ScreenPoint centerPoint, float radius) {
        this.centerPoint = centerPoint;
        this.radius = radius;
        gameCalculation = new GameCalculation(context);

        paint = new Paint();
        playerColor = ContextCompat.getColor(context, R.color.player);
        healthPointColor = ContextCompat.getColor(context, R.color.darkBackgroundText);

        healthPoint = PLAYER_LIVES;
    }

    public void draw(Canvas canvas) {

        paint.setColor(playerColor);
        canvas.drawCircle(centerPoint.x, centerPoint.y, radius, paint);

        paint.setColor(healthPointColor);
        paint.setTextSize(50);
        String livesText = "LIVES: " + healthPoint;
        canvas.drawText(
                livesText, 50, 50, paint
        );
    }

    public void update(Joystick joystick) {
        float speedX = joystick.getMovementToolX() * PLAYER_MAX_SPEED;
        float speedY = joystick.getMovementToolY() * PLAYER_MAX_SPEED;

        ScreenPoint newScreenPoint = new ScreenPoint(centerPoint.x + speedX, centerPoint.y + speedY);
        centerPoint = gameCalculation.normDeviation(newScreenPoint);
    }

    public ScreenPoint getCenterPoint() {
        return centerPoint;
    }

    public float getRadius() {
        return radius;
    }

    public float getMaxDistanceFromCenterToEdge() {
        return radius;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }
}
