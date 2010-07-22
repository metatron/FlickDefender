package jp.arrow.angelforest.flickdefender;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import jp.arrow.angelforest.engine.core.TextureLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class FlickDefenderLogic {
    private static FlickDefenderLogic logic;
    private static Context context;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();

    public static int ADD_ENEMY_TIMING = 100;
    private int tick;

    private static Paint textPaint;

    public static FlickDefenderLogic getInstance(Context context) {
        if(logic == null) {
            logic = new FlickDefenderLogic(context);
            textPaint = new Paint();
            textPaint.setColor(Color.MAGENTA);
        }

        return logic;
    }

    private FlickDefenderLogic(Context context) {
        FlickDefenderLogic.context = context;
        detectScreenWidthHeight();
    }

    private void detectScreenWidthHeight() {
        Display disp =
            ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).
            getDefaultDisplay();

        SCREEN_WIDTH = disp.getWidth();
        SCREEN_HEIGHT = disp.getHeight();
    }

    //-------------- logic ----------------//
    public void init() {
        enemyList.clear();

    }

    public synchronized void addEnemy() {
        if(tick%ADD_ENEMY_TIMING == 0) {
            enemyList.add(new Enemy());
        }
    }

    public synchronized void drawEnemy() {
        for(Enemy enemy : enemyList) {
            if(!enemy.isDead()) {
                enemy.draw();
                enemy.move();

                //detect dead
                enemy.detectDead();
            }
            else {
                enemyList.remove(enemy);
            }
        }
    }

//    SquarePolygon testsquare = new SquarePolygon();
    public void detectCollision(Bullet bullet) {
        for(Enemy enemy : enemyList) {
            if(!enemy.isDead()) {
                float enemyLeft = enemy.getX()-enemy.getW();
                float enemyRight = enemy.getX()+enemy.getW();
                float enemyTop = enemy.getY()-enemy.getH();
                float enemyBottom = enemy.getY()+enemy.getH();

                float bulletLeft = bullet.getX()-bullet.getW();
                float bulletRight = bullet.getX()+bullet.getW();
                float bulletTop = bullet.getY()-bullet.getH();
                float bulletBottom = bullet.getY()+bullet.getH();

//                testsquare.draw((int)bulletLeft, (int)bulletTop, 1, 1, 0);

                boolean iscollide = enemyLeft < bulletRight && bulletLeft < enemyRight && bulletTop < enemyBottom && enemyTop < bulletBottom;
//                Log.e(null, "bullet: " + bullet.getX() + ", " + bullet.getY() + " enemy: " + enemy.getX() + ", " + enemy.getY() + " :: " + iscollide);

                if(iscollide) {
                    Log.e(null, "collision!");
                    enemy.setDead(true);
                    bullet.setDead(true);
                }
            }
        }
    }

    public void displayTick(GL10 gl) {
        String tickStr = "time: " + tick;
        int w = (int)textPaint.measureText(tickStr);
        int h = (int)(textPaint.descent()-textPaint.ascent());
        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(tickStr, 0, (int)-textPaint.ascent(), textPaint);

        TextureLoader loader = new TextureLoader();
    }

    public void tickTimer() {
        tick++;

        if(ADD_ENEMY_TIMING > 1) {
            ADD_ENEMY_TIMING--;
        }
    }

}
