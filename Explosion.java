import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Explosion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Explosion extends Actor
{
    private Screen screen;
    private GreenfootSound exp = new GreenfootSound("Explosion.wav");
    public Explosion() {
        
    }
    
    protected void addedToWorld(World world) {
        screen = (Screen)world;
        setImage(new GreenfootImage("explosion00.png"));
        getImage().scale(getImage().getWidth()*1/8, getImage().getHeight()*1/8);
        
        exp.setVolume(65);
        if(screen.on)
            exp.play();
    } 
    
    public void act() {
        if(getImage().getTransparency() - 5 > 0) {
            getImage().setTransparency(getImage().getTransparency() - 5);
        } else {
            screen.removeObject(this);
        }
    }
}
