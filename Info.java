import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Info here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Info extends Actor
{
    private Screen screen;
    public String name, loc, number, threat = "-", mining = "-", allies = "-";
    private 
        String[] 
            names = {"Delta", "Gamma", "Alpha", "Beta"},
            locations = {"Centari","Aquarius","Andromeda","Aquila"},
            numbers = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
            
    private int style = 0;
    
    public Info(World world){
        screen = (Screen)world;
        setImage(new GreenfootImage(screen.getUI("glassPanel_tab.png")));
        
        getImage().scale(getImage().getWidth()*5/4, getImage().getHeight()*5/4);
        Random r = new Random();
        name = names[r.nextInt(4)];
        loc = locations[r.nextInt(locations.length)];
        number = numbers[r.nextInt(numbers.length)];
        
        mining = Integer.toString(r.nextInt(9) + 1);
        int temp = r.nextInt(9);
        threat = Integer.toString(Math.max(Integer.parseInt(mining) + temp - 3, 1)); 
        
        style = 0;
    }    
    
    public Info(World world, int style){
        screen = (Screen)world;
        setImage(new GreenfootImage(screen.getUI("glassPanel_tab.png")));
        
        getImage().scale(getImage().getWidth()*5/4, getImage().getHeight()*5/4);        
        this.style = style;
    }  
    
    protected void addedToWorld(World world) {
        float font_size = 12.0f;
        getImage().setFont(screen.font);
        getImage().setFont(getImage().getFont().deriveFont(font_size));
        int len = getImage().getAwtImage().getGraphics().getFontMetrics(getImage().getFont()).stringWidth("Delta");
        
        getImage().setFont(screen.font);
        getImage().setFont(getImage().getFont().deriveFont(font_size));
        getImage().setColor(java.awt.Color.WHITE);
     
        if(style == 0) {
            getImage().drawString("Info", 20, 18);
            getImage().drawString(name + " SECTOR", 7, 35);
            getImage().drawString(loc + " " + number, 7, 52);
            getImage().drawString("Threat lvl: " + threat, 7, 98);
            getImage().drawString("Mining lvl: " + mining, 7, 115);
            getImage().drawString("Allies lvl: " + allies, 7, 132);
        } else if(style == 1) {
            getImage().drawString("Vendor", 20, 18);
            getImage().drawString("Buy 10 health", 7, 35);
            getImage().drawString("for 100 chunks", 7, 52);
            getImage().drawString("Space to buy.", 7, 132);
        } else if(style == 2) {
            getImage().drawString("Vendor", 20, 18);
            getImage().drawString("Buy 10 fuel", 7, 35);
            getImage().drawString("for 50 chunks", 7, 52);
            getImage().drawString("Space to buy.", 7, 132);
        }
    }
}
