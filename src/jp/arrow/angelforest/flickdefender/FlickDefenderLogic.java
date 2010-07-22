package jp.arrow.angelforest.flickdefender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import jp.arrow.angelforest.engine.core.TextureLoader;
import jp.arrow.angelforest.engine.core.TexturePolygon;

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

    public static final int GAME_READY = 0;
    public static final int GAME_STARTED = 1;
    public static final int GAME_OVER = 2;
    private static int status = GAME_READY;

    private List<Enemy> enemyList = Collections.synchronizedList(new ArrayList<Enemy>());

    public static int DEFAULT_LEVEL_TIMING = 50;
    public static int HIGHTEST_LEVEL_TIMING = 10;
    public static int ADD_ENEMY_TIMING = DEFAULT_LEVEL_TIMING;
    private int tick;

    private static Paint textPaint;
    private TexturePolygon textTexture;

    public static final int MAX_HP = 10;
    private static int hp = MAX_HP;
    private static int defended = 0;

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
        hp = MAX_HP;
        defended = 0;
        status = GAME_READY;
        ADD_ENEMY_TIMING = DEFAULT_LEVEL_TIMING;
    }

    public synchronized void addEnemy() {
        if(tick%ADD_ENEMY_TIMING == 0) {
            enemyList.add(new Enemy());
        }
    }

    public synchronized void drawEnemy() {
        for(Enemy enemy : enemyList) {
            if(enemy != null && !enemy.isDead()) {
                enemy.draw();
                enemy.move();

                //detect dead
                detectHpDown(enemy);
                enemy.detectDead();
            }
            else {
                //DO NOT remove directry.
                //set null first
                if(enemy == null) {
                    enemyList.remove(enemy);
                }
                else {
                    enemy = null;
                }
            }
        }
    }

//    SquarePolygon testsquare = new SquarePolygon();
    public synchronized void detectCollision(Bullet bullet) {
        for(Enemy enemy : enemyList) {
            if(enemy != null && !enemy.isDead()) {
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
//                    Log.e(null, "collision!");
                    //set deads and decrease timing
                    enemy.setDead(true);
                    bullet.setDead(true);

                    //increment score
                    defended++;

                    //make the game lvl harder
                    if(ADD_ENEMY_TIMING > HIGHTEST_LEVEL_TIMING) {
                        ADD_ENEMY_TIMING--;
                    }
                }
            }

        }
    }

    private void detectHpDown(Enemy enemy) {
        if(enemy.getX() > 0 && enemy.getX() < FlickDefenderLogic.SCREEN_WIDTH && enemy.getY() > FlickDefenderLogic.SCREEN_HEIGHT) {
            hp--;

            if(hp == 0) {
                status = GAME_OVER;
            }
            Log.e(null, "hp down: " + hp);
        }
    }

    public void displayText(GL10 gl) {
        switch (status) {
        case GAME_STARTED:
            displayHP(gl);
            break;
        case GAME_OVER:
            displayGameOver(gl);
            break;
        }
    }

    public void displayGameOver(GL10 gl) {
        String tickStr = "GAME OVER";
        int w = (int)textPaint.measureText(tickStr);
        int h = (int)(textPaint.descent()-textPaint.ascent());
        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(tickStr, 0, (int)-textPaint.ascent(), textPaint);
        //load text
        textTexture = new TexturePolygon(context, bitmap);
        textTexture.draw(FlickDefenderLogic.SCREEN_WIDTH/2, FlickDefenderLogic.SCREEN_HEIGHT/2, 180);
        textTexture.delete();
    }

    public void displayHP(GL10 gl) {
        String tickStr = "HP: " + hp;
        String scoreStr = "defended: " + defended;

        int w = (int)textPaint.measureText(tickStr);
        int w2 = (int)textPaint.measureText(scoreStr);

        if(w2 >= w) {
            w = w2;
        }

        int h = (int)(textPaint.descent()-2*textPaint.ascent());
        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(tickStr, 0, (int)-textPaint.ascent(), textPaint);
        canvas.drawText("defended: " + defended, 0, (int)-2*textPaint.ascent(), textPaint);

        //load text
        textTexture = new TexturePolygon(context, bitmap);
        textTexture.draw(40, 20, 180);
        textTexture.delete();
    }

    public void tickTimer() {
        tick++;

//        if(ADD_ENEMY_TIMING > 1) {
//            ADD_ENEMY_TIMING--;
//        }
    }

    public static int getStatus() {
        return status;
    }

    public static void setStatus(int status) {
        FlickDefenderLogic.status = status;
    }

}
