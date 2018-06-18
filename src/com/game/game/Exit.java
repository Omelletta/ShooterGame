package com.game.game;

import com.game.engine.GameContainer;
import com.game.engine.Renderer;

public class Exit extends GameObject {
    private Player player;

    public Exit(int posX, int posY, Player pl) {
        this.tag = "exit";
        this.posX = posX * GameManager.TS;
        this.posY = posY * GameManager.TS;
        this.width = GameManager.TS;
        this.height = GameManager.TS;
        this.player = pl;

    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        if (this.posX > player.posX - 5 && this.posX < player.posX + 5 && this.posY > player.posY - 5 && this.posY < player.posY + 5) {
            this.dead = true;
            gm.newlvl();

        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawFillRect((int) posX, (int) posY, 16, 16, 0xFFac0000);
    }
}
