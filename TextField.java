import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * Write a description of class TextField here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TextField extends Actor
{
    private String text;
    private Color color;
    private float font_size;
    
    private Screen screen;
    private boolean click_active;
    
    // Fade and flash stuff
    private boolean fade, flash, flip;
    private int min_fade, fade_speed;
    
    private int trans = 255;
    
    public TextField(String text, Color color, float font_size) {
        this.text = text;
        this.color = color;
        this.font_size = font_size;
    }
    
    protected void addedToWorld(World world) {
        screen = (Screen)world;
        getImage().setFont(screen.font);
        getImage().setFont(getImage().getFont().deriveFont(font_size));
        int len = getImage().getAwtImage().getGraphics().getFontMetrics(getImage().getFont()).stringWidth(text);
        
        setImage(new GreenfootImage(len,(int)font_size));
        getImage().setFont(screen.font);
        getImage().setFont(getImage().getFont().deriveFont(font_size));
        getImage().setColor(color);
        getImage().drawString(text, 0, (int)font_size*5/6);
        //getImage().setTransparency(0);
    }
    
    public void act() {
        //if(Greenfoot.isKeyDown("shift")) {
        //    trans = 255;
        //} else if(trans - 2 > 0)
        //    trans -= 2;
        getImage().setTransparency(trans);
        
        if(fade) {    
            if(getImage().getTransparency() - fade_speed > min_fade && !flip)
                getImage().setTransparency(getImage().getTransparency() - fade_speed);
            else
                flip = true;
                
            if(flash && flip)
                if(getImage().getTransparency() + fade_speed < 255)
                    getImage().setTransparency(getImage().getTransparency() + fade_speed);
                else
                    flip = false;
        }
        
        if(click_active && Greenfoot.mouseClicked(null))
            active();
    }
    
    public void active() {}
    
    public void clickActive(boolean click_active) {
        this.click_active = click_active;
    }
    
    public void setFadeFlash(int min_fade, int fade_speed) {
        this.min_fade = min_fade;
        this.fade_speed = fade_speed;
        fade = true;
        flash = true;
    }
    
    public void update(String text) {
        getImage().setFont(screen.font);
        getImage().setFont(getImage().getFont().deriveFont(font_size));
        int len = getImage().getAwtImage().getGraphics().getFontMetrics(getImage().getFont()).stringWidth(text);
        
        setImage(new GreenfootImage(len,(int)font_size));
        getImage().setFont(screen.font);
        getImage().setFont(getImage().getFont().deriveFont(font_size));
        getImage().setColor(color);
        getImage().drawString(text, 0, (int)font_size*5/6);
        getImage().setTransparency(trans);
    }
}
