package com.game.game;

import com.game.engine.GameContainer;
import com.game.engine.Renderer;
import com.game.engine.gfx.Image;

import java.util.Random;

public class Crate extends GameObject {
    private final int tileX, tileY;
    private Player player;
    private Image t1 = new Image("/lvl/crate.png");

    public Crate(int tileX, int tileY, Player pl) {
        t1.setAlpha(false);
        this.tag = "crate";
        this.tileX = tileX;
        this.tileY = tileY;
        this.player = pl;
        posX = tileX * GameManager.TS - 8;
        posY = tileY * GameManager.TS - 8;

    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        if (this.posX > player.posX - 12 && this.posX < player.posX + 12 && this.posY > player.posY - 12 && this.posY < player.posY + 12) {
            int random;
            Random generator = new Random();
            random = generator.nextInt(9);
            if (random == 0) {
                player.setRiflebullet(player.getRiflebullet() + 30*gm.getLvlhard());
            } else if (random == 1 || random == 2 || random == 3) {
                player.setPistolbullet(player.getPistolbullet() + 30*gm.getLvlhard());

            } else {
                player.setHp(player.getHp() + 20*gm.getLvlhard());
            }
            this.dead = true;

        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        System.out.println(posX);
        System.out.println(posY);
        r.drawImage(t1, (int) posX, (int) posY);
    }
}
