package com.game.game;

import com.game.engine.audio.SoundClip;

public class EnemyWeapon {
    private int cooldown;
    private int color;
    private int damage;
    private SoundClip sound;

    EnemyWeapon(int cooldown, int color, int damage, String sound) {
        this.cooldown = cooldown;
        this.color = color;
        this.damage = damage;
        this.sound = new SoundClip(sound);
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public SoundClip getSound() {
        return sound;
    }

    public void setSound(SoundClip sound) {
        this.sound = sound;
    }
}
