import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.util.*;
/**
 * Write a description of class MainMenu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MainMenu extends Actor
{
    private Screen screen;
    
    protected void addedToWorld(World world) {
        getImage().setTransparency(0);
        screen = (Screen)world;
        
        screen.addObject(new Backdrop(1, new Color(75,75,75,155), 3), getX(), getY());
        screen.addObject(new Backdrop(0.5f, new Color(125,125,125,105), 4), getX(), getY());
        
        screen.addObject(new TextField("Space Force", Color.WHITE, 44f), screen.getWidth()/2, screen.getHeight()/2 - 100);
        
        screen.addObject(new Button("START GAME!", 2, 24f) {
            public void active() {
                screen.start();
            }
            public void hover() {}
        }, screen.getWidth()/2, screen.getHeight()/2 - 15);
        
        screen.addObject(new Button("Set Controls", 2, 24f) {
            public void active() {
                screen.addObject(new TextField("Select a key then click the controls key you want to set.", Color.WHITE, 18f), 
                screen.getWidth()/2, screen.getHeight()/2 + 75);
                
                screen.addObject(new TextField("Move Forward: ", Color.WHITE, 18f),
                    screen.getWidth()/2 - 15, screen.getHeight()/2 + 105);
                screen.addObject(new Button("W", 2, 18f) {
                    public void active() {
                        String key = Greenfoot.getKey();
                        redraw(key);
                        screen.keys[0] = key;
                    }
                    public void hover() {}
                }, screen.getWidth()/2 + 95, screen.getHeight()/2 + 99);
                
                screen.addObject(new TextField("Turn Left: ", Color.WHITE, 18f),
                    screen.getWidth()/2 - 15, screen.getHeight()/2 + 135);
                screen.addObject(new Button("A", 2, 18f) {
                    public void active() {
                        String key = Greenfoot.getKey();
                        redraw(key);
                        screen.keys[1] = key;
                    }
                    public void hover() {}
                }, screen.getWidth()/2 + 95, screen.getHeight()/2 + 129);
                
                screen.addObject(new TextField("Turn Right: ", Color.WHITE, 18f),
                    screen.getWidth()/2 - 15, screen.getHeight()/2 + 165);
                screen.addObject(new Button("D", 2, 18f) {
                    public void active() {
                        String key = Greenfoot.getKey();
                        redraw(key);
                        screen.keys[2] = key;
                    }
                    public void hover() {}
                }, screen.getWidth()/2 + 95, screen.getHeight()/2 + 159);
                
                screen.addObject(new TextField("Change Map/Leave Zone: ", Color.WHITE, 18f),
                    screen.getWidth()/2 - 45, screen.getHeight()/2 + 195);
                screen.addObject(new Button("Shift", 2, 17f) {
                    public void active() {
                        String key = Greenfoot.getKey();
                        redraw(key);
                        screen.keys[3] = key;
                    }
                    public void hover() {}
                }, screen.getWidth()/2 + 135, screen.getHeight()/2 + 189);
                
                screen.addObject(new TextField("Select Zone/Gravity Orb: ", Color.WHITE, 18f),
                    screen.getWidth()/2 - 45, screen.getHeight()/2 + 225);
                screen.addObject(new Button("Space", 2, 17f) {
                    public void active() {
                        String key = Greenfoot.getKey();
                        redraw(key);
                        screen.keys[4] = key;
                    }
                    public void hover() {}
                }, screen.getWidth()/2 + 145, screen.getHeight()/2 + 219);
                
                screen.addObject(new TextField("Mute: ", Color.WHITE, 18f),
                    screen.getWidth()/2 - 15, screen.getHeight()/2 + 255);
                screen.addObject(new Button("M", 2, 17f) {
                    public void active() {
                        String key = Greenfoot.getKey();
                        redraw(key);
                        screen.keys[5] = key;
                    }
                    public void hover() {}
                }, screen.getWidth()/2 + 65, screen.getHeight()/2 + 249);
            }
            
            public void hover() {}
        }, screen.getWidth()/2, screen.getHeight()/2 + 35);
        
        
    }
    
    public class Backdrop extends Actor {
        private float speed = 0, y;
        private boolean created = false;
        private Screen screen;
        private Color c;
        private int size;
        
        public Backdrop(float speed, Color c, int size) {
            this.speed = speed;
            this.c = c;
            this.size = size;
        }
        
        protected void addedToWorld(World world) {
            screen = (Screen)world;
            setImage(new GreenfootImage(screen.getWidth(), screen.getHeight()));
            
            Random r = new Random();
            
            for(int i = 0; i < 20; i++) {
                int x = r.nextInt(screen.getWidth() - 20) + 20;
                int y = r.nextInt(screen.getHeight() - 20) + 20;
                getImage().setColor(c);
                getImage().fillOval(x, y, size, size);
                //getImage().fill();
            }
            y = getY();
        }
        
        public void act() {
            if(getY() < screen.getHeight()/2 && !created) {
                created = true;
                screen.addObject(new Backdrop(speed, c, size), screen.getWidth()/2, (int)(y - speed) + getImage().getHeight());
            }
            
            y -= speed;
            setLocation(getX(), (int)y);
            
            if(getY() + getImage().getHeight()/2 <= 0)
                screen.removeObject(this);
        }
    }
}
