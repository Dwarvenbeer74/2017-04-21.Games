package dwarvenbeer.a1ninja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Павел on 24.04.2017.
 */

public class GameView extends SurfaceView {
    private Bitmap bitmap;        /**Загружаем спрайт*/
    private SurfaceHolder holder; /**Поле рисования*/
    private GameManager gameloopThread;  /**объект класса GameManager*/
    private Sprite sprite;
    private List<Sprite> sprites = new ArrayList<Sprite>();

    private SoundPool soundPool;
    private int sExplosion;

    public GameView(Context context) {
        super(context);
        gameloopThread = new GameManager(this);
        holder = getHolder();

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        sExplosion = soundPool.load(context, R.raw.bombexplosion, 1);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createSprites();
                gameloopThread.setRunning(true);
                gameloopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameloopThread.setRunning(false);
                while (retry) {
                    try {
                        gameloopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bad1);
    }

    public void onDraw1(Canvas canvas) {
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
        }
        for (Sprite sprite : sprites) {
            sprite.onDrawSprite(canvas);
        }
    }

    private Sprite createSprite(int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
        return new Sprite(this, bitmap);
    }

    public void createSprites() {
        sprite = new Sprite(this, bitmap);
        sprites.add(createSprite(R.drawable.kid1));
        sprites.add(createSprite(R.drawable.kid2));
        sprites.add(createSprite(R.drawable.kid3));
        sprites.add(createSprite(R.drawable.kid4));
        sprites.add(createSprite(R.drawable.kid5));
        sprites.add(createSprite(R.drawable.kid6));
        sprites.add(createSprite(R.drawable.kid7));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (getHolder()) {
            for (int i = sprites.size() - 1; i >= 0; i--) {
                Sprite sprite = sprites.get(i);
                if (sprite.isCollision(event.getX(), event.getY())) {
                    sprites.remove(sprite);
                    soundPool.play(sExplosion, 1.0f, 1.0f, 0, 0, 3.0f);
                    if (sprites.size() == 0) {
                        Toast.makeText(getContext(), "Победа!", Toast.LENGTH_SHORT).show();
                        createSprites();
                    }
                    break;
                }
            }
            return super.onTouchEvent(event);
        }
    }
}
