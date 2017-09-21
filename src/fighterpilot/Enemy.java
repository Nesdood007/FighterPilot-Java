package fighterpilot;

import level.Chunk;
import level.Sprite;
import level.SpriteLayer;
import physics.Physics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import gameEvent.AI;
import physics.Collidable;
import gameEvent.Stats;
import graphics.Animation;

public class Enemy extends Sprite implements AI, Physics, Collidable, Stats{

    private Chunk[][] chunkMatrix;
    private Chunk chunk;

    private BufferedImage img;
    private Random rand = new Random();

    private Rectangle hitbox;
    private BufferedImage shadowMask;

    private boolean fireProjectile;
    private double speed;
    private int coolDown;
    private int currentWait = 0;

    //Testing Animation
    private Animation ani = new Animation("TestImages/explosion.png", 64, 64, 25, 32, false);
    private boolean useAni = false;

    //Stats Information -- Health, power, def, spc, spd
    private int maxHealth = 50;
    private int maxPower = 50;
    private int maxDefense = 20;
    private int maxSpecial = 50;
    private double maxSpeed = 0.001;
    private int health = 100;
    private int power = 50;
    private int defense = 20;
    private int special = 50;

    //For Collision, so things don't instantly die when touched by something else
    private final int RECOVERYTIME = 8;
    private int currentRecoveryTime = RECOVERYTIME;

    public Enemy(String name, int id, int dataValue, double x, double y){
        super(name, id, dataValue, x, y);
        this.speed = 0.001;
        coolDown = 1000;

        try{
            img = ImageIO.read(new File("FighterPilotImages/Enemy.png"));
        } catch(IOException e){
            System.out.println(e);
            img = new BufferedImage(8, 8, BufferedImage.OPAQUE);//8x8 is default size
        }

        hitbox = new Rectangle(img.getWidth(), img.getHeight());


    }

    /**Creates a New Enemy
     * 
     * @param name NAme of Enemy
     * @param id ID
     * @param dataValue Data Value
     * @param x X Location
     * @param y Y Location
     * @param speed Speed to Travel at
     * @param coolDown CoolDown between shots
     */
    public Enemy(String name, int id, int dataValue, double x, double y, double speed, int coolDown){
        super(name, id, dataValue, x, y);
        this.speed = speed;
        try{
            img = ImageIO.read(new File("FighterPilotImages/Enemy.png"));
        } catch(IOException e){
            System.out.println(e);
            img = new BufferedImage(8, 8, BufferedImage.OPAQUE);//8x8 is default size
        }

        hitbox = new Rectangle(img.getWidth(), img.getHeight());
        this.coolDown = coolDown;

    }

    public Enemy(){
        super("NoName", -1, 0, 0.0, 0.0);
        this.speed = 0.001;
        this.coolDown = 1000;
        try{
            img = ImageIO.read(new File("FighterPilotImages/Enemy.png"));
        } catch(IOException e){
            System.out.println(e);
            img = new BufferedImage(8, 8, BufferedImage.OPAQUE);//8x8 is default size
        }
    }

    public BufferedImage getImage(){
        //return img;



        if(!ani.isPlaying()){
            useAni = false;
            //ani.stop();
        }
        if(useAni) return ani.getBufferedImage();
        return img;

    }

