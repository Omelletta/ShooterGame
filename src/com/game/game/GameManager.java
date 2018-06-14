package com.game.game;

import com.game.engine.AbstractGame;
import com.game.engine.GameContainer;
import com.game.engine.Renderer;
import com.game.engine.gfx.Image;
import com.game.mapgenerator.MapImage;
import com.game.mapgenerator.SimplexNoiseGenerator;
import com.game.mapgenerator.WorldGenerator;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameManager extends AbstractGame {
    WorldGenerator worldgen = new SimplexNoiseGenerator(7, 0.1f, 0.0150f);
    MapImage mi = new MapImage();
    public static final int TS = 16;
    private Image t1;
    private Image tm ;
    private Image menu1;
    private Image menu2;
    private Image menu;
    private int where;
    private int playr = 0;
    private int lvlhard = 1;
    private Exit exit;
    private int state = 0;

    private ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private Camera camera;

    private boolean[] collision ;
    private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
    private int levelW, levelH;

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    public GameManager()
    {
        t1 =  new Image("/lvl/t1.png");
        tm = new Image("/lvl/tm.png");
        menu1 =  new Image("/menu/menu1.png");
        menu2 = new Image("/menu/menu2.png");
        double[][] array = worldgen.createWorld(lvlhard*150, lvlhard*200);
        BufferedImage bf = mi.visualize(array);
        Weapon weapon1 = new Weapon(6,40,"/audio/pistolfire.wav","/audio/pistolreload.wav","Pistolet",20,"pistol");
        weapons.add(weapon1);
        Weapon weapon2 = new Weapon(30,5,"/audio/riflefire.wav","/audio/riflereload.wav","Karabin",10,"rifle");
        weapons.add(weapon2);
        loadLevel(new Image(bf));
        this.setPlayer(weapons,200);
        this.setEnemies();
        camera = new Camera("player");
        this.setExit();
        menu = menu1;
        where = 1;
    }
    public void restart()
    {
        playr = 0;
        lvlhard = 1;
        objects.clear();
        weapons.clear();
        double[][] array = worldgen.createWorld(lvlhard*150, lvlhard*200);
        BufferedImage bf = mi.visualize(array);
        Weapon weapon1 = new Weapon(6,40,"/audio/pistolfire.wav","/audio/pistolreload.wav","Pistolet",20,"pistol");
        weapons.add(weapon1);
        Weapon weapon2 = new Weapon(30,5,"/audio/riflefire.wav","/audio/riflereload.wav","Karabin",10,"rifle");
        weapons.add(weapon2);
        loadLevel(new Image(bf));
        this.setEnemies();
        this.setPlayer(weapons,200);
        this.setExit();
        camera = new Camera("player");
    }
    public void newlvl()
    {
        lvlhard++;
        int hp = 200;
        for(int i = 0; i <objects.size(); i++)
        {
            if(objects.get(i).getTag().equals("player"))
            {
                Player pl = (Player) objects.get(i);
                hp = pl.getHp();
            }
        }

        playr = 0;
        objects.clear();
        double[][] array = worldgen.createWorld(lvlhard*150, lvlhard*200);
        BufferedImage bf = mi.visualize(array);
        loadLevel(new Image(bf));
        this.setEnemies();
        this.setPlayer(weapons,hp);
        this.setExit();
        camera = new Camera("player");
    }
    private void setPlayer(ArrayList<Weapon> wp,int hp)
    {
        int random ;
        int random2;
        while (true) {
            Random generator = new Random();
            random = generator.nextInt(lvlhard*20);
            random2 = generator.nextInt(lvlhard*15);
            if(collision[random+random2*levelW] == false)
            {
                break;
            }
        }
        objects.add(new Player(random, random2,hp, wp.get(1)));
    }
    private void setExit() {
            int random;
            int random2;
            while (true) {
                Random generator = new Random();
                random = generator.nextInt(lvlhard * 20);
                random2 = generator.nextInt(lvlhard * 15);
                if (collision[random + random2 * levelW] == false) {
                    break;
                }
            }
            for (int i = 0; i < objects.size(); i++) {
                if (objects.get(i).getTag().equals("player")) {
                    Player pl = (Player) objects.get(i);
                    exit = new Exit(random, random2, pl);
                }
            }
            objects.add(exit);
    }
    private void setEnemies()
    {
        EnemyWeapon ew = new EnemyWeapon(40,0xff1b25e7,lvlhard*20,"/audio/pistolfire.wav");
        EnemyWeapon ew2 = new EnemyWeapon(5,0xff1b25e7,lvlhard*5,"/audio/riflefire.wav");
        int random ;
        int random2;
        for (int i =0;i<5*lvlhard;i++)
        {
            while(true) {
                Random generator = new Random();
                random = generator.nextInt(lvlhard*20);
                random2 = generator.nextInt(lvlhard*15);
                if(collision[random+random2*levelW] == false)
                {
                    break;
                }
            }
            if(i<4*i - i) {
                objects.add(new Enemy("enemy1", random, random2, lvlhard*50, 100, ew));
            }
            else
            {
                objects.add(new Enemy("enemy1", random, random2, lvlhard*100, 100, ew2));
            }
        }
    }
    private void setexit()
    {

    }

    @Override
    public void init(GameContainer gc) {
        gc.getRenderer().setAmbientColor(-1);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if(state == 1) {
            gc.getRenderer().drawText("Poziom " + lvlhard, 240, 0, 0xff00ffff);
            for (int i = 0; i < objects.size(); i++) {
                objects.get(i).update(gc, this, dt);
                if (objects.get(i).dead) {
                    objects.remove(i);
                    i--;
                }
            }
            if (playr == 1) {
                this.restart();
            }

            if (gc.getInput().isKey(KeyEvent.VK_Q))
                this.newlvl();
            camera.update(gc, this, dt);
            if(gc.getInput().isKey(KeyEvent.VK_ESCAPE))
            {
                state = 0;
            }
        }
        else
        {
            if(gc.getInput().isKey(KeyEvent.VK_ENTER) && where == 0)
            {
                this.restart();
                state = 1;
            }
            else if(gc.getInput().isKey(KeyEvent.VK_ENTER) && where == 1)
            {

            }
            if(((gc.getInput().isKeyDown(KeyEvent.VK_W))||(gc.getInput().isKeyDown(KeyEvent.VK_S))) && where == 0)
            {
                where = 1;
                menu = menu2;
            }
            else if(((gc.getInput().isKeyDown(KeyEvent.VK_W))||(gc.getInput().isKeyDown(KeyEvent.VK_S))) && where == 1)
            {
                where = 0;
                menu = menu1;
            }
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        if (state == 1) {
            camera.render(r);
            for (int y = 0; y < levelH; y++) {
                for (int x = 0; x < levelW; x++) {
                    if (collision[x + y * levelW] == true) {
                        // r.drawFillRect(x*TS, y*TS,TS,TS,0xff0f0f0f);

                        r.drawImage(t1, x * TS, y * TS);
                    } else {
                        // r.drawFillRect(x*TS, y*TS,TS,TS,0xfff9f9f9);
                        r.drawImage(tm, x * TS, y * TS);
                    }
                }
            }
            for (GameObject obj : objects) {
                if (obj.tag.equals("crate"))
                    obj.render(gc, r);
            }
            for (GameObject obj : objects) {
                if (!obj.tag.equals("crate"))
                    obj.render(gc, r);
            }
            for (GameObject obj : objects) {
                if (obj.tag.equals("player")) {
                    Player pl = (Player) obj;
                    r.drawText("Naboje do pisoletu: " + pl.getPistolbullet(), 0, 10, 0xff00ffff);
                    r.drawText("Naboje do karabinu: " + pl.getRiflebullet(), 0, 20, 0xff00ffff);
                    r.drawText("W magazynku " + pl.getWeapon().getName() + " :" + pl.getWeapon().getBullets(), 0, 30, 0xff00ffff);
                    r.drawText("Zycie:" + pl.getHp(), 0, 230, 0xff00ffff);
                }
            }
            r.drawText("Poziom " + lvlhard, 0, 220, 0xff00ffff);
        }
        else
        {
            r.drawImage(menu,0,0);
        }

    }
    public void loadLevel(Image img)
    {
        Image levelImage = img;
        levelW = levelImage.getW();
        levelH = levelImage.getH();
        collision = new boolean[levelW * levelH];
        for (int y = 0; y < levelImage.getH();y++)
        {
            for (int x = 0; x < levelImage.getW();x++)
            {
                if(levelImage.getP()[x + y * levelImage.getW()] == 0xff000000)
                {
                    collision[x+y*levelImage.getW()] = true;
                }
                else
                {
                    collision[x+y*levelImage.getW()] = false;
                }
            }
        }
    }
    public void addObject(GameObject object)
    {
        objects.add(object);
    }
    public GameObject getObject(String tag)
    {
        for(int i=0;i<objects.size();i++)
        {
            if(objects.get(i).tag.equals(tag))
            {
                return objects.get(i);
            }
        }
        return null;

    }
    public boolean getCollision(int x, int y)
    {
        if(x < 0 || x>=levelW || y<0 || y>= levelH)
            return true;
        return collision[x+y*levelW];
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void setPlayr(int playr) {
        this.playr = playr;
    }

    public static void main(String args[])
    {
        GameContainer gc = new GameContainer(new GameManager());
        gc.setWidth(320);
        gc.setHeight(240);
        gc.setScale(3f);
        gc.start();
    }

}
