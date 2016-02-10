import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends GravObj {  
    public void customAct() {
        applyLogic();
    }
    
    protected void applyLogic() {}
    
    protected void damage(int dmg) {}
    
    protected class Sensor extends Actor {
        public boolean intersect;
        public int ang;
        public Sensor(int ang) {
            setImage(new GreenfootImage(2, 120));
            getImage().setColor(java.awt.Color.YELLOW);
            getImage().fill();
            getImage().setTransparency(0);
            this.ang = ang;
        }
        
        public Sensor(int ang, int size) {
            setImage(new GreenfootImage(2, size));
            getImage().setColor(java.awt.Color.YELLOW);
            getImage().fill();
            getImage().setTransparency(0);
            this.ang = ang;
        }
        
        public void act() {
            if(getIntersectingObjects(GravObj.class).size() - getIntersectingObjects(Blaster.class).size() - getIntersectingObjects(Chunk.class).size() > 1) {
                getImage().setColor(java.awt.Color.RED);
                getImage().fill();
                intersect = true;
            } else {
                getImage().setColor(java.awt.Color.YELLOW);
                getImage().fill();
                intersect = false;
            }
        }
    }
}
