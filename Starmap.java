import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.awt.*;
/**
 * Write a description of class Starmap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Starmap extends Actor
{
    private Screen screen;
    private HashMap<Actor, Point> zones;
    private boolean first = true;
    
    protected void addedToWorld(World world) {
        screen = (Screen)world;
        setImage(new GreenfootImage(screen.getWidth(), screen.getHeight()));
        
        if(first) {
            screen.addObject(new Backdrop(1, new Color(55,55,55,155), 3), getX(), getY());
            screen.addObject(new Backdrop(0.5f, new Color(105,105,105,105), 4), getX(), getY());
            
            createZones();
        } else
            setupMap();
    }
    
    public void createZones() {
        zones = new HashMap();
        first = false;
        Random r = new Random();
        for(int i = 0; i < 10; i++) {
            Zone z = new Zone();
            int x = r.nextInt(screen.getWidth() - 50) + 25;
            int y = r.nextInt(screen.getHeight() - 100) + 75;
            screen.addObject(z, x, y);
            if(z.checkZone()) {
                screen.removeObject(z);
                i--;
            } else {
                zones.put(z, new Point(x, y));
            }
        }
        
        int x = r.nextInt(screen.getWidth() - 50) + 25;
        int y = r.nextInt(screen.getHeight() - 100) + 75;
        HealthVendor hv = new HealthVendor();
        screen.addObject(hv, x, y);
        while(hv.checkZone()) {
            screen.removeObject(hv);
            x = r.nextInt(screen.getWidth() - 50) + 25;
            y = r.nextInt(screen.getHeight() - 100) + 75;
            screen.addObject(hv, x, y);
        }    
        zones.put(hv, new Point(x, y));
        
        x = r.nextInt(screen.getWidth() - 50) + 25;
        y = r.nextInt(screen.getHeight() - 100) + 75;
        FuelVendor fv = new FuelVendor();
        screen.addObject(fv, x, y);
        while(fv.checkZone()) {
            screen.removeObject(fv);
            x = r.nextInt(screen.getWidth() - 50) + 25;
            y = r.nextInt(screen.getHeight() - 100) + 75;
            screen.addObject(fv, x, y);
        }    
        zones.put(fv, new Point(x, y));
    }
    
    public void setupMap() {
        for(Actor a: zones.keySet()) {
            Point p = zones.get(a);
            screen.addObject(a, p.x, p.y);
        }
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
