package dwarvenbeer.a1ninja;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Павел on 24.04.2017.
 */

public class Sprite {
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private GameView gameView;/**Объект класса GameView*/
    private Bitmap bitmap;
    private int x = 5;
    private int y = 0;
    private int xSpeed = 5;
    private int ySpeed = 5;
    private int currentFrame = 0;
    private int currentFrameX = 0;
    private int currentFrameY = 0;
    private int count = 0;
    private int width = 50;
    private int height = 50;
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 up, 1 left, 0 down, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };

    public Sprite(GameView gameView, Bitmap bitmap) {/**Конструктор*/
        this.gameView = gameView;
        this.bitmap = bitmap;
        this.width = bitmap.getWidth() / BMP_COLUMNS;
        this.height = bitmap.getHeight() / BMP_ROWS;

        Random random = new Random();
        x = random.nextInt(gameView.getWidth() - width);
        y = random.nextInt(gameView.getHeight() - height);
        xSpeed = random.nextInt(20) - 5;
        ySpeed = random.nextInt(20) - 5;
    }

    private void update() {
        if (x > gameView.getWidth() - width  - xSpeed || x + xSpeed <= 0) {
            xSpeed = -xSpeed;
        }
        x = x +xSpeed;

        if (y > gameView.getHeight() - height  - ySpeed || y + ySpeed <= 0) {
            ySpeed = -ySpeed;
        }
        y = y +ySpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void onDrawSprite(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public boolean isCollision(float x2, float y2) {
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }
}
