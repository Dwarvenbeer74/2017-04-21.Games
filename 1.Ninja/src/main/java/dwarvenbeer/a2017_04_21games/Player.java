package dwarvenbeer.a2017_04_21games;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Павел on 21.04.2017.
 */

public class Player {
    GameView gameView;      /**Объект главного класса*/
    Bitmap bitmap;          //спрайт
    int x;                  //х и у координаты рисунка
    int y;

    public Player(GameView gameView, Bitmap bitmap) {
        this.gameView = gameView;
        this.bitmap = bitmap;

        this.x = 0;
        this.y = gameView.getHeight() /2;
    }

    public void onDraw1(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

}
