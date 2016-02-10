import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameOver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameOver extends Actor
{
    private Screen screen;
    
    protected void addedToWorld(World world) {
        screen = (Screen)world;
        try {
            //setImage(new GreenfootImage(screen.getUI("metalPanel_plate.png")));
            setImage(new GreenfootImage(screen.getWidth(), screen.getHeight()));
            getImage().setColor(new java.awt.Color(30,30,30,225));
            getImage().fill();
            
            screen.addObject(new Button("New Game", 2, 28f) {
                public void active() {
                    remove();
                    screen.newGame();
                }
                public void hover() {}
            }, screen.getWidth()/2, screen.getHeight()*2/4);
            
            //screen.addObject(new Button("Restart Map", 2) {
             //   public void hover() {}
            //}, screen.getWidth()/2, screen.getHeight()*3/5);
        } catch(NullPointerException e) {
            System.out.println("Incorrect sprite name.");
        }
    }
}
