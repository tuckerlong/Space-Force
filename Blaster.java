import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Blaster here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Blaster extends GravObj
{
    private Actor target;
    private double moveX, moveY, speed = 8;
    private GreenfootSound sfx = new GreenfootSound("laser4mod.wav");
    private boolean remove = false;
    private Enemy source;
    
    public Blaster(Actor target, Enemy source) {
        this.target = target;
        this.source = source;
    }
    
    protected void imageSetup() {
        setImage(new GreenfootImage(screen.getSprite("laserRed02.png")));
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
        List<Starship> targets = getObjectsInRange(15, Starship.class);
        if(targets.size() > 0) {
            //targets.get(0).hit();
            //int range = r.nextInt(5) + 1;
            ///for(int i = 0; i < range; i++)
            //    screen.addObject(new Chunk(r.nextFloat()*2 - 1, r.nextFloat()*2 - 1), getX(), getY());
            targets.get(0).damage(1);
            remove = true;
        }
        
        List<Enemy> enemies = getObjectsInRange(15, Enemy.class);
        if(enemies.size() > 0 && !enemies.contains(source)) {
            //targets.get(0).hit();
            //int range = r.nextInt(5) + 1;
            ///for(int i = 0; i < range; i++)
            //    screen.addObject(new Chunk(r.nextFloat()*2 - 1, r.nextFloat()*2 - 1), getX(), getY());
            enemies.get(0).damage(1);
            remove = true;
        }
        
        if(getX() > screen.getWidth() + 10 || getX() < -10 || getY() > screen.getHeight() + 10 || getY() < -10)
            remove = true;
            
        if(remove)
            screen.removeObject(this);
    } 
}
