package fighterpilot;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gameEvent.AI;
import gameEvent.Stats;
import graphics.Animation;
import level.Chunk;
import level.Sprite;
import physics.Collidable;

public class LargeFlyingPanel extends Sprite implements AI, Stats, Collidable{
    
    private double speed;
    
    private int initialHealth = 10;
    private int health = initialHealth;
    
    private int initialDefense = 1;
    private int defense = initialDefense;
    
    private int initialPower = 1;
    private int power = initialPower;
    
    private Animation ani = new Animation("FighterPilotImages/LargeFloatingPanelAnimation.png", 64, 64, 8, 10, true);
    
    private Rectangle hitbox;
    
    public LargeFlyingPanel(String name, int id, int dataValue, double xLoc, double yLoc, double speed){
	super(name, id, dataValue, xLoc, yLoc);
	this.speed = speed;
	hitbox = new Rectangle(ani.getWidth(), ani.getHeight());
    }
    
    @Override
    public boolean isDead() {
	// TODO Auto-generated method stub
	return health <= 0;
    }

    @Override
    public BufferedImage getImage() {
	// TODO Auto-generated method stub
	return ani.getBufferedImage();
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
	if(c instanceof Stats && ((Stats) c).getCurrentPower() != 0){
	    this.health -= ((Stats) c).getCurrentPower() - this.defense;
	    //System.out.println(health);
	}
	
    }

    @Override
    public int getCurrentHealth() {
	// TODO Auto-generated method stub
	return health;
    }

    @Override
    public int getCurrentPower() {
	// TODO Auto-generated method stub
	//return power;
	return power;
    }

    @Override
    public int getCurrentDefense() {
	// TODO Auto-generated method stub
	return defense;
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
	return initialHealth;
    }

    @Override
    public int getInitialPower() {
	// TODO Auto-generated method stub
	//return initialPower;
	return 0;
    }

    @Override
    public int getInitialDefense() {
	// TODO Auto-generated method stub
	return initialDefense;
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

    @Override
    public void doAI() {
	// TODO Auto-generated method stub
	loc.x -= speed;
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

}
