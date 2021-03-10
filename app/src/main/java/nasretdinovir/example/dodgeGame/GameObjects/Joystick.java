package nasretdinovir.example.dodgeGame.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import nasretdinovir.example.dodgeGame.Tools.ScreenPoint;


public class Joystick {
    public static final float BACKGROUND_DEFAULT_RADIUS = 150;
    public static final float JOYSTICK_DEFAULT_RADIUS = 60;

    private float backgroundRadius;
    private float joystickRadius;
    private ScreenPoint joystickCenter;
    private ScreenPoint backgroundCenter;

    private Paint backgroundPaint;
    private Paint joystickPaint;

    boolean isPressed;
    private float movementToolX;
    private float movementToolY;

    public Joystick(ScreenPoint centerPoint, float backgroundRadius, float joystickRadius) {

        joystickCenter = new ScreenPoint(centerPoint.x, centerPoint.y);
        backgroundCenter = new ScreenPoint(centerPoint.x, centerPoint.y);

        this.backgroundRadius = backgroundRadius;
        this.joystickRadius = joystickRadius;

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.GRAY);
        backgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        joystickPaint = new Paint();
        joystickPaint.setColor(Color.WHITE);
        joystickPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        isPressed = false;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(
                backgroundCenter.x,
                backgroundCenter.y,
                backgroundRadius,
                backgroundPaint
        );

        canvas.drawCircle(
                joystickCenter.x,
                joystickCenter.y,
                joystickRadius,
                joystickPaint
        );
    }

    public void update() {
        updateJoystickCenterPosition();
    }

    private void updateJoystickCenterPosition() {
        float maxJoystickMoveRadius = backgroundRadius - joystickRadius;
        joystickCenter.x = backgroundCenter.x + movementToolX * maxJoystickMoveRadius;
        joystickCenter.y = backgroundCenter.y + movementToolY * maxJoystickMoveRadius;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public boolean inJoystickArea(float positionX, float positionY) {
        float distanceToCenterOX = Math.abs(backgroundCenter.x - positionX);
        float distanceToCenterOY = Math.abs(backgroundCenter.y - positionY);
        float distanceToCenter = (float) Math.sqrt(
                Math.pow(distanceToCenterOX, 2) + Math.pow(distanceToCenterOY, 2)
        );

        return distanceToCenter < backgroundRadius;
    }

    public void setMovementTool(float positionX, float positionY) {
        float deltaX = positionX - backgroundCenter.x;
        float deltaY = positionY - backgroundCenter.y;
        float deltaDistance = (float) Math.sqrt(
                Math.pow(deltaX, 2) + Math.pow(deltaY, 2)
        );

        float maxJoystickMoveRadius = backgroundRadius - joystickRadius;
        if (deltaDistance < maxJoystickMoveRadius) {
            movementToolX = deltaX / maxJoystickMoveRadius;
            movementToolY = deltaY / maxJoystickMoveRadius;
        } else {
            movementToolX = deltaX / deltaDistance;
            movementToolY = deltaY / deltaDistance;
        }
    }

    public void resetMovementTool() {
        movementToolX = 0;
        movementToolY = 0;
    }

    public float getMovementToolX() {
        return movementToolX;
    }

    public float getMovementToolY() {
        return movementToolY;
    }
}
