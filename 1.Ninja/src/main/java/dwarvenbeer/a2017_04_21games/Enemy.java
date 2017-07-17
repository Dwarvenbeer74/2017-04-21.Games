package dwarvenbeer.a2017_04_21games;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.Window;
import android.view.WindowManager;

import java.util.Random;

/**
 * Created by Павел on 21.04.2017.
 */

public class Enemy {
    public int x;
    public int y;
    public int speed;
    public int width;
    public int height;
    public GameView gameView;
    public Bitmap bitmap;

    public Enemy(GameView gameView, Bitmap bitmap) {
        this.gameView = gameView;
        this.bitmap = bitmap;

        Random random = new Random();
        this.x = 1500;
        this.y = random.nextInt(700);
        this.speed = random.nextInt(10)+5;

        this.width = 90;
        this.height = 80;
    }

    public void update() {
        x -= speed;
    }

    public void onDraw1(Canvas canvas) {
        update();
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
