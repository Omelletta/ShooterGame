package com.game.game;

import com.game.engine.GameContainer;
import com.game.engine.Renderer;
import com.game.engine.gfx.ImageTile;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player extends GameObject {
    private int tileX,tileY;
    private float offX,offY;
    private int camX;
    private int camY;
    private int diretion = 0;
    private float animation = 0;
    private ImageTile playerImage = new ImageTile("/player/player.png",16,16);
    private float speed = 100;
    private int cooldown=0;
    private boolean reloading = false;
    private int hp;
    private int pistolbullet = 30;
    private int riflebullet = 210;
    private Weapon weapon;
    public Player(int posX, int posY,int hp, Weapon weapon)
    {
        this.tag = "player";
        this.posX = posX * GameManager.TS;
        this.posY = posY * GameManager.TS;
        this.tileX = posX;
        this.tileY = posY;
        this.offX = 0;
        this.offY = 0;
        this.hp = hp;
        this.width = GameManager.TS;
        this.height = GameManager.TS;
        this.weapon = weapon;
    }
    @Override
    public void update(GameContainer gc,GameManager gm, float dt) {
        for(GameObject go:gm.getObjects())
        {
            if(go.tag.equals("bulletenemy"))
            {
                if (go.posX >= this.posX && go.posX<this.posX+12 && go.posY >= this.posY && go.posY<this.posY+12)
                {
                    BulletEnemy be = (BulletEnemy)go;
                    this.hp-= be.getDamage();
                    go.dead = true;
                }
            }
        }

        //Moving
        if(hp>0) {
            if (gc.getInput().isKey(KeyEvent.VK_D)) {
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
            if (gc.getInput().isKey(KeyEvent.VK_A)) {
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
            if (gc.getInput().isKey(KeyEvent.VK_S)) {
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
            if (gc.getInput().isKey(KeyEvent.VK_W)) {
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
            //End moving
            //Final position
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
            //reloading
            if (gc.getInput().isKey(KeyEvent.VK_R) && !weapon.getReload().isRunning())
            {
                if (weapon.getType() == "pistol" && weapon.getMaxbullets()<=pistolbullet ) {
                    weapon.getReload().play();
                    weapon.setBullets(0);
                    pistolbullet -= weapon.getMaxbullets();
                    reloading = true;
                }
                else if ((weapon.getType() == "rifle" && weapon.getMaxbullets()<=riflebullet ))
                {
                    weapon.getReload().play();
                    weapon.setBullets(0);
                    riflebullet -= weapon.getMaxbullets();
                    reloading = true;
                }

            }
            if(!weapon.getReload().isRunning() && reloading == true)
            {
                weapon.setBullets(weapon.getMaxbullets());
                reloading = false;
            }
            //choosing weapon
            if (gc.getInput().isKey(KeyEvent.VK_1 ) && !reloading) {
                this.weapon = gm.getWeapons().get(0);
            }
            if (gc.getInput().isKey(KeyEvent.VK_2 )&& !reloading) {
                this.weapon = gm.getWeapons().get(1);

            }
            //shooting
            if (gc.getInput().isButton(MouseEvent.BUTTON1)) {
                if (cooldown == 0) {
                    if(weapon.getBullets()>0) {
                        gm.addObject(new Bullet("bullet",tileX, tileY, offX + width / 2, offY + width / 2, camX, camY, gc.getInput().getMouseX() - 1, gc.getInput().getMouseY() - 2, this));

                        weapon.getShot().play();
                        weapon.setBullets(weapon.getBullets()-1);
                        cooldown = weapon.getCooldown();
                    }
                } else {
                    cooldown--;
                }
            }
            if (gc.getInput().isKey(KeyEvent.VK_D)) {
                diretion = 1;
                animation += dt * 8;
                if (animation >= 3)
                    animation = 0;
            } else if (gc.getInput().isKey(KeyEvent.VK_A)) {
                diretion = 3;
                animation += dt * 8;
                if (animation >= 3)
                    animation = 0;
            } else if (gc.getInput().isKey(KeyEvent.VK_S)) {
                diretion = 2;
                animation += dt * 8;
                if (animation >= 3)
                    animation = 0;
            } else if (gc.getInput().isKey(KeyEvent.VK_W)) {
                diretion = 0;
                animation += dt * 8;
                if (animation >= 3)
                    animation = 0;
            } else {
                animation = 0;
            }
            //cheat
            if (gc.getInput().isKey(KeyEvent.VK_Z)){
                hp = 200;

            }
        }
        else
        {
            gm.setPlayr(1);
            this.dead = true;
        }
    }

    public int getHp() {
        return hp;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImageTile(playerImage,(int)posX,(int)posY,(int)animation,diretion);

    }

    public int getCamX() {
        return camX;
    }

    public void setCamX(int camX) {
        this.camX = camX;
    }

    public int getCamY() {
        return camY;
    }

    public void setCamY(int camY) {
        this.camY = camY;
    }

    public void setDiretion(int diretion) {
        this.diretion = diretion;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getPistolbullet() {
        return pistolbullet;
    }

    public void setPistolbullet(int pistolbullet) {
        this.pistolbullet = pistolbullet;
    }

    public int getRiflebullet() {
        return riflebullet;
    }

    public void setRiflebullet(int riflebullet) {
        this.riflebullet = riflebullet;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
