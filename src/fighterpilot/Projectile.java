package fighterpilot;

import level.Chunk;
import level.Sprite;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameEvent.AI;
import physics.Collidable;
import gameEvent.Stats;

public class Projectile extends Sprite implements AI, Collidable, Stats {

    private double xDirection, yDirection;
    private boolean facingRight;
    private BufferedImage left, right;

    private int lifetime;//Lifetime determined by number of times AI is done.
    private int currentLifetime = 0;
    private boolean isDead = false;

    //CollisionDetection
    private Rectangle hitbox;

    //Stats Information -- Health, power, def, spc, spd
    private int maxHealth = 0;
    private int maxPower = 10;
    private int maxDefense = 0;
    private int maxSpecial = 0;
    private double maxSpeed = 0.01;
    private int health = 0;
    private int power = 10;
    private int defense = 0;
    private int special = 0;

    /**This Creates a new Projectile
     * 
     * @param name Name of Projectile
     * @param id ID of Projectile
     * @param dataValue Data Value
     * @param x Starting X Location
     * @param y Starting Y Location
     * @param xDirection X Direction and speed to travel
     * @param yDirection Y Direction and speed to travel
     */
    public Projectile(String name, int id, int dataValue, double x, double y, double xDirection, double yDirection){
	super(name, id, dataValue, x, y);
	this.xDirection = xDirection;
	this.yDirection = yDirection;

	if(xDirection > 0){
	    facingRight = false;
	}else{
	    facingRight = true;
	}

	try{
	    left = ImageIO.read(new File("FighterPilotImages/ProjectileL.png"));
	} catch(IOException e){
	    System.out.println(e);
	    left = new BufferedImage(8, 8, BufferedImage.OPAQUE);//8x8 is default size
	}

	try{
	    right = ImageIO.read(new File("FighterPilotImages/ProjectileR.png"));
	} catch(IOException e){
	    System.out.println(e);
	    right = new BufferedImage(8, 8, BufferedImage.OPAQUE);//8x8 is default size
	}

	if(facingRight){
	    hitbox = new Rectangle(right.getWidth(), right.getHeight());
	}else{
	    hitbox = new Rectangle(left.getWidth(), left.getHeight());
	}

	lifetime = 2000;

    }

    public Projectile(String name, int id, int dataValue, double x, double y, double xDirection, double yDirection, int lifetime){
	super(name, id, dataValue, x, y);
	this.xDirection = xDirection;
	this.yDirection = yDirection;

	if(xDirection > 0){
	    facingRight = false;
	}else{
	    facingRight = true;
	}

	try{
	    left = ImageIO.read(new File("FighterPilotImages/ProjectileL.png"));
	} catch(IOException e){
	    System.out.println(e);
	    left = new BufferedImage(8, 8, BufferedImage.OPAQUE);//8x8 is default size
	}

	try{
	    right = ImageIO.read(new File("FighterPilotImages/ProjectileR.png"));
	} catch(IOException e){
	    System.out.println(e);
	    right = new BufferedImage(8, 8, BufferedImage.OPAQUE);//8x8 is default size
	}

	if(facingRight){
	    hitbox = new Rectangle(right.getWidth(), right.getHeight());
	}else{
	    hitbox = new Rectangle(left.getWidth(), left.getHeight());
	}

	this.lifetime = lifetime;
    }

    public BufferedImage getImage(){
	if(facingRight){
	    return right;
	}
	return left;
    }


    @Override
    public void doAI() {
	loc.x += xDirection;
	loc.y += yDirection;

	currentLifetime++;
	if(currentLifetime > lifetime){
	    isDead = true;
	}

    }

    @Override
    public boolean isDead(){
	return isDead;
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


    public Rectangle getHitbox() {
	// TODO Auto-generated method stub
	return hitbox;
    }

    public BufferedImage getShadowMask() {
	// TODO Auto-generated method stub
	return null;
    }


    public void doCollision(Collidable c) {
	// TODO Auto-generated method stub

	if(c instanceof Stats){
	    currentLifetime  = lifetime;//Kills sprite
	}
    }

    @Override
    public int getCurrentHealth() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getCurrentPower() {
	// TODO Auto-generated method stub
	return power;
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
	return 0;
    }

    @Override
    public int getInitialHealth() {
	// TODO Auto-generated method stub
	return 0;
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
	return 0;
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
