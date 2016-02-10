import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Chunk here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Chunk extends GravObj {
    private float moveX, moveY, x, y;
    public boolean toPlayer = false;
    
    public Chunk(float moveX, float moveY) {
        this.moveX = moveX;
        this.moveY = moveY;
    }

    protected void imageSetup() {
        Random r = new Random();
        if(r.nextInt(10) > 5)
            setImage(new GreenfootImage(screen.getSprite("meteorBrown_tiny1.png")));
        else
            setImage(new GreenfootImage(screen.getSprite("meteorBrown_tiny2.png")));
        getImage().scale(getImage().getWidth()*1/4, getImage().getHeight()*1/4);
        x = getX();
        y = getY();
        
        rotation = r.nextInt(360);
    }   
    
    public void customAct() {
        /*if(getObjectsInRange(100, GravityOrb.class).size() == 0) {
            //gYSpeed = 0;
            //gXSpeed = 0;
            //System.out.println("Out");
            if(gYSpeed != 0) gYSpeed += ((Math.abs(gYSpeed)/(gYSpeed * -1.0)) * 5);
            if(gXSpeed != 0) gXSpeed += ((Math.abs(gXSpeed)/(gXSpeed * -1.0)) * 5);
        }*/
        
        //x += moveX + (gXSpeed/100.0);
        //y += moveY - (gYSpeed/100.0);
        xSpeed = moveX;
        ySpeed = moveY;
        //setLocation((int)x, (int)y);
        
        if(toPlayer) {
            xSpeed = 5;
            Starship target = (Starship)screen.getObjects(Starship.class).get(0);
            
            double moveX, moveY;
            
            if(Math.abs(target.getX() - getX()) > Math.abs(target.getY() - getY())) {
                if(target.getX() > getX()) moveX = xSpeed;
                else moveX = -xSpeed;
                double timeX = Math.abs((target.getX() - getX())/moveX);
                moveY = (target.getY() - getY())/timeX;
            } else {
                if(target.getY() > getY()) moveY = xSpeed;
                else moveY = -xSpeed;
                double timeY = Math.abs((target.getY() - getY())/moveY);
                moveX = (target.getX() - getX())/timeY;
            }
            
            if(target.getX() > getX()) {
                double adj = target.getY() - getY();
                double opp = target.getX() - getX();
                double hyp = Math.sqrt((adj*adj) + (opp * opp));
                double temp = ((Math.asin(adj/hyp)*180)/Math.PI);
                this.setRotation(90 + (int)temp);
            } else {
                double adj = target.getY() - getY();
                double opp = getX() - target.getX();
                double hyp = Math.sqrt((adj*adj) + (opp * opp));
                double temp = ((Math.acos(adj/hyp)*180)/Math.PI);
                this.setRotation(0 + (int)temp);
            }
            xSpeed = 0;
            //x += (int)moveX;
            //y += (int)moveY;
            stopAct = true;
            setLocation(getX() + (int)moveX, getY() + (int)moveY);
        }
        
        if(getX() > screen.getWidth() + 10 || getX() < -10 || getY() > screen.getHeight() + 10 || getY() < -10)
            remove = true;
        if(getObjectsInRange(20, Starship.class).size() > 0) {
            screen.updateScore(1);
            remove = true;    
        }
        //if(remove)
        //    remove();
    }
}
