import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Fighter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fighter extends Enemy
{
    private boolean attackCooldown = false, damageCooldown;
    private int attackTick = 0, damageTick = 0, health = 1;
    private Sensor sensorRight, sensorLeft, sensorCenter, sensorLeftSide, sensorRightSide;
    protected void imageSetup(){
        setImage(new GreenfootImage(screen.getSprite("enemyBlue5.png")));
        getImage().scale(getImage().getWidth()*1/4, getImage().getHeight()*1/4);
        sensorRight = new Sensor(8);
        sensorLeft = new Sensor(-8);
        sensorCenter = new Sensor(0);
        sensorLeftSide = new Sensor(-90, 35);
        sensorRightSide = new Sensor(90, 35);
        screen.addObject(sensorRight, getX(), getY());
        screen.addObject(sensorLeft, getX(), getY());
        screen.addObject(sensorCenter, getX(), getY());
        screen.addObject(sensorLeftSide, getX(), getY());
        screen.addObject(sensorRightSide, getX(), getY());
    }     
    
    public void customAct() {
        if(!damageCooldown) damageTick++;
        if(damageTick > 50) damageCooldown = true;
        
        List<Asteroid> asteroids = getIntersectingObjects(Asteroid.class);
        if(asteroids.size() > 0 && damageCooldown) {
            damageCooldown = false;
            damageTick = 0;
            damage(1);
            //getImage().drawImage(new GreenfootImage(screen.getSprite("playerShip1_damage1.png")), 0, 0);
        }
        
        List<Enemy> enemies = getIntersectingObjects(Enemy.class);
        if(enemies.size() > 0 && damageCooldown) {
            damageCooldown = false;
            damageTick = 0;
            damage(1);
            
            //getImage().drawImage(new GreenfootImage(screen.getSprite("playerShip1_damage1.png")), 0, 0);
        }
        
        //if(sensor.intersect) {
        //    rotation += 4;
        //}
        double xSpeed = 0.5;
        double dx = getX();
        double dy = getY();
        double ang = rotation + sensorRight.ang;
        double x = dx;
        double y = dy + sensorRight.getImage().getHeight()/2;
        double xMod = (Math.cos(ang*Math.PI/180)*(x - dx)) - (Math.sin(ang*Math.PI/180)*(y - dy));
        double yMod = (Math.sin(ang*Math.PI/180)*(x - dx)) + (Math.cos(ang*Math.PI/180)*(y - dy));
        sensorRight.setLocation((int)(getX() - xMod), (int)(getY() - yMod));
        sensorRight.setRotation((int)(ang));
        
        if(sensorRight.intersect) {
            if(!sensorLeft.intersect) {
                super.x -= (Math.cos(ang*Math.PI/180)*(xSpeed)) - (Math.sin(ang*Math.PI/180)*(xSpeed));
                super.y += (Math.sin(ang*Math.PI/180)*(xSpeed)) + (Math.cos(ang*Math.PI/180)*(xSpeed));
            }
        }
        
        ang = rotation + sensorLeft.ang;
        y = getY() + sensorLeft.getImage().getHeight()/2;
        xMod = (Math.cos(ang*Math.PI/180)*(x - dx)) - (Math.sin(ang*Math.PI/180)*(y - dy));
        yMod = (Math.sin(ang*Math.PI/180)*(x - dx)) + (Math.cos(ang*Math.PI/180)*(y - dy));
        sensorLeft.setLocation((int)(getX() - xMod), (int)(getY() - yMod));
        sensorLeft.setRotation((int)(ang));
        
        if(sensorLeft.intersect) {
            super.x += (Math.cos(ang*Math.PI/180)*(xSpeed)) - (Math.sin(ang*Math.PI/180)*(xSpeed));
            super.y += (Math.sin(ang*Math.PI/180)*(xSpeed)) + (Math.cos(ang*Math.PI/180)*(xSpeed));
        }
        
        ang = rotation + sensorCenter.ang;
        y = getY() + sensorCenter.getImage().getHeight()/2;
        xMod = (Math.cos(ang*Math.PI/180)*(x - dx)) - (Math.sin(ang*Math.PI/180)*(y - dy));
        yMod = (Math.sin(ang*Math.PI/180)*(x - dx)) + (Math.cos(ang*Math.PI/180)*(y - dy));
        sensorCenter.setLocation((int)(getX() - xMod), (int)(getY() - yMod));
        sensorCenter.setRotation((int)(ang));
        
        if(sensorCenter.intersect) {
            if(!sensorRight.intersect && !sensorLeft.intersect) {
                super.x += (Math.cos(ang*Math.PI/180)*(xSpeed)) - (Math.sin(ang*Math.PI/180)*(xSpeed));
                super.y += (Math.sin(ang*Math.PI/180)*(xSpeed)) + (Math.cos(ang*Math.PI/180)*(xSpeed));
            }
        }
        
        ang = rotation + sensorLeftSide.ang;
        y = getY() + sensorLeftSide.getImage().getHeight()/2;
        xMod = (Math.cos(ang*Math.PI/180)*(x - dx)) - (Math.sin(ang*Math.PI/180)*(y - dy));
        yMod = (Math.sin(ang*Math.PI/180)*(x - dx)) + (Math.cos(ang*Math.PI/180)*(y - dy));
        sensorLeftSide.setLocation((int)(getX() - xMod), (int)(getY() - yMod));
        sensorLeftSide.setRotation((int)(ang));
        
        if(sensorLeftSide.intersect) {
            //if(sensorLeftSide.intersect) {
            //    super.x -= (Math.cos(ang*Math.PI/180)*(xSpeed)) - (Math.sin(ang*Math.PI/180)*(xSpeed));
            //    super.y += (Math.sin(ang*Math.PI/180)*(xSpeed)) + (Math.cos(ang*Math.PI/180)*(xSpeed));
            //} else {
                super.x += (Math.cos(rotation*Math.PI/180)*(xSpeed)) - (Math.sin(rotation*Math.PI/180)*(xSpeed));
                super.y += (Math.sin(rotation*Math.PI/180)*(xSpeed)) + (Math.cos(rotation*Math.PI/180)*(xSpeed));
            //}
        }
        
        ang = rotation + sensorRightSide.ang;
        y = getY() + sensorRightSide.getImage().getHeight()/2;
        xMod = (Math.cos(ang*Math.PI/180)*(x - dx)) - (Math.sin(ang*Math.PI/180)*(y - dy));
        yMod = (Math.sin(ang*Math.PI/180)*(x - dx)) + (Math.cos(ang*Math.PI/180)*(y - dy));
        sensorRightSide.setLocation((int)(getX() - xMod), (int)(getY() - yMod));
        sensorRightSide.setRotation((int)(ang));
        
        if(sensorRightSide.intersect) {
            //if(sensorLeftSide.intersect) {
            //    super.x -= (Math.cos(ang*Math.PI/180)*(xSpeed)) - (Math.sin(ang*Math.PI/180)*(xSpeed));
            //    super.y += (Math.sin(ang*Math.PI/180)*(xSpeed)) + (Math.cos(ang*Math.PI/180)*(xSpeed));
            //} else {
                super.x += (Math.cos(ang*Math.PI/180)*(xSpeed)) - (Math.sin(ang*Math.PI/180)*(xSpeed));
                super.y += (Math.sin(ang*Math.PI/180)*(xSpeed)) + (Math.cos(ang*Math.PI/180)*(xSpeed));
            //}
        }
        
        applyLogic();
    }
           
    public void applyLogic() {
        if(getObjectsInRange(100, GravityOrb.class).size() == 0) {
            //gYSpeed = 0;
            //gXSpeed = 0;
            //System.out.println("Out");
            if(gYSpeed != 0) gYSpeed += ((Math.abs(gYSpeed)/(gYSpeed * -1.0)) * 5);
            if(gXSpeed != 0) gXSpeed += ((Math.abs(gXSpeed)/(gXSpeed * -1.0)) * 5);
        }
        
        List<Starship> starships = (List<Starship>)screen.getObjects(Starship.class);
        if(starships.size() > 0) {
            Starship target = starships.get(0);
            double moveX, moveY, speed = 2;
            if(Math.abs(target.getX() - getX()) > Math.abs(target.getY() - getY())) {
                if(target.getX() > getX()) moveX = speed;
                else moveX = -speed;
                double timeX = Math.abs((target.getX() - getX())/moveX);
                moveY = (target.getY() - getY())/timeX;
            } else {
                if(target.getY() > getY()) moveY = speed;
                else moveY = -speed;
                double timeY = Math.abs((target.getY() - getY())/moveY);
                moveX = (target.getX() - getX())/timeY;
            }
        
            if(target.getX() > getX()) {
                double adj = target.getY() - getY();
                double opp = target.getX() - getX();
                double hyp = Math.sqrt((adj*adj) + (opp * opp));
                double temp = ((Math.asin(adj/hyp)*180)/Math.PI);
                rotation = 90 + (int)temp;
                this.setRotation(270 + (int)temp);
            } else {
                double adj = target.getY() - getY();
                double opp = getX() - target.getX();
                double hyp = Math.sqrt((adj*adj) + (opp * opp));
                double temp = ((Math.acos(adj/hyp)*180)/Math.PI);
                rotation = 180 + (int)temp;
                this.setRotation(0 + (int)temp);
            }
            
            
            if(!attackCooldown) attackTick++;
            if(attackTick > 20) attackCooldown = true;
            
            if(!getObjectsInRange(150, Starship.class).contains(target)) {
                xSpeed += 0.2;
                if(xSpeed > 2) xSpeed = 2;
            } else if(getObjectsInRange(250, Starship.class).contains(target) && attackCooldown) {
                screen.addObject(new Blaster(target, this), getX(), getY());
                attackCooldown = false;
                attackTick = 0;
            }
        }
        
        
    }
    
    public void damage(int dmg) {
        health -= dmg;
        if(health <= 0) {
            screen.addObject(new Explosion(), getX(), getY());
            remove = true;
        }
    }
    
    public void remove() {
        if(remove) {
            screen.removeObject(sensorLeft);
            screen.removeObject(sensorRight);
            screen.removeObject(sensorCenter);
            screen.removeObject(sensorLeftSide);
            screen.removeObject(sensorRightSide);
            screen.removeObject(this);
        }
    }
    
    public boolean goodSpawn() {
        if(getObjectsInRange(250, Starship.class).size() > 0)
            return false;
        return true;
    }
}
