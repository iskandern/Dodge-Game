package nasretdinovir.example.dodgeGame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

    private Game game;
    private boolean running;
    private SurfaceHolder surfaceHolder;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;

        running = false;
    }
    public void startLoop() {
        running = true;
        start();
    }

    public void stopLoop() {

        if (!running) {
            return;
        }

        running = false;
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();

        Canvas canvas;
        while (running) {

            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                running = game.update();
                game.draw(canvas);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
