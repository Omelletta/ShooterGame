package com.game.game;

import com.game.engine.GameContainer;
import com.game.engine.Renderer;

public class BulletEnemy extends GameObject {
    private float speed = 400;
    private int x,y;
    private int tileX,tileY;
    private float offX,offY;
    private int tmpX,tmpY;
    int tmpx;
    int tmpy;
    float dist ;
    private EnemyWeapon ew;
    private int damage;
    public BulletEnemy(String tag, int tileX, int tileY, float offX, float offY, int x, int y, EnemyWeapon ew)
    {
        this.tag = tag;
        this.x = x;
        this.y = y;
        this.tmpX= (int) (tileX *GameManager.TS +offX);
        this.tmpY= (int) (tileY *GameManager.TS +offY);
        this.ew = ew;
        this.tileX = tileX;
        this.tileY =  tileY;
        this.offX = offX;
        this.offY = offY;
        this.damage = ew.getDamage();
        posX = tileX *GameManager.TS +offX;
        posY = tileY *GameManager.TS +offY;
        if (tmpX >= x)
        {
            tmpx = (tmpX-x);
            if (tmpY>y) {
                tmpy = (tmpY - y);
            }
            else
            {
                tmpy = (y-tmpY);
            }
        }
        else
        {
            tmpx = (x-tmpX);
            if (tmpY>y) {
                tmpy = (tmpY - y);
            }
            else
            {
                tmpy = (y-tmpY);
            }
        }
        dist = (int) Math.sqrt(tmpy*tmpy + tmpx*tmpx);
    }
    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        if (tmpX >= x)
        {
            if (tmpY>y)
            {
                offY -= tmpy*speed *dt/dist-1 ;
                offX -= tmpx*speed *dt/dist-1 ;
            }
            else
            {
                offY += tmpy*speed *dt/dist +1;
                offX -= tmpx*speed *dt/dist -1;

            }
        }
        else {
            if (tmpY > y) {
                offY -= tmpy * speed * dt / dist-1;
                offX += tmpx * speed * dt / dist+1;
            } else {
                offY += tmpy * speed * dt / dist+1;
                offX += tmpx * speed * dt / dist+1;
            }
        }
        if(offY > GameManager.TS)
        {
            tileY++;
            offY -= GameManager.TS;
        }
        if(offY < 0)
        {
            tileY--;
            offY += GameManager.TS;
        }
        if(offX > GameManager.TS)
        {
            tileX++;
            offX -= GameManager.TS;
        }
        if(offX < 0)
        {
            tileX--;
            offX += GameManager.TS;
        }
        if(gm.getCollision(tileX,tileY))
        {
            this.dead = true;
        }
        posX = tileX * GameManager.TS + offX;
        posY = tileY * GameManager.TS + offY;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawFillRect((int)posX+2 ,(int)posY+2 ,2,2,	ew.getColor());
    }

    public int getDamage() {
        return damage;
    }
}
