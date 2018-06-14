package com.game.game;

import com.game.engine.GameContainer;
import com.game.engine.Renderer;
import com.game.engine.audio.SoundClip;
import com.game.engine.gfx.ImageTile;

import java.util.Random;

public class Enemy extends GameObject {
    private int tileX,tileY;
    private float offX,offY;
    private int diretion = 0;
    private float animation = 0;
    private ImageTile enemyImage;
    private float speed = 50;
    private int delay = 0;
    private int random=0;
    private int hp;
    private double distance = 999;
    private int view;
    private int cooldowne = 0;
    private int cooldownen;
    private Player player;
    private SoundClip shot;
    private EnemyWeapon ew;
    public Enemy(String tag,int posX, int posY, int hp,int view, EnemyWeapon ew)
    {
        this.tag = tag;
        this.enemyImage = new ImageTile("/enemies/"+tag+".png",16,16);
        this.posX = posX * GameManager.TS;
        this.posY = posY * GameManager.TS;
        this.tileX = posX;
        this.tileY = posY;
        this.offX = 0;
        this.offY = 0;
        this.width = GameManager.TS;
        this.height = GameManager.TS;
        this.hp = hp;
        this.view = view;
        this.cooldownen = ew.getCooldown();
        this.ew = ew;
        shot = ew.getSound();
    }
    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        //checking for damage
        for(GameObject go:gm.getObjects())
        {
            if(go.tag.equals("bullet"))
            {
                if (go.posX >= this.posX && go.posX<this.posX+14 && go.posY >= this.posY && go.posY<this.posY+14)
                {
                    for(GameObject go2:gm.getObjects()) {
                        if(go2.tag.equals("player")) {
                            player = (Player) go2;
                            this.hp -= player.getWeapon().getPower();
                            go.dead = true;
                        }
                    }
                }
            }
        }
        for(GameObject go:gm.getObjects()) {
            if (go.tag.equals("player"))
            {
                player = (Player) go;
                distance =  Math.sqrt((this.posX - go.posX)*(this.posX - go.posX) + (this.posY - go.posY)*(this.posY - go.posY));
            }
        }

        if(hp>0) {

            if (delay == 0) {
                Random generator = new Random();
                random = generator.nextInt(4);
                delay = 10;
            } else {
                delay--;
            }
            //Moving

            if(distance > view ) {
                if (random == 0) {
                    if (gm.getCollision(tileX + 1, tileY) || gm.getCollision(tileX + 1, tileY + (int) Math.signum((int) offY))) {
                        if (offX < 0) {
                            offX += dt * speed;
                            if (offX > 0) {
                                offX = 0;
                            }
                        } else {
                            offX = 0;
                        }
                    } else {
                        offX += dt * speed;
                    }
                }
                if (random == 1) {
                    if (gm.getCollision(tileX - 1, tileY) || gm.getCollision(tileX - 1, tileY + (int) Math.signum((int) offY))) {
                        if (offX > 0) {
                            offX -= dt * speed;
                            if (offX < 0) {
                                offX = 0;
                            }
                        } else {
                            offX = 0;
                        }
                    } else {
                        offX -= dt * speed;
                    }
                }
                if (random == 2) {
                    if (gm.getCollision(tileX, tileY + 1) || gm.getCollision(tileX + (int) Math.signum((int) offX), tileY + 1)) {
                        if (offY < 0) {
                            offY += dt * speed;
                            if (offY > 0) {
                                offY = 0;
                            }
                        } else {
                            offY = 0;
                        }
                    } else {
                        offY += dt * speed;
                    }
                }
                if (random == 3) {
                    if (gm.getCollision(tileX, tileY - 1) || gm.getCollision(tileX + (int) Math.signum((int) offX), tileY - 1)) {
                        if (offY > 0) {
                            offY -= dt * speed;
                            if (offY < 0) {
                                offY = 0;
                            }
                        } else {
                            offY = 0;
                        }
                    } else {
                        offY -= dt * speed;
                    }
                }
                if (random == 0) {
                    diretion = 1;
                    animation += dt * 8;
                    if (animation >= 3)
                        animation = 0;
                } else if (random == 1) {
                    diretion = 3;
                    animation += dt * 8;
                    if (animation >= 3)
                        animation = 0;
                } else if (random == 2) {
                    diretion = 2;
                    animation += dt * 8;
                    if (animation >= 3)
                        animation = 0;
                } else if (random == 3) {
                    diretion = 0;
                    animation += dt * 8;
                    if (animation >= 3)
                        animation = 0;
                } else {
                    animation = 0;
                }
            }
                else if(distance >40)
                {
                    if (player.posX > this.posX+5 && !gm.getCollision(tileX + 1, tileY) ) {
                        offX += dt * speed;
                        diretion = 1;
                        animation += dt * 8;
                        if (animation >= 3)
                            animation = 0;
                    }
                    else if (player.posX < this.posX -5 && !gm.getCollision(tileX - 1, tileY) ) {
                        offX -= dt * speed;
                        diretion = 3;
                        animation += dt * 8;
                        if (animation >= 3)
                            animation = 0;
                    }
                    else if (player.posY > this.posY+5 && !gm.getCollision(tileX , tileY + 1) )
                        {
                            offY += dt * speed;
                            diretion = 2;
                            animation += dt * 8;
                            if (animation >= 3)
                                animation = 0;
                        }
                        else if (player.posY < this.posY -5 && !gm.getCollision(tileX , tileY - 1))
                        {
                            offY -= dt * speed;
                            diretion = 0;
                            animation += dt * 8;
                            if (animation >= 3)
                                animation = 0;
                        }
                }
                else
            {
            if (player.posY > this.posY+5 && !gm.getCollision(tileX , tileY + 1) )
            {
                diretion = 2;
            }
            else if (player.posY < this.posY -5 && !gm.getCollision(tileX , tileY - 1))
            {
                diretion = 0;
            }
            else if (player.posX > this.posX+1 && !gm.getCollision(tileX + 1, tileY) ) {
                    diretion = 1;
                }
            else if (player.posX < this.posX -1 && !gm.getCollision(tileX - 1, tileY) ) {
                    diretion = 3;
                }
                if(cooldowne == 0) {
                    gm.addObject(new BulletEnemy("bulletenemy", this.tileX, this.tileY, this.offX + width / 2, this.offY + width / 2, (int) (player.posX - 1), (int) (player.posY - 2),ew));
                    shot.play();
                    cooldowne = cooldownen;
                }
                else
                {
                    cooldowne--;
                }
            }


            if (offY > GameManager.TS / 2) {
                tileY++;
                offY -= GameManager.TS;
            }
            if (offY < -GameManager.TS / 2) {
                tileY--;
                offY += GameManager.TS;
            }
            if (offX > GameManager.TS / 2) {
                tileX++;
                offX -= GameManager.TS;
            }
            if (offX < -GameManager.TS / 2) {
                tileX--;
                offX += GameManager.TS;
            }
            posX = tileX * GameManager.TS + offX;
            posY = tileY * GameManager.TS + offY;
        }
        else
        {
            int random ;
                Random generator = new Random();
                random = generator.nextInt(5);
                if(random == 1) {
                    gm.addObject(new Crate(this.tileX, this.tileY, player));
                }
            this.dead = true;
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImageTile(enemyImage,(int)posX,(int)posY,(int)animation,diretion);
    }
}
