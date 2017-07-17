package dwarvenbeer.a2017_04_21games;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Павел on 21.04.2017.
 */

public class GameView extends SurfaceView {

    private GameThread mTread;
    private EnemyThread eThread;
    //private Thread thread;
    public int shotX;
    public int shotY;
    private boolean running = false;

    private List<Bullet> bullets = new ArrayList<Bullet>();
    private List<Enemy> enemies = new ArrayList<Enemy>();

    Bitmap ninja;
    Bitmap bullet;
    Bitmap enemy;

    public int ghDel = 100;

    //-------------Start of GameThread--------------------------------------------------\\
    public class GameThread extends Thread{
        private GameView view;               /**Объект класса*/
        public GameThread(GameView view) {   /**Конструктор класса*/
            this.view = view;
        }

        public void setRunning(boolean run) {/**Задание состояния потока*/
            running = run;
        }

        public void run() {                  /** Действия, выполняемые в потоке */
            while (running) {
                Canvas canvas = null;
                try {
                    canvas = view.getHolder().lockCanvas();// подготовка Canvas-а
                    synchronized (view.getHolder()) {
                        onDraw1(canvas);
                        testCollision();
                    }
                }
                catch (Exception e) {}
                finally {
                    if (canvas != null) {
                        view.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
    //-------------End of GameThread--------------------------------------------------\\

    //-------------Start of EnemyThread--------------------------------------------------\\
    public class EnemyThread extends Thread{
        private GameView view;               /**Объект класса*/
        public EnemyThread(GameView view) {   /**Конструктор класса*/
            this.view = view;
        }

        public void setRunning(boolean run) {/**Задание состояния потока*/
            running = run;
        }

        public void run() {                  /** Действия, выполняемые в потоке */
            while (true) {
                Random random = new Random();
                try {
                    ghDel = random.nextInt(5000);
                    EnemyThread.sleep(ghDel);
                    enemies.add(new Enemy(view, enemy));
                    } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //-------------End of EnemyThread--------------------------------------------------\\

    public GameView(final Context context) {
        super(context);
        mTread = new GameThread(this);
        eThread = new EnemyThread(this);
        //thread = new Thread();

        getHolder().addCallback(new SurfaceHolder.Callback() {/*Рисуем все наши объекты и все все все*/
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mTread.setRunning(true);
                mTread.start();
                eThread.setRunning(true);
                eThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                mTread.setRunning(false);
                eThread.setRunning(false);
                while (retry) {
                    try {
                        mTread.join();
                        eThread.join();
                        retry = false;
                    }
                    catch (InterruptedException e) {}
                }
            }
        });

        ninja = BitmapFactory.decodeResource(getResources(), R.drawable.ninja_1_tr);
        bullet = BitmapFactory.decodeResource(getResources(), R.drawable.ninja_bullet_tr);
        enemy = BitmapFactory.decodeResource(getResources(), R.drawable.ninja_ghost_tr);
        //enemies.add(new Enemy(this, enemy));
    }

    protected void onDraw1(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        Iterator<Bullet> j = bullets.iterator();
        while (j.hasNext()) {
            Bullet b = j.next();
            if (b.x >= 1000 || b.x <= 1000) {
                b.onDraw1(canvas);
            } else {
                j.remove();
            }
        }
        Iterator<Enemy> i = enemies.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (e.x >= 1000 || e.x<= 1000) {
                e.onDraw1(canvas);
            } else {
                i.remove();
            }
        }
        canvas.drawBitmap(ninja, 5, 120, null);   //рисуем героя
    }

    public Bullet createSprite(Bitmap bitmap) {
        return new Bullet(this, bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        shotX = (int) event.getX();
        shotY = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            bullets.add(createSprite(bullet));  //рисуем пули
            Toast.makeText(getContext(), Integer.toString(ghDel), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void testCollision() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();

                if ((Math.abs(bullet.x - enemy.x) <= (bullet.width + enemy.width) / 2f)
                        && (Math.abs(bullet.y - enemy.y) <= (bullet.height + enemy.height) / 2f)) {
                    enemyIterator.remove();
                    bulletIterator.remove();
                }
            }
        }
    }

}
