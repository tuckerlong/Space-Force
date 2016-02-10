import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Asteroid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Asteroid extends GravObj {  
    public int hitCount;
    
    public Asteroid(int hitCount) {
        this.hitCount = hitCount;
    }
    
    protected void imageSetup() {
        setImage(new GreenfootImage(screen.getSprite("meteorBrown_big1.png")));
        getImage().scale(getImage().getWidth()*1/2, getImage().getHeight()*1/2);
        mass = 0;
        if(hitCount >= 4) {
            setImage(new GreenfootImage(screen.getSprite("meteorBrown_med1.png")));
            mass = 1;
        } 
        if(hitCount >= 7) {
            setImage(new GreenfootImage(screen.getSprite("meteorBrown_small1.png")));
            mass = 2;
        }
        if(hitCount >= 9) {
            setImage(new GreenfootImage(screen.getSprite("meteorBrown_tiny1.png")));
            mass = 4;
        }
        Random r = new Random();
        rotation = r.nextInt(360);
        setRotation(rotation);
        
    }
    
    public void hit() {
        hitCount++;
        if(hitCount == 4) {
            setImage(new GreenfootImage(screen.getSprite("meteorBrown_med1.png")));
            mass = 1;
        } 
        if(hitCount == 7) {
            setImage(new GreenfootImage(screen.getSprite("meteorBrown_small1.png")));
            mass = 2;
        }
        if(hitCount == 9) {
            setImage(new GreenfootImage(screen.getSprite("meteorBrown_tiny1.png")));
            mass = 4;
        }
        if(hitCount == 10)
            getWorld().removeObject(this);
    }
    
    public void customAct() {
        xSpeed = 0.5;
    }
}
