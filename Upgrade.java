import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Upgrade here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Upgrade extends Actor
{
    private Screen screen;
    private TextField info1, info2;
    
    protected void addedToWorld(World world) {
        screen = (Screen)world;
        try {
            //setImage(new GreenfootImage(screen.getUI("metalPanel_plate.png")));
            setImage(new GreenfootImage(screen.getWidth(), screen.getHeight()));
            getImage().setColor(new java.awt.Color(30,30,30,225));
            getImage().fill();
            
            info1 = new TextField("Upgrade Health", java.awt.Color.WHITE, 16f);
            info2 = new TextField("Upgrade Fuel", java.awt.Color.WHITE, 16f);
            
            screen.addObject(info1, screen.getWidth()/2 - 150, screen.getHeight()*2/5);
            screen.addObject(new Button(screen.getSprite("powerupRed_shield.png")) {
                public void active() {
                    Starship.healthMax += 2;
                    screen.map();
                    screen.removeObject(info1);
                    screen.removeObject(info2);
                    Health hBar = (Health)screen.getObjects(Health.class).get(0);
                    hBar.update(Starship.health, Starship.healthMax);
                }
                public void hover() {}
            }, screen.getWidth()/2 - 150, screen.getHeight()*2/4);
            
            screen.addObject(info2, screen.getWidth()/2 + 150, screen.getHeight()*2/5);
            screen.addObject(new Button(screen.getSprite("powerupGreen_bolt.png")) {
                public void active() {
                    screen.fuelMax += 5;
                    screen.map();
                    screen.removeObject(info1);
                    screen.removeObject(info2);
                    
                    Fuel fBar = (Fuel)screen.getObjects(Fuel.class).get(0);
                    fBar.update(screen.fuel, screen.fuelMax);  
                }
                public void hover() {}
            }, screen.getWidth()/2 + 150, screen.getHeight()*2/4);
            
        } catch(NullPointerException e) {
            System.out.println("Incorrect sprite name.");
        }
    }  
}
