import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Starship here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Starship extends GravObj
{
    private Screen screen;
    
    public int tick = 0, miningTick = 0, damageTick = 0;
    public static int health = 100, healthMax = 100;
    
    private boolean cooldown = false, miningCooldown = false, damageCooldown = false, clearWarp = false;
    private Speed s;
    private Warp w;
    
    protected void addedToWorld(World world) {
        screen = (Screen)world;
        x = getX();
        y = getY();
        
        setImage(new GreenfootImage(screen.getSprite("playerShip1_green.png")));
        getImage().scale(getImage().getWidth()*1/4, getImage().getHeight()*1/4);
        setRotation(0);
        
        w = new Warp();
        screen.addObject(w, getX(), getY());
        //s = new Speed();
        //s.getImage().mirrorVertically();
        //screen.addObject(s, getX() - 1, getY() + getImage().getHeight()/2 + s.getImage().getHeight());
    }
    
    public void act() 
    {
        if(Greenfoot.isKeyDown(screen.keys[0])){
            xSpeed += 0.5;
            if(xSpeed > 3) xSpeed = 3;
        }
        
        if(Greenfoot.isKeyDown(screen.keys[2])) {
            rotation += 4;
            if(rotation >= 360) rotation -= 360;
            turn(4);
        }
        
        if(Greenfoot.isKeyDown(screen.keys[1])) {
            rotation -= 4;
            if(rotation < 0) rotation += 360;
            turn(-4);
        }
        
        if(Greenfoot.isKeyDown(screen.keys[3])) {
            w.increase();
            clearWarp = true;
        } else if(clearWarp) {
            w.clear();
            clearWarp = false;
        }
        
        
        if(!cooldown) tick++;
        if(tick > 20) cooldown = true;
        
        if(Greenfoot.isKeyDown(screen.keys[4]) && cooldown && !screen.menu) {
            screen.addObject(new GravityOrb(), getX(), getY());
            cooldown = false;
            tick = 0;
        }
        
        if(Greenfoot.isKeyDown(screen.keys[4]) && screen.menu && getObjectsInRange(35, Zone.class).size() > 0) {
            Zone z = (Zone)getObjectsInRange(35, Zone.class).get(0);
            screen.startZone(z);
            cooldown = false;
            tick = 0;
        }
        
        if(Greenfoot.isKeyDown(screen.keys[4]) && screen.menu && getObjectsInRange(35, HealthVendor.class).size() > 0 && cooldown) {
            cooldown = false;
            tick = 0;
            
            
            if(screen.removeChunks(100)) {
                health = Math.min(health + 10, healthMax);
                Health hBar = (Health)(screen.getObjects(Health.class).get(0));
                hBar.update(health, healthMax);
            }
        }
        
        if(Greenfoot.isKeyDown(screen.keys[4]) && screen.menu && getObjectsInRange(35, FuelVendor.class).size() > 0 && cooldown) {
            cooldown = false;
            tick = 0;
            
            screen.refuel();
        }
        
        if(getObjectsInRange(100, GravityOrb.class).size() == 0) {
            //gYSpeed = 0;
            //gXSpeed = 0;
            //System.out.println("Out");
            if(gYSpeed != 0) gYSpeed += ((Math.abs(gYSpeed)/(gYSpeed * -1.0)) * 5);
            if(gXSpeed != 0) gXSpeed += ((Math.abs(gXSpeed)/(gXSpeed * -1.0)) * 5);
        }
        
        if(!miningCooldown) miningTick++;
        if(miningTick > 50) miningCooldown = true;
        
        if(miningCooldown) {
            miningCooldown = false;
            miningTick = 0;
            List<Asteroid> asteroids = getObjectsInRange(150, Asteroid.class);
            for(int i = 0; i < asteroids.size(); i++) {
                screen.addObject(new Laser(asteroids.get(i)), getX(), getY());
            }
        }
        
        if(!damageCooldown) damageTick++;
        if(damageTick > 50) damageCooldown = true;
        
        List<Asteroid> asteroids = getIntersectingObjects(Asteroid.class);
        if(asteroids.size() > 0 && damageCooldown) {
            damageCooldown = false;
            damageTick = 0;
            damage(10);
            //getImage().drawImage(new GreenfootImage(screen.getSprite("playerShip1_damage1.png")), 0, 0);
        }
        
        applyMovement();
        w.setLocation(getX(), getY());
        w.setRotation(rotation);
        //s.setLocation(getX() - 1, getY() + getImage().getHeight()/2 + s.getImage().getHeight());
        //s.getImage().mirrorVertically();
        //s.setRotation(rotation);
        
        if(remove) {
            screen.removeObject(this);
        }
    } 
    
    public void damage(int dmg) {
        health -= dmg;
        screen.shake();
        
        if(health <= 0) {
            screen.addObject(new Explosion(), getX(), getY());
            remove = true;
            screen.addObject(new GameOver(), screen.getWidth()/2, screen.getHeight()/2);
        } else {
            Health hBar = (Health)(screen.getObjects(Health.class).get(0));
            hBar.update(health, healthMax);
        }
    }
    
    public class Speed extends Actor {
        protected void addedToWorld(World world) {
            setImage(new GreenfootImage(screen.getSprite("speed.png")));
            getImage().scale(getImage().getWidth()*1/4, getImage().getHeight()*1/4);
        }
    }
    
    public class Warp extends Actor {
        protected void addedToWorld(World world) {
            setImage(new GreenfootImage(screen.getSprite("shield3.png")));
            getImage().scale(getImage().getWidth()*1/4, getImage().getHeight()*1/4);
            getImage().setTransparency(0);
        }
        
        public void increase() {
            if(getImage().getTransparency() + 4 < 255)
                getImage().setTransparency(getImage().getTransparency() + 4);
            else
                screen.warp();
        }
        
        public void clear() {
            getImage().setTransparency(0);    
        }
    }
}
