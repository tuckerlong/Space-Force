import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Health here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Health extends Actor
{
    private Screen screen;
    
    protected void addedToWorld(World world){
        screen = (Screen)world;
        setImage(new GreenfootImage(100, 16));
        getImage().setColor(java.awt.Color.WHITE);
        getImage().drawRect(0,0,99,15);
        getImage().drawRect(1,1,97,13);
        //getImage().scale(getImage().getWidth()*1/4, getImage().getHeight()*1/4);
    }    
    
    public void update(int cur, int max) {
        getImage().clear();
        getImage().setColor(java.awt.Color.RED.darker());
        getImage().fillRect(0,0,(int)(((double)cur/(double)max)*100),15);
        
        //getImage().setColor(java.awt.Color.RED);
        //getImage().fillRect(0,0,cur,5);
        
        String str = Integer.toString(cur) +"/" + Integer.toString(max);
        
        getImage().setColor(java.awt.Color.WHITE);
        getImage().drawRect(0,0,99,15);
        getImage().drawRect(1,1,97,13);
        
        getImage().setFont(screen.font);
        getImage().setFont(getImage().getFont().deriveFont(16.0f));
        int len = getImage().getAwtImage().getGraphics().getFontMetrics(getImage().getFont()).stringWidth(str);
        
        getImage().setFont(screen.font);
        getImage().setFont(getImage().getFont().deriveFont(16.0f));
        getImage().setColor(java.awt.Color.WHITE);
        getImage().drawString(str, getImage().getWidth()/2 - len/2, 14);
    }
}
