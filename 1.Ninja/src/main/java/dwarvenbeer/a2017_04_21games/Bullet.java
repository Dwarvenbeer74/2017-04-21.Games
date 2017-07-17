package dwarvenbeer.a2017_04_21games;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Павел on 21.04.2017.
 */

public class Bullet {
    private Bitmap bitmap;   /**Картинка*/
    public int x;            /**Позиция*/
    public int y;
    private int mSpeed = 25;
    public double angle;
    public int width;
    public int height;
    public GameView gameView;

    public Bullet(GameView gameView, Bitmap bitmap) {/**Конструктор*/
        this.gameView = gameView;
        this.bitmap = bitmap;

        this.x = 0;             //позиция по Х
        this.y = 120;           //позиция по У
        this.width = 27;        //ширина снаряда
        this.height = 40;       //высота снаряда

        angle = Math.atan((double) (y - gameView.shotY) / (x - gameView.shotX));
    }

    private void update() {  /**Перемещение объекта, его направление*/
        x += mSpeed * Math.cos(angle);//движение по Х со скоростью mSpeed и углу заданном координатой angle
        y += mSpeed * Math.sin(angle);//движение по Y со скоростью mSpeed и углу заданном координатой angle
    }

    public void onDraw1(Canvas canvas) {
        update();
        canvas.drawBitmap(bitmap, x, y, null);
    }


}
