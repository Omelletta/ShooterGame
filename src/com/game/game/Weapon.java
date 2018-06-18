package com.game.game;

import com.game.engine.audio.SoundClip;

public class Weapon {
    private int bullets;
    private int maxbullets;
    private int cooldown;
    private SoundClip shot;
    private SoundClip reload;
    private String name;
    private int power;
    private String type;

    Weapon(int bullets, int cooldown, String shot, String reload, String name, int power, String type) {
        this.bullets = bullets;
        this.maxbullets = bullets;
        this.cooldown = cooldown;
        this.shot = new SoundClip(shot);
        this.reload = new SoundClip(reload);
        this.name = name;
        this.power = power;
        this.type = type;
    }

    public int getBullets() {
        return bullets;
    }

    public void setBullets(int bullets) {
        this.bullets = bullets;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public SoundClip getShot() {
        return shot;
    }

    public void setShot(SoundClip shot) {
        this.shot = shot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxbullets() {
        return maxbullets;
    }

    public int getPower() {
        return power;
    }

    public SoundClip getReload() {
        return reload;
    }

    public String getType() {
        return type;
    }
}
