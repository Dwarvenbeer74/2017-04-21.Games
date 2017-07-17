package dwarvenbeer.a1ninja;

import android.graphics.Canvas;

/**
 * Created by Павел on 24.04.2017.
 */

public class GameManager extends Thread {
    private GameView gameView;
    private boolean running = false;

    public GameManager(GameView gameView) {
        this.gameView = gameView;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    public void run() {
        while (running) {
            Canvas canvas = null;
            try {
                canvas = gameView.getHolder().lockCanvas();
                synchronized (gameView.getHolder()) {
                    gameView.onDraw1(canvas);
                }
            } finally {
                if (canvas != null) {
                    gameView.getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