    @Override
    public void doAI() {

        currentRecoveryTime++;//For Collision Detection.

        //Look through ChunkMatrix for Player, and then move accordingly

        this.chunk = chunkMatrix[1][1];//Temporary. Delete when Chunk Matrix fully implemented.

        boolean foundPlayer = false;

        /*for(int i = 0; i < chunk.layers.size(); i++){
	    if(chunk.layers.get(i) instanceof SpriteLayer){
		for(int j = 0; j < ((SpriteLayer) chunk.layers.get(i)).sprites.size(); j++){
		    if(((SpriteLayer) chunk.layers.get(i)).sprites.get(j) instanceof Player){
			//Moves Enemy closer to Player
			foundPlayer = true;

			if(((SpriteLayer) chunk.layers.get(i)).sprites.get(j).loc.y > this.loc.y){
			    this.loc.y += speed;
			}
			if(((SpriteLayer) chunk.layers.get(i)).sprites.get(j).loc.y < this.loc.y){
			    this.loc.y -= speed;
			}
			currentWait++;
			if(((SpriteLayer) chunk.layers.get(i)).sprites.get(j).loc.y - 2.00 <= this.loc.y && ((SpriteLayer) chunk.layers.get(i)).sprites.get(j).loc.y + 2.00 >= this.loc.y){
			    coolDown = 100;
			    if(currentWait >= coolDown){
				currentWait = 0;
				fireProjectile = true;
			    }
			}
		    }
		}
	    }

	}*/

        for(int i = 0; i < chunkMatrix.length; i++){//row
            for(int j = 0; j < chunkMatrix[0].length; j++){//col

                if(chunkMatrix[i][j] != null){

                    //System.out.println(i + " " + j + "\t" + chunkMatrix[i][j]);

                    for(int k = 0; k < chunkMatrix[i][j].layers.size(); k++){//layer
                        if(chunkMatrix[i][j].layers.get(k) instanceof SpriteLayer){
                            for(int l = 0; l < ((SpriteLayer) chunkMatrix[i][j].layers.get(k)).sprites.size(); l++){//Sprite
                                if(((SpriteLayer) chunkMatrix[i][j].layers.get(k)).sprites.get(l) instanceof Player){
                                    //Moves Enemy closer to Player
                                    foundPlayer = true;

                                    if(((SpriteLayer) chunkMatrix[i][j].layers.get(k)).sprites.get(l).loc.y + ((double) (i - 1) * chunkMatrix[i][j].getBlockSize()[0] ) > this.loc.y){
                                        this.loc.y += speed;
                                    }
                                    if(((SpriteLayer) chunkMatrix[i][j].layers.get(k)).sprites.get(l).loc.y + ((double) (i - 1) * chunkMatrix[i][j].getBlockSize()[0] ) < this.loc.y){
                                        this.loc.y -= speed;
                                    }
                                    currentWait++;
                                    if(((SpriteLayer) chunkMatrix[i][j].layers.get(k)).sprites.get(l).loc.y + ((double) (i - 1) * chunkMatrix[i][j].getBlockSize()[0] ) - 2.00 <= this.loc.y && ((SpriteLayer) chunkMatrix[i][j].layers.get(k)).sprites.get(l).loc.y + ((double) (i - 1) * chunkMatrix[i][j].getBlockSize()[0] ) + 2.00 >= this.loc.y){
                                        coolDown = 100;
                                        if(currentWait >= coolDown){
                                            currentWait = 0;
                                            fireProjectile = true;
                                        }
                                    }
                                }
                            }
                        }
                    }

                }else{
                    //System.out.println(i + " " + j + "\tNull");
                }


            }

        }

        if(!foundPlayer){
            this.loc.x -= speed;
            coolDown = 1000;//1000

            currentWait++;
            if(currentWait >= coolDown){
                currentWait = 0;
                fireProjectile = true;
            }
        }

        //Move in a Direction
        //Fire Randomly

    }

    @Override
    public void updateChunk(Chunk[][] c) {
        this.chunkMatrix = c;
        this.chunk = chunkMatrix[1][1];//Temporary. Delete when Chunk Matrix fully implemented.
    }

    @Override
    public boolean hasChildren() {

        return fireProjectile;
    }

    @Override
    public Sprite getChildren() {

        if(fireProjectile){
            fireProjectile = false;
            return new Projectile("Fire", 0 ,0 ,this.loc.x - 1.0, this.loc.y + 1.0, -0.01, 0.0);
        }
        return null;
    }

    public Rectangle getHitbox(){

        return hitbox;
    }


    public BufferedImage getShadowMask() {
        // TODO Auto-generated method stub
        return shadowMask;
    }

    @Override
    //Look at other Sprites (and eventually blocks) in ChunkMatrix. Look for collisions. If there is one, assess damage
    public void doPhysics() {

        if(chunkMatrix != null){

            for(int i = 1; i < chunkMatrix.length-1; i++){//row
                for(int j = 1; j < chunkMatrix[0].length-1; j++){//col

                    if(chunkMatrix[i][j] != null){

                        for(int k = 0; k < chunkMatrix[i][j].layers.size(); k++){//Layer
                            if(chunkMatrix[i][j].layers.get(k) instanceof SpriteLayer){
                                for(int l = 0; l < ((SpriteLayer)chunkMatrix[i][j].layers.get(k)).sprites.size(); l++){//Sprite
                                    if(!((SpriteLayer)chunkMatrix[i][j].layers.get(k)).sprites.get(l).equals(this) && ((SpriteLayer)chunkMatrix[i][j].layers.get(k)).sprites.get(l) instanceof Physics){
                                        if(this.getHitbox().intersects(((Physics)((SpriteLayer)chunkMatrix[i][j].layers.get(k)).sprites.get(l)).getHitbox()) && ((Physics)((SpriteLayer)chunkMatrix[i][j].layers.get(k)).sprites.get(l)).getHitbox() != null){
                                            System.out.println("Collision Between " + this + "\t " + ((SpriteLayer)chunkMatrix[i][j].layers.get(k)).sprites.get(l));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else{
            System.out.println("ChunkMatrix is Null!!");
        }
    }

    public void doCollision(Collidable c){
        //System.out.println("Collision has occoured between " + this.toString() + "\t" + c.toString());
        if(currentRecoveryTime >= RECOVERYTIME){
            currentRecoveryTime = 0;

            useAni = true;
            ani.play();

            if(c instanceof Stats){
                health = health - ((Stats) c).getCurrentPower();
                //System.out.println(health);
            }
        }
    }

    public String toString(){
        return "Enemy|name: " + name + "\tID: " + id + "\t" + loc + "\tDataValue:" + data_value + "\tIsDead:" + this.isDead();
    }

    public boolean isDead(){
        return health <= 0;
    }

    @Override
    public int getCurrentHealth() {
        // TODO Auto-generated method stub
        return health;
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
