package fighterpilot;

import level.Chunk;
import level.Sprite;
import physics.Physics;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import audio.AudioManager;
import audio.Sound;
import gameEvent.AI;
import gameEvent.Stats;
import physics.Collidable;
import gameInput.Button;
import gameInput.Controllable;
import graphics.Animation;

public class Player extends Sprite implements AI, Controllable, Physics, Collidable, Stats {

    private Button[] buttons = {new Button("moveUp", -1, KeyEvent.VK_W), new Button("moveDown", -1, KeyEvent.VK_S), new Button("moveLeft", -1, KeyEvent.VK_A), new Button("moveRight", -1, KeyEvent.VK_D), new Button("fire", -1, KeyEvent.VK_E), new Button("shield", -1, KeyEvent.VK_Q)}; //moveUp, moveDown,moveLeft, moveRight, fire, shield

    private Rectangle hitbox;
    private Chunk[][] chunkMatrix;

    private boolean fireProjectile;

    private int coolDown = 100;
    private int currentCoolDown = 0;

    //Stats: health, power, def, spc, spd
    private int initialHealth = 1000;
    private int initialPower = 0;
    private int initialDefense = 5;
    private int initialSpecial;
    private int initialSpeed;

    private int health = initialHealth;
    private int power = initialPower;
    private int defense = initialDefense;
    private int special;
    private double speed;

    private BufferedImage img;
    
    //Audio Sounds
    Sound fire, explode;
    
    /**Creates new Player
     * 
     * @param name Name of Player
     * @param id ID of Player
     * @param data_value Data Value
     * @param x X position
     * @param y Y position
     */
    public Player(String name, int id, int data_value, double x, double y) {//nidxy
        super(name,id,data_value,x,y);
        this.speed = 0.01;

        try{
            img = ImageIO.read(new File("FighterPilotImages/Ship.png"));//FighterPilotImages/Ship.png
        } catch(IOException e){
            System.err.println(e);
            img = new BufferedImage(8, 8, BufferedImage.OPAQUE);//8x8 is default size
        }

        hitbox = new Rectangle(img.getWidth(), img.getHeight());
        fire = AudioManager.loadSound("sfx/shoot.wav", false);
        explode = AudioManager.loadSound("sfx/shortExplode.wav", false);
    }
    /**Creates new Player
     * 
     * @param name NAme of Player
     * @param id ID of player
     * @param data_value Data VAlue
     * @param x X Position
     * @param y Y Position
     * @param speed Speed of Player
     */
    public Player(String name, int id, int data_value, double x, double y, double speed) {//nidxy
        super(name,id,data_value,x,y);
        this.speed = speed;

        try{
            img = ImageIO.read(new File("FighterPilotImages/Ship.png"));
        } catch(IOException e){
            System.out.println(e);
            img = new BufferedImage(8, 8, BufferedImage.OPAQUE);//8x8 is default size
        }

        hitbox = new Rectangle(img.getWidth(), img.getHeight());
    }

    @Override
    public void doAI() {

        boolean moveUp = false, moveDown = false, moveLeft = false, moveRight = false, fire = false, shield = false;

        for(Button temp : buttons){
            if(temp.getName().equals("moveUp")){
                moveUp = temp.getState();
            } else if(temp.getName().equals("moveDown")){
                moveDown = temp.getState();
            } else if(temp.getName().equals("moveLeft")){
                moveLeft = temp.getState();
            } else if(temp.getName().equals("moveRight")){
                moveRight = temp.getState();
            } else if(temp.getName().equals("fire")){
                fire = temp.getState();
            } else if(temp.getName().equals("shield")){
                shield = temp.getState();
            }
        }

        //Checks to see if can move
        boolean canMoveUp = true, canMoveDown = true, canMoveLeft = true, canMoveRight = true;

        if(loc.x >= (double) chunkMatrix[1][1].getBlockSize()[1] - 1 && chunkMatrix[1][2] == null){
            canMoveRight = false;
        }
        if(loc.y >= (double) chunkMatrix[1][1].getBlockSize()[0] - 1 && chunkMatrix[2][1] == null){
            canMoveDown = false;
        }
        if(loc.x <= 1.0 && chunkMatrix[1][0] == null){//0 1
            canMoveLeft = false;
        }
        if(loc.y <= 1.0 && chunkMatrix[0][1] == null){//1 0
            canMoveUp = false;
        }

        if(moveUp && canMoveUp){
            loc.y -= speed; 
        }else if(moveDown && canMoveDown){
            loc.y += speed;
        }
        if(moveLeft && canMoveLeft){
            loc.x -= speed;
        }else if(moveRight && canMoveRight){
            loc.x += speed;
        }

        currentCoolDown++;
        if(fire){
            if(currentCoolDown >= coolDown){
                currentCoolDown = 0;
                fireProjectile = true;
            }
        }
    }

    public BufferedImage getImage(){
        return img;
    }

    public boolean isDead() {
        return health < 0;
    }

    public void updateChunk(Chunk[][] c) {
        chunkMatrix = c;
    }

    @Override
    public boolean hasChildren() {
        return fireProjectile;
    }

    @Override
    public Sprite getChildren() {

        //return null;
        if(fireProjectile == true){
            fireProjectile = false;
            fire.play();
            return new Projectile("PlayerSpawn", 0, 0, this.loc.x + 1.6, this.loc.y + 0.9, 0.01, 0.0);
        }
        return null;
    }

    @Override
    public void doPhysics() {
        // TODO Auto-generated method stub
    }

    @Override
    public BufferedImage getShadowMask() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Rectangle getHitbox(){
        //HitBox is not adjusted for coordinates, since that would make global Collision detection easier and also would fix the problem of not seeing the location in the level.
        return hitbox;
    }
    @Override
    public void doCollision(Collidable c) {
        // TODO Auto-generated method stub
        //System.out.println("Player was hit by " + c);
        if(c instanceof Stats){
            if(((Stats)c).getCurrentPower() != 0){
                explode.play();
                health -= ((Stats)c).getCurrentPower() - defense;
            }
        }

    }
    @Override
    public Button[] getButtons() {
        // TODO Auto-generated method stub
        return buttons;
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
