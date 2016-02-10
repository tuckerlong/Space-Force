import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class FuelVendor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FuelVendor extends GravObj
{
    private Info i;
    private boolean nearby = false, up = false;
    
    protected void imageSetup(){
        setImage(new GreenfootImage(screen.getSprite("enemyGreen3.png")));
        getImage().scale(getImage().getWidth()*1/4, getImage().getHeight()*1/4);
        i = new Info(screen, 2);
        remove = false;
    }  
    
    public void customAct() {
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
    
    public boolean checkZone() {
        if(getObjectsInRange(50, Zone.class).size() > 0)
            return true;
        return false;
    }  
}
