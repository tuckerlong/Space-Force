import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Laser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Laser extends GravObj
{
    private Asteroid target;
    private double moveX, moveY, speed = 10;
    private GreenfootSound sfx = new GreenfootSound("laser4mod.wav");
    private boolean remove = false;
    
    public Laser(Asteroid target) {
        this.target = target;
    }
    
    protected void imageSetup() {
        setImage(new GreenfootImage(screen.getSprite("laserGreen02.png")));
        getImage().scale(getImage().getWidth()*1/4, getImage().getHeight()*1/4);
        
        if(Math.abs(target.getX() - getX()) > Math.abs(target.getY() - getY())) {
            if(target.getX() > getX()) moveX = speed;
            else moveX = -speed;
            double timeX = Math.abs((target.getX() - getX())/moveX);
            moveY = (target.getY() - getY())/timeX;
        } else {
            if(target.getY() > getY()) moveY = speed;
            else moveY = -speed;
            double timeY = Math.abs((target.getY() - getY())/moveY);
            moveX = (target.getX() - getX())/timeY;
        }
        
        if(target.getX() > getX()) {
            double adj = target.getY() - getY();
            double opp = target.getX() - getX();
            double hyp = Math.sqrt((adj*adj) + (opp * opp));
            double temp = ((Math.asin(adj/hyp)*180)/Math.PI);
            this.setRotation(90 + (int)temp);
        } else {
            double adj = target.getY() - getY();
            double opp = getX() - target.getX();
            double hyp = Math.sqrt((adj*adj) + (opp * opp));
            double temp = ((Math.acos(adj/hyp)*180)/Math.PI);
            this.setRotation(0 + (int)temp);
        }
        sfx.setVolume(60);
        if(screen.on)
            sfx.play();
    }
    
    public void act() {
        setLocation(getX() + (int)moveX, getY() + (int)moveY);
        Random r = new Random();
        List<Asteroid> asteroids = getObjectsInRange(15, Asteroid.class);
        if(asteroids.size() > 0) {
            asteroids.get(0).hit();
            int range = r.nextInt(5) + 1;
            for(int i = 0; i < range; i++)
                screen.addObject(new Chunk(r.nextFloat()*2 - 1, r.nextFloat()*2 - 1), getX(), getY());
            remove = true;
        }
        
        if(getX() > screen.getWidth() + 10 || getX() < -10 || getY() > screen.getHeight() + 10 || getY() < -10)
            remove = true;
            
        if(remove)
            screen.removeObject(this);
    }
}
