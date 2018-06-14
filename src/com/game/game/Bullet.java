package com.game.game;

import com.game.engine.GameContainer;
import com.game.engine.Renderer;

public class Bullet extends GameObject {
    private float speed = 400;
    private Player player;
    private int x,y;
    private int tileX,tileY;
    private float offX,offY;
    private int tmpX,tmpY;
    int tmpx;
    int tmpy;
    float dist ;
    private final int ROT = 25;
    public Bullet(String tag,int tileX,int tileY,float offX, float offY,int camX,int camY, int x, int y,Player player)
    {
        this.tag = tag;
        this.player = player;
        this.x = x;
        this.y = y;

        this.tileX = tileX;
        this.tileY =  tileY;
        this.offX = offX;
        this.offY = offY;
        posX = tileX *GameManager.TS +offX;
        posY = tileY *GameManager.TS +offY;
        this.tmpX= (int) posX - camX;
        this.tmpY= (int) posY -camY;
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
                        if (tmpX == x)
                        {
                            player.setDiretion(0);
                        }
                        else if (tmpX  < x+ROT && dist>10)
                        {
                            player.setDiretion(0);
                        }
                        else
                        {
                            if(tmpY  < y+ROT && dist>10)
                            {
                                player.setDiretion(3);
                            }
                            else {
                                player.setDiretion(4);
                            }
                        }
                        offY -= tmpy*speed *dt/dist;
                        offX -= tmpx*speed *dt/dist;
                    }
                    else
                    {
                        if (tmpX == x)
                        {
                            player.setDiretion(2);
                        }
                        else if (tmpX  < x+ROT && dist>10)
                        {
                            player.setDiretion(2);
                        }
                        else
                        {
                            if(tmpY == y)
                            {
                                player.setDiretion(3);
                            }
                            else if(tmpY  > y-ROT && dist>10)
                            {
                                player.setDiretion(3);
                            }
                            else {
                                player.setDiretion(6);
                            }
                }
                offY += tmpy*speed *dt/dist;
                offX -= tmpx*speed *dt/dist;

            }
        }

        else
        {
            if (tmpY>y)
            {

                if (tmpX  > x-ROT && dist>10)
                {
                    player.setDiretion(0);
                }
                else if(tmpY  < y+ROT && dist>10)
                {
                    player.setDiretion(1);
                }
                else
                {
                    player.setDiretion(5);
                }
                offY -= tmpy*speed *dt/dist;
                offX += tmpx*speed *dt/dist;
            }
            else
            {
                if (tmpX  > x-ROT && dist>10)
                {
                    player.setDiretion(2);
                }
                else if(tmpY == y)
                {
                    player.setDiretion(1);
                }
                else if(tmpY  > y-ROT && dist>10)
                {
                    player.setDiretion(1);
                }
                else
                {
                    player.setDiretion(7);
                }
                offY += tmpy*speed *dt/dist;
                offX += tmpx*speed *dt/dist;
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
        posX = tileX *GameManager.TS +offX;
        posY = tileY *GameManager.TS +offY;

    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawFillRect((int)posX+2 ,(int)posY+2 ,2,2,	0xFF000000);
    }
}
