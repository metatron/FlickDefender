package jp.arrow.angelforest.flickdefender;

import jp.arrow.angelforest.engine.core.SquarePolygon;

public class Bullet {
    protected int w;
    protected int h;

    protected float x;
    protected float y;
    protected float vx;
    protected float vy;

    private SquarePolygon square;

    protected boolean isDead = false;

    public Bullet() {
        square = new SquarePolygon();

        w = 10;
        h = 10;
    }

    public void draw() {
        if(!isDead) {
            square.draw((int)x, (int)y, w, h, 0);
        }
    }

    public void move() {
        if(!isDead) {
            x += vx;
            y += vy;
        }
    }

    public void reset() {
        x = 0;
        y = 0;
        vx = 0;
        vy = 0;
        isDead = false;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
