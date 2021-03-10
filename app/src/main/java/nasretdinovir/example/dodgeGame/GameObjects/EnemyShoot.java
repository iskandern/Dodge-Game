package nasretdinovir.example.dodgeGame.GameObjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import nasretdinovir.example.dodgeGame.R;
import nasretdinovir.example.dodgeGame.Tools.ScreenPoint;

public class EnemyShoot {
    private static final float SHOOT_MAX_SPEED = 15;
    private static final int UPDATES_PER_SPAWN = 20;
    private static int updatesLeftBeforeNextSpawn = 0;

    private ScreenPoint centerPoint;
    private float width;
    private float height;
    private float direction;
    private int liveTime;

    private Paint paint;
    private int color;


    public EnemyShoot(Context context, ScreenPoint randBorderPoint, float width, float height, float direction) {
        this.width = width;
        this.height = height;
        this.direction = direction;

        centerPoint = randBorderPoint;
        liveTime = 0;

        paint = new Paint();
        color = ContextCompat.getColor(context, R.color.enemy);
        paint.setColor(color);
    }

    public static boolean isReadyToSpawn() {
        if (updatesLeftBeforeNextSpawn <= 0) {
            updatesLeftBeforeNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updatesLeftBeforeNextSpawn--;
        }
        return false;
    }

    public void draw(Canvas canvas) {

//        float newX = (float)Math.cos(Math.toRadians(direction))*1400 + beginPoint.x;
//        float newY = (float)Math.sin(Math.toRadians(direction))*1400 + beginPoint.y;
//        canvas.drawLine(beginPoint.x, beginPoint.y, newX, newY, paint);

        canvas.save();
        canvas.translate(centerPoint.x, centerPoint.y);
        canvas.rotate(direction);

        float left = -width / 2;
        float right = width / 2;
        float top = -height / 2;
        float bottom = height / 2;
        canvas.drawRect(left, top, right, bottom, paint);

        canvas.restore();

        liveTime++;
    }

    public void update() {

        float speedX = (float) (Math.cos(Math.toRadians(direction)) * SHOOT_MAX_SPEED);
        float speedY = (float) (Math.sin(Math.toRadians(direction)) * SHOOT_MAX_SPEED);

        centerPoint.x += speedX;
        centerPoint.y += speedY;
    }

    public ScreenPoint getCenterPoint() {
        return centerPoint;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getLiveTime() {
        return liveTime;
    }

    public float getDirection() {
        return direction;
    }

    public float getMaxDistanceFromCenterToEdge() {
        return (float) Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2));
    }
}
