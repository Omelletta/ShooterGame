package com.game.game;

import com.game.engine.GameContainer;
import com.game.engine.Renderer;

public class Camera {
    private float offX, offY;
    private GameObject target = null;
    private String targetTag;

    public Camera(String tag)
    {
        this.targetTag = tag;
    }

    public void update(GameContainer gc,GameManager gm, float dt)
    {
        if(target == null)
        {
            target = gm.getObject(targetTag);
        }
        if(target == null)
            return;
        float targetX = (target.getPosX() + target.getWidth()/2) - gc.getWidth() /2;
        float targetY = (target.getPosY()+ target.getHeight()/2) - gc.getHeight()/2;
        offX -= dt*(offX - targetX) *5;
        offY -= dt*(offY - targetY) *5;
        Player player = (Player) target;
        gc.getRenderer().setCamX((int) offX);
        gc.getRenderer().setCamY((int) offY);
        player.setCamX((int) offX);
        player.setCamY((int) offY);
    }
    public void render(Renderer r)
    {
        r.setCamX((int) offX);
        r.setCamY((int) offY);
    }

    public float getOffX() {
        return offX;
    }

    public void setOffX(float offX) {
        this.offX = offX;
    }

    public float getOffY() {
        return offY;
    }

    public void setOffY(float offY) {
        this.offY = offY;
    }

    public GameObject getTarget() {
        return target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }

    public String getTargetTag() {
        return targetTag;
    }

    public void setTargetTag(String targetTag) {
        this.targetTag = targetTag;
    }
}
