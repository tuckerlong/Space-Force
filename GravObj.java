import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GravObj here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GravObj extends Actor
{
    public int gXSpeed, gYSpeed;
    public double xSpeed, ySpeed;
    public double x, y;
    public int rotation = 0;
    public int mass = 5;
    protected boolean remove = false, stopAct = false;
    protected Screen screen;
    
    protected void addedToWorld(World world) {
        screen = (Screen)world;
        x = getX();
        y = getY();
        imageSetup();
    } 
    
    public void act() {
        customAct();
        if(!stopAct)
            applyMovement();
        remove();
    }

    protected void applyMovement() {
        double prevX = x;
        double prevY = y;
        
        y -= (Math.cos(rotation * Math.PI / 180) * xSpeed * 1.5 + ((double)gYSpeed/100.0)); 
        x += (Math.sin(rotation * Math.PI / 180) * xSpeed * 1.5 + ((double)gXSpeed/100.0));
        
        this.setLocation((int)x, (int)y);
        //if(getObjectsInRange(45, GravObj.class).size() > 0)
        //    this.setLocation((int)prevX, (int)prevY);
        
        
        if(getY() <= 0) {
            setLocation(getX(), getWorld().getHeight()-2);
            y = getWorld().getHeight()-2;
        } else if(getY() >= getWorld().getHeight()-1) {
            setLocation(getX(), 1);
            y = 1;
        }
        
        if(getX() <= 0) {
            setLocation(getWorld().getWidth()-2, getY());
            x = getWorld().getWidth()-2;
        } else if(getX() >= getWorld().getWidth()-1) {
            setLocation(1, getY());
            x = 1;
        }
        
        if(xSpeed > 0) {
            xSpeed -= 0.1;
        } else xSpeed = 0;
    } 
    
    protected void imageSetup() {}
    protected void customAct() {}
    protected void remove() {
        if(remove)
            screen.removeObject(this);
    }
    
    protected int gravCount() {
        return getObjectsInRange(100, GravityOrb.class).size();
    }
}
