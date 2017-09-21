package fighterpilot;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameEvent.AI;
import gameEvent.Stats;
import level.Chunk;
import level.Location;
import level.Sprite;
import physics.Collidable;

public class PongBall extends Sprite implements AI, Collidable, Stats{
    
    BufferedImage image;
    
    //Collision
    Rectangle hitbox;
    boolean north = false, south = false, east = false, west = false;
    
    private double dirInDegrees;
    boolean isMoving;
    
    //Stats
    private int initialHealth = 5;
    private double initialSpeed = 0.01;
    private int health = initialHealth;
    private double speed = initialSpeed;
    
    public PongBall(String name, int id, int dataValue, double xLoc, double yLoc, double dirInDegrees, boolean isMoving){
	super(name, id, dataValue, xLoc, yLoc);
	this.dirInDegrees = dirInDegrees;
	this.isMoving = isMoving;
	try{
	    image = ImageIO.read(new File("box.png"));
	} catch (IOException e){
	    e.printStackTrace();
	}
	hitbox = new Rectangle(image.getWidth(), image.getHeight());
    }

    @Override
    public boolean isDead() {
	// TODO Auto-generated method stub
	return health < 0;
    }

    @Override
    public BufferedImage getImage() {
	// TODO Auto-generated method stub
	return image;
    }

    @Override
    public Rectangle getHitbox() {
	// TODO Auto-generated method stub
	return hitbox;
    }

    @Override
    public BufferedImage getShadowMask() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void doCollision(Collidable c) {
	// TODO Auto-generated method stub
	Rectangle temp = c.getHitbox();
	
	//Check location of other Collidable Sprite, compare to this object to determine which way to move
	//top, right, bottom, left
	/*if(temp.getX() > hitbox.getX() && temp.getY() < hitbox.getHeight()/2 + hitbox.getY()){
	    dirInDegrees = Math.abs(dirInDegrees + 90.0);
	    System.out.print("East ");
	}
	if(temp.getX() > hitbox.getX() && temp.getY() > hitbox.getHeight()/2 + hitbox.getY()){
	    dirInDegrees = Math.abs(dirInDegrees - 90.0);
	    System.out.print("West ");
	}
	if(temp.getY() > hitbox.getY() && temp.getX() < hitbox.getWidth()/2 + hitbox.getX()){
	    dirInDegrees = Math.abs(dirInDegrees - 180.0);
	    System.out.print("South ");
	}
	if(temp.getY() > hitbox.getY() && temp.getX() > hitbox.getWidth()/2 + hitbox.getX()){
	    dirInDegrees = Math.abs(dirInDegrees + 180.0);
	    System.out.print("North ");
	}*/
	if(hitbox.getY() < c.getHitbox().getY() + c.getHitbox().getHeight()){
	    //System.out.print("North ");
	    dirInDegrees = Math.abs(dirInDegrees + 180.0);
	    north = true;
	} else if(hitbox.getY() + hitbox.getHeight() > c.getHitbox().getY()){
	    //System.out.print("South ");
	    dirInDegrees = Math.abs(dirInDegrees);
	    south = true;
	}
	if(hitbox.getX() + hitbox.getWidth() < c.getHitbox().getY()){
	    //System.out.print("East ");
	    dirInDegrees = Math.abs(dirInDegrees + 90.0);
	    east = true;
	} else if(hitbox.getX() < c.getHitbox().getX() + c.getHitbox().getWidth()){
	    //System.out.print("West ");
	    dirInDegrees = Math.abs(dirInDegrees - 90.0);
	    west = true;
	}
	
	//System.out.println("Collision");
	
	if(c instanceof Stats && !(c instanceof PongShip)){
	    if(((Stats)c).getCurrentPower() != 0){
		health -= ((Stats)c).getCurrentPower();
	    }
	}
	
    }

    @Override
    public void doAI() {
	// TODO Auto-generated method stub
	if(isMoving){
	    this.loc.x += Math.cos(dirInDegrees) * speed;
	    this.loc.y -= Math.sin(dirInDegrees) * speed;
	}
	//System.out.println(Math.cos(dirInDegrees) * speed + " " + Math.sin(dirInDegrees) * speed);
    }

    @Override
    public void updateChunk(Chunk[][] c) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public boolean hasChildren() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public Sprite getChildren() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int getCurrentHealth() {
	// TODO Auto-generated method stub
	return health;
    }

    @Override
    public int getCurrentPower() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getCurrentDefense() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getCurrentSpecial() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public double getCurrentSpeed() {
	// TODO Auto-generated method stub
	return speed;
    }

    @Override
    public int getInitialHealth() {
	// TODO Auto-generated method stub
	return initialHealth;
    }

    @Override
    public int getInitialPower() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getInitialDefense() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getInitialSpecial() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public double getInitialSpeed() {
	// TODO Auto-generated method stub
	return initialSpeed;
    }

    @Override
    public void setHealth(int newHealth) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void setPower(int newPower) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void setDefense(int newDefense) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void setSpecial(int newSpecial) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void setSpeed(double newSpeed) {
	// TODO Auto-generated method stub
	
    }

}
