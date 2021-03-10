package nasretdinovir.example.dodgeGame.Tools;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.util.Random;

import nasretdinovir.example.dodgeGame.GameObjects.EnemyShoot;
import nasretdinovir.example.dodgeGame.GameObjects.Player;

public class GameCalculation {

    private ScreenPoint borderPoint;

    public GameCalculation(Context context) {
        borderPoint = getBorder(context);
    }

    private ScreenPoint getBorder(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        return new ScreenPoint(size.x, size.y);
    }

    public float directionToPlayer(ScreenPoint point, Player player) {
        float playerPositionX = player.getCenterPoint().x;
        float playerPositionY = player.getCenterPoint().y;

        float deltaX = playerPositionX - point.x;
        float deltaY = playerPositionY - point.y;

        float directionAngle = (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
        return directionAngle;
    }

    public boolean outOfBorder(EnemyShoot enemyShoot) {
        return enemyShoot.getCenterPoint().x < 0
                || enemyShoot.getCenterPoint().x  > borderPoint.x
                || enemyShoot.getCenterPoint().y < 0
                || enemyShoot.getCenterPoint().y > borderPoint.y;
    }

    public ScreenPoint normDeviation(ScreenPoint newScreenPoint) {
        if (newScreenPoint.x <= 0) {
            newScreenPoint.x = 0;
        }
        if (newScreenPoint.y <= 0) {
            newScreenPoint.y = 0;
        }
        if (newScreenPoint.x >= borderPoint.x) {
            newScreenPoint.x = borderPoint.x;
        }
        if (newScreenPoint.y >= borderPoint.y) {
            newScreenPoint.y = borderPoint.y;
        }
        return newScreenPoint;
    }

    public ScreenPoint getRandBorderPoint() {
        Random random = new Random();

        int randomBorderScenario = random.nextInt(4);
        switch (randomBorderScenario) {
            case 0:
                return new ScreenPoint(random.nextInt((int) borderPoint.x), 0);
            case 1:
                return new ScreenPoint(0, random.nextInt((int) borderPoint.y));
            case 2:
                return new ScreenPoint(random.nextInt((int) borderPoint.x), borderPoint.y);
            case 3:
                return new ScreenPoint(borderPoint.x, random.nextInt((int) borderPoint.y));
        }
        return null;
    }

    public boolean collisionDetected(EnemyShoot enemyShoot, Player player) {
        ScreenPoint rotatedPlayerCenterPoint = rotatePoint(
                player.getCenterPoint(),
                -enemyShoot.getDirection(),
                enemyShoot.getCenterPoint()
        );
        float distanceOX = Math.abs(rotatedPlayerCenterPoint.x - enemyShoot.getCenterPoint().x);
        float distanceOY = Math.abs(rotatedPlayerCenterPoint.y - enemyShoot.getCenterPoint().y);

        if (distanceOX > (enemyShoot.getWidth() / 2 + player.getRadius())) {
            return false;
        }
        if (distanceOY > (enemyShoot.getHeight() / 2 + player.getRadius())) {
            return false;
        }

        if (distanceOX <= (enemyShoot.getWidth() / 2)) {
            return true;
        }
        if (distanceOY <= (enemyShoot.getHeight() / 2)) {
            return true;
        }

        float cornerDistanceSq = (float) (Math.pow((distanceOX - enemyShoot.getWidth() / 2), 2) +
                        Math.pow((distanceOY - enemyShoot.getHeight() / 2 ), 2));

        return (cornerDistanceSq <= Math.pow(player.getRadius(), 2));
    }

    public ScreenPoint rotatePoint(ScreenPoint toRotateScreenPoint, float angle, ScreenPoint rotationCenterPoint) {

        ScreenPoint oldScreenPoint = new ScreenPoint(toRotateScreenPoint.x, toRotateScreenPoint.y);
        oldScreenPoint.x -= rotationCenterPoint.x;
        oldScreenPoint.y -= rotationCenterPoint.y;

        float newPointX = (float) (
                oldScreenPoint.x * Math.cos(Math.toRadians(angle)) -
                oldScreenPoint.y * Math.sin(Math.toRadians(angle))
        );
        float newPointY = (float) (
                oldScreenPoint.x * Math.sin(Math.toRadians(angle)) +
                oldScreenPoint.y * Math.cos(Math.toRadians(angle))
        );

        newPointX += rotationCenterPoint.x;
        newPointY += rotationCenterPoint.y;

        return new ScreenPoint(newPointX, newPointY);
    }

    public ScreenPoint getJoystickPoint(float backgroundRadius) {
        float borderIndent = 5;
        float positionX = 0 + (borderIndent + backgroundRadius);
        float positionY = borderPoint.y - (borderIndent + backgroundRadius);

        return new ScreenPoint(positionX, positionY);
    }

    public ScreenPoint getPlayerPoint() {
        return new ScreenPoint(borderPoint.x / 2, borderPoint.y / 2);
    }
}
