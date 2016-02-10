import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Button extends Actor {
    private int delay;
    private boolean ready = true, hover = false, remove = false, border = true;
    public boolean clicked = false;
    private String name;
    private float size = 22.0f;
    private GreenfootImage img;
    private int style = 0;
    
    public Button(String name) {
        this.name = name;    
    }
    
    public Button(GreenfootImage img) {
        this.img = img;
        style = -1;
    }
    
    public Button(String name, int style) {
        this.name = name;
        
        switch(style) {
            case 2:
                border = false;
                break;
            default:
                break;
        }
    }
    
    public Button(String name, int style, float size) {
        this.name = name;
        this.size = size;
        
        switch(style) {
            case 2:
                border = false;
                break;
            default:
                break;
        }
    }
    
    public void addedToWorld(World world) {
        if(style == -1) {
            setImage(img);
        } else {
            getImage().setFont(((Screen)world).font);
            getImage().setFont(getImage().getFont().deriveFont(size));
            
            int w = getImage().getAwtImage().getGraphics().getFontMetrics(getImage().getFont()).stringWidth(name);
            
            setImage(new GreenfootImage(w + 6,25));
            getImage().setColor(java.awt.Color.WHITE);
            getImage().setFont(((Screen)world).font);
            getImage().setFont(getImage().getFont().deriveFont(size));
            
            if(border) {
                getImage().drawRect(0, 0, getImage().getWidth()-1, getImage().getHeight()-1);
                getImage().drawRect(1, 1, getImage().getWidth()-3, getImage().getHeight()-3);
            }
            
            getImage().drawString(name, 3, 24);
        }
    }
    
    public void act() {
        if(!remove) {
            delay++;
            if(delay >= 10) ready = true;
            
            if(Greenfoot.mouseClicked(this) && ready) {            
                clear();
                active();
            }
            
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if(mouse != null) {
                if(mouse.getX() > getX() - getImage().getWidth()/2 && mouse.getX() < getX() + getImage().getWidth()/2 &&
                    mouse.getY() > getY() - getImage().getHeight()/2 && mouse.getY() < getY() + getImage().getHeight()/2) {
                    
                    if(!hover)
                        hover();
                    hover = true;
                } else if(hover) {
                    hover = false;
                    clearHover();
                }  
            }
        } else {
            getWorld().removeObject(this);
        }
    }   
    
    public void active() {
    }
    
    public void clearHover(){}
    
    public abstract void hover();
    
    private void clear() {
        delay = 0;
        ready = false;
    }
    
    public void redraw(String name) {
        getImage().setFont(((Screen)getWorld()).font);
            getImage().setFont(getImage().getFont().deriveFont(size));
            
            int w = getImage().getAwtImage().getGraphics().getFontMetrics(getImage().getFont()).stringWidth(name);
            
            setImage(new GreenfootImage(w + 6,25));
            getImage().setColor(java.awt.Color.WHITE);
            getImage().setFont(((Screen)getWorld()).font);
            getImage().setFont(getImage().getFont().deriveFont(size));
            
            if(border) {
                getImage().drawRect(0, 0, getImage().getWidth()-1, getImage().getHeight()-1);
                getImage().drawRect(1, 1, getImage().getWidth()-3, getImage().getHeight()-3);
            }
            
            getImage().drawString(name, 3, 24);
    }
    
    public void remove() {
        remove = true;
    }
}

