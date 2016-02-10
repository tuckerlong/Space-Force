import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class GravityOrb here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GravityOrb extends GravObj
{
    private Screen screen;
    private int max = 300;
    private Range r;
    
    protected void addedToWorld(World world) {
        screen = (Screen)world;
        
        setImage(new GreenfootImage(screen.getSprite("ufoYellow.png")));
        getImage().scale(getImage().getWidth()*1/8, getImage().getHeight()*1/8);
        
        r = new Range();
        screen.addObject(r, getX(), getY());
        x = getX();
        y = getY();
    }    
    
    public void act() {
        List<GravObj> objs = getObjectsInRange(100, GravObj.class);
        for(int i = 0; i < objs.size(); i++) {
            GravObj obj = objs.get(i);
            int dx = obj.getX() - getX();
            int dy = obj.getY() - getY();
            double hyp = Math.sqrt((dx * dx) + (dy * dy));
            
            if(dy != 0) {
                if(obj.gYSpeed <= max && obj.gYSpeed >= -max) obj.gYSpeed  += obj.mass * (Math.abs(dy)/dy);
                if(obj.gYSpeed > max) obj.gYSpeed = max;
                if(obj.gYSpeed < -max) obj.gYSpeed = -max ;
            }
            
            if(dx != 0) {
                if(obj.gXSpeed <= max && obj.gXSpeed >= -max) obj.gXSpeed  -= obj.mass * (Math.abs(dx)/dx);
                if(obj.gXSpeed > max) obj.gXSpeed = max;
                if(obj.gXSpeed < -max) obj.gXSpeed = -max;
            }
        }
        
        applyMovement();
        r.setLocation(getX(), getY());
        remove();
    }
    
    public void remove() {
        if(remove) {
            screen.removeObject(r);
            screen.removeObject(this);
        }
    }
    
    
    public class Range extends Actor {
        public Range() {
            setImage(new GreenfootImage(200,200));
            getImage().setColor(java.awt.Color.GREEN.darker().darker().darker());
            getImage().drawOval(0,0,200,200);
            getImage().setColor(new java.awt.Color(0,0,0,0));
            getImage().fillRect(0,0,200,200);
        }
        
        public void act() {
            turn(1);
        }
    }
}
