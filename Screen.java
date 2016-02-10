import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * Write a description of class Screen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Screen extends World
{
    private SpriteSheetReader sprites;
    private SpriteSheetReader ui;
    private Starmap starmap;
    public boolean menu = true;
    public Font font;
    
    private boolean shake = false, flip = false;
    private int tick = 0;
    private int chunks = 0;
    private TextField score;
    
    public int fuel = 100, fuelMax = 100;
    
    private Zone zone;
    
    private Random r;
    
    /**
     * Move Forward 
     * Turn Left
     * Turn Right
     * Warp
     * Select
     */
    public String[] keys = {"W","A","D","shift","space","m"};
    
    GreenfootSound bgMusic, bgBattle;
    
    public Screen() {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1, false); 
        
        setPaintOrder(Button.class, GameOver.class, Starship.class, Enemy.class, Chunk.class, Asteroid.class,
            Info.class, Zone.class, Health.class, Fuel.class, TextField.class, Starmap.class);
        Greenfoot.setSpeed(50);
        
        font = new Font("q",0,0);
        String path = Screen.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        InputStream is = Screen.class.getResourceAsStream("resources/kenpixel_blocks.ttf");
        Font uniFont = null;
        try {
            //font = Font.createFont(0,new File(+"resources\\Vdj.ttf"));//  "File" does not work with .jars
            uniFont=Font.createFont(Font.TRUETYPE_FONT,is);
         }
        catch(java.awt.FontFormatException r){
            System.err.println("FontFormatException: " + r.getMessage());
        }
        catch(java.io.IOException r){
            System.err.println("FontFormatException: " + r.getMessage());
        }
        font = uniFont.deriveFont(24f);
        
        /*try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/kenpixel_blocks.ttf"));
        } catch (IOException e) {
            System.out.println("Font file Test.ttf could not be found");
        } catch (FontFormatException e) {
            System.out.println("Font file Test.ttf has a formate exception");
        }*/
        
        r = new Random();
        
        // Fill background with black.
        getBackground().setColor(java.awt.Color.BLACK);
        getBackground().fill();
        
        // Create a sprite sheet reader for spaceship sprites.
        sprites = new SpriteSheetReader("sheet.png");
        sprites.readXML("resources/sheet.xml");
        
        // Create a sprite sheet reader for the ui sprites.
        ui = new SpriteSheetReader("uipackSpace_sheet.png");
        ui.readXML("resources/uipackSpace_sheet.xml");
        
        addObject(new MainMenu(), getWidth()/2, getHeight()/2);
        //start();      
        bgMusic = new GreenfootSound("HubTitle.mp3");
        //bgMusic.playLoop();
        bgMusic.setVolume(2);
        
        bgBattle = new GreenfootSound("BattleMusic.mp3");
        bgBattle.setVolume(0);
        //bgBattle.playLoop();
        Greenfoot.start();
        //addObject(new Asteroid(), 300, 200);
    }
    
    public void start() {
        java.util.List<Button> buttons = (java.util.List<Button>)getObjects(Button.class);
        for(int i = 0; i < buttons.size(); i++)
            buttons.get(i).remove();
            
        removeObjects(getObjects(TextField.class));
        removeObjects(getObjects(MainMenu.class));
        
        starmap = new Starmap();
        addObject(starmap, getWidth()/2, getHeight()/2);
        addObject(new Starship(), 400, 300);
        
        score = new TextField("Chunks: 0", Color.WHITE, 18.0f);
        addObject(score, getWidth() - 150, 30);
        
        addObject(new TextField("Health:", Color.WHITE, 18.0f), 75, 30);
        addObject(new Health(), 175, 29);
        Health hBar = (Health)getObjects(Health.class).get(0);
        hBar.update(100, 100);
        
        addObject(new TextField("Fuel:", Color.WHITE, 18.0f), getWidth()/2 - 30, 30);
        addObject(new Fuel(), getWidth()/2 + 55, 29);
        Fuel fBar = (Fuel)getObjects(Fuel.class).get(0);
        fBar.update(100, 100);  
    }
    
    boolean delay = true, on = true;
    int t = 10;
    double a = 0, b = 0;
    public void act() {
        if(on) {
            if(menu) {
                if(!bgMusic.isPlaying()) {
                    bgMusic.playLoop();
                }
                if(a+1 < 10) {
                    a += 0.1;
                    bgMusic.setVolume((int)a);
                    if(bgBattle.isPlaying())
                        bgBattle.setVolume(10-(int)a);
                } else
                    bgBattle.stop();
            } else {
                if(!bgBattle.isPlaying())
                    bgBattle.playLoop();
                if(a-1 > 0) {
                    a -= 0.05;
                    if(10 - (a - b) > 0) 
                        b += 0.5;
                    else
                        b = (10 - a);
                    bgMusic.setVolume((int)a);
                    bgBattle.setVolume(15-(int)a);
                } else
                    bgMusic.stop();
            }
        }
        
        if(!delay) t++;
        if(t > 10) delay = true;
        if(Greenfoot.isKeyDown(keys[5]) && delay) {
            on = !on;
            delay = false;
            t = 0;
            if(!on) {
                bgMusic.stop();
                bgBattle.stop();
            }
        }
        
        if(!menu) {
            if(getObjects(Enemy.class).size() == 0 || getObjects(Asteroid.class).size() == 0) {
                java.util.List<Asteroid> asteroids = getObjects(Asteroid.class);
                for(int i = 0; i < asteroids.size(); i++) {
                    int range = 0;
                    for(int c = 0; c < (10 - asteroids.get(i).hitCount); c++)
                        range += (r.nextInt(5) + 1);
                    for(int c = 0; c < range; c++)
                        addObject(new Chunk(r.nextFloat()*2 - 1, r.nextFloat()*2 - 1), asteroids.get(i).getX(), asteroids.get(i).getY());
                    removeObject(asteroids.get(i));
                }
                
                java.util.List<Chunk> chunks = getObjects(Chunk.class);
                for(int i = 0; i < chunks.size(); i++) {
                    chunks.get(i).toPlayer = true;    
                }
            }
        }
        
        if(shake && !flip) {
            ArrayList<GravObj> objs = (ArrayList<GravObj>)getObjects(GravObj.class);
            for(int i = 0; i < objs.size(); i++)
                objs.get(i).x -= 1;
                
            tick++;
            
            if(tick == 2) {
                tick = 0;
                flip = true;
            }
        } else if(shake && flip) {
            ArrayList<GravObj> objs = (ArrayList<GravObj>)getObjects(GravObj.class);
            for(int i = 0; i < objs.size(); i++)
                objs.get(i).x += 1;
                
            tick++;
            
            if(tick == 4) {
                tick = 0;
                shake = false;
            }
        } else if(!shake && flip) {
            ArrayList<GravObj> objs = (ArrayList<GravObj>)getObjects(GravObj.class);
            for(int i = 0; i < objs.size(); i++)
                objs.get(i).x -= 1;
                
            tick++;
            
            if(tick == 2) {
                tick = 0;
                flip = false;
            }
        } 
    }
    
    public GreenfootImage getSprite(String sprite) {
        return sprites.getSprite(sprite);
    }
    
    public GreenfootImage getUI(String sprite) {
        return ui.getSprite(sprite);
    }
    
    public void newGame() {
        
        
        java.util.List<GravObj> objs = (java.util.List<GravObj>)getObjects(GravObj.class);
        for(int i = 0; i < objs.size(); i++)
            objs.get(i).remove = true;
            
        java.util.List<Button> buttons = (java.util.List<Button>)getObjects(Button.class);
        for(int i = 0; i < buttons.size(); i++)
            buttons.get(i).remove();
        removeObjects(getObjects(Starship.Warp.class));
        
        removeObjects(getObjects(GameOver.class));
        removeObject(starmap);
        removeObjects(getObjects(Info.class));
        removeObjects(getObjects(Zone.class));
        starmap = new Starmap();
        
        addObject(starmap, getWidth()/2, getHeight()/2);
        addObject(new Starship(), 400, 300);
        
        fuel = 100;
        chunks = 0;
        score.update("Chunks: " + Integer.toString(chunks));
        Starship.health = Starship.healthMax;
        
        Health hBar = (Health)getObjects(Health.class).get(0);
        hBar.update(Starship.health, Starship.healthMax);
        
        Fuel fBar = (Fuel)getObjects(Fuel.class).get(0);
        fBar.update(fuel, fuelMax);
        menu = true;
    }
    
    public void refuel() {
        if(chunks >= 50) {
            chunks -= 50;
            score.update("Chunks: " + Integer.toString(chunks));
            fuel = Math.min(fuel + 10, fuelMax);
            
            Fuel fBar = (Fuel)getObjects(Fuel.class).get(0);
            fBar.update(fuel, fuelMax);
        }
    }
    
    public boolean removeChunks(int amount) {
        if(chunks >= 100) {
            chunks -= 100;
            score.update("Chunks: " + Integer.toString(chunks));
            return true;
        }
        return false;
    }
    
    public void startZone(Zone z) {
        if(!z.finished) {
            //bgMusic.setVolume(0);
            //bgBattle.setVolume(15);
            removeObject(starmap);
            removeObjects(getObjects(Info.class));
            removeObjects(getObjects(Zone.class));
            
            java.util.List<GravObj> objs = (java.util.List<GravObj>)getObjects(GravObj.class);
            for(int i = 0; i < objs.size(); i++)
                objs.get(i).remove = true;
                    
            addObject(new Starship(), 400, 300);
            
            menu = false;
            zone = z;
            z.spawn();
        }
    }
    
    public void shake() {
        if(!flip)
            shake = true;
    }
    
    public void updateScore(int newchunks) {
        chunks += newchunks;
        score.update("Chunks: " + Integer.toString(chunks));
    }
    
    public void warp() {
        //bgMusic.setVolume(10);
        //bgBattle.setVolume(0);
        
        if(fuel - 25 >= 0 || !menu) {
            boolean cleared = false;
            if(getObjects(Asteroid.class).size() == 0) {
                cleared = true;
                if(getObjects(Chunk.class).size() != 0)
                    return;
            }
            
            if(menu)
                fuel -= 25;
            Fuel fBar = (Fuel)getObjects(Fuel.class).get(0);
            fBar.update(fuel, fuelMax);   
            
            java.util.List<GravObj> objs = (java.util.List<GravObj>)getObjects(GravObj.class);
            for(int i = 0; i < objs.size(); i++)
                objs.get(i).remove = true;
            removeObjects(getObjects(Starship.Warp.class));
            
            if(menu) {
                removeObject(starmap);
                removeObjects(getObjects(Info.class));
                removeObjects(getObjects(Zone.class));
                removeObjects(getObjects(Starmap.Backdrop.class));
                starmap = new Starmap();
                map();
            } else {
                zone.finished = true;
                if(cleared) {
                    addObject(new Upgrade(), getWidth()/2, getHeight()/2);
                } else 
                    map();
            }
        }
    }
    
    public void map() {
        //removeObjects(getObjects(TextField.class));
        removeObjects(getObjects(Upgrade.class));
        
        addObject(new TextField("Health:", Color.WHITE, 18.0f), 75, 30);
        
        java.util.List<Button> buttons = (java.util.List<Button>)getObjects(Button.class);
        for(int i = 0; i < buttons.size(); i++)
            buttons.get(i).remove();
        
        addObject(starmap, getWidth()/2, getHeight()/2);
        addObject(new Starship(), 400, 300);
        menu = true;
    }
}
