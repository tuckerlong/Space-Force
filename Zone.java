import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Zone here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zone extends Actor
{
    private Screen screen;
    private Info i;
    private boolean nearby = false, up = false;
    public boolean finished = false;
    
    protected void addedToWorld(World world) {
        screen = (Screen)world;
        if(finished)
            setImage(new GreenfootImage(screen.getSprite("numeralX.png")));
        else
            setImage(new GreenfootImage(screen.getSprite("numeral0.png")));
        i = new Info(screen);
        //getImage().scale(getImage().getWidth()*1/4, getImage().getHeight()*1/4);
    }
    
    public void act() {
        if(!finished) {
            if(getObjectsInRange(35, Starship.class).size() > 0) {
                nearby = true;
            } else {
                nearby = false;
                up = false;
                screen.removeObject(i);
            }
            if(nearby && !up) {
                up = true;
                int xmod = getX() + i.getImage().getWidth()/2 + 10;
                int ymod = getY() + i.getImage().getHeight()/2 + 10;
                if(xmod + i.getImage().getWidth()/2 > screen.getWidth())
                    xmod = getX() - i.getImage().getWidth()/2 - 10;
                    
                if(ymod + i.getImage().getHeight()/2 > screen.getHeight())
                    ymod = getY() - i.getImage().getHeight()/2 - 10;
                screen.addObject(i, xmod, ymod);
            }
        }
    }
    
    public boolean checkZone() {
        if(getObjectsInRange(50, Zone.class).size() > 0)
            return true;
        return false;
    }
    
    public void spawn() {
        Random r = new Random();
        int numAst = Integer.parseInt(i.mining);
        for(int i = 0; i < numAst; i++) {
            int x = r.nextInt(screen.getWidth() - 20) + 20;
            int y = r.nextInt(screen.getHeight() - 20) + 20;
            screen.addObject(new Asteroid(r.nextInt(10)), x, y);
        }
        
        numAst = Integer.parseInt(i.threat);
        for(int i = 0; i < numAst; i++) {
            int x = r.nextInt(screen.getWidth() - 20) + 20;
            int y = r.nextInt(screen.getHeight() - 20) + 20;
            Fighter f = new Fighter();
            screen.addObject(f, x, y);
            if(!f.goodSpawn()) {
                f.remove = true;
                f.remove();
                i--;
            }
        }
        
        if(r.nextInt(2) == 0) {
            int x = r.nextInt(screen.getWidth() - 20) + 20;
            int y = r.nextInt(screen.getHeight() - 20) + 20;
            screen.addObject(new Asteroid(7), x, y);
        }
            
    }
}
