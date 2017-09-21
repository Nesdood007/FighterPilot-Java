package fighterpilot;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameEvent.AI;
import gameEvent.Stats;
import level.Chunk;
import level.Sprite;
import level.SpriteLayer;
import physics.Collidable;

public class PongShip extends Sprite implements AI, Collidable, Stats{

    private boolean createBall = true, placeBall = true;

    private final int LOOPTIMEOUT = 100;
    private int currentLoopTimeout = 0; 

    private BufferedImage image;

    private Rectangle hitbox;

    private Chunk[][] chunkMatrix;

    //FacingDirection
    private int faceDirection;
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    //Stats
    private int initialHealth = 50;
    private double initialSpeed = 0.01;
    private int health = initialHealth;
    private double speed = initialSpeed;


    public PongShip(String name, int id, int dataValue, double xLoc, double yLoc, int faceDirection, boolean placeBall){
        super(name, id, dataValue, xLoc, yLoc);
        this.faceDirection = faceDirection;
        try{
            if(faceDirection == 0 || faceDirection == 2){
                image = ImageIO.read(new File("barHoriz.png"));
            } else {
                image = ImageIO.read(new File("barVert.png"));
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        hitbox = new Rectangle(image.getWidth(), image.getHeight());
        this.placeBall = placeBall;
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

        if(c instanceof Stats && !(c instanceof PongBall)){
            if(((Stats)c).getCurrentPower() != 0){
                health -= ((Stats)c).getCurrentPower();
            }
        }

    }

    @Override
    public void doAI() {
        // TODO Auto-generated method stub

        boolean ballFound = false;

        double locationBiasR = 0.0, locationBiasC = 0.0;

        for(int i = 0; i < chunkMatrix.length; i++){
            for(int j = 0; j < chunkMatrix[0].length; j++){
                if(chunkMatrix[i][j] != null){
                    locationBiasR = chunkMatrix[i][j].getBlockSize()[0] * (i - 1);
                    locationBiasC = chunkMatrix[i][j].getBlockSize()[1] * (j - 1);
                    for(int lyr = 0; lyr < chunkMatrix[i][j].layers.size(); lyr++){
                        if(chunkMatrix[i][j].layers.get(lyr) instanceof SpriteLayer){
                            for(int spt = 0; spt < ((SpriteLayer)chunkMatrix[i][j].layers.get(lyr)).sprites.size(); spt++){
                                if(((SpriteLayer)chunkMatrix[i][j].layers.get(lyr)).sprites.get(spt) instanceof PongBall){
                                    if(faceDirection == 0 || faceDirection == 2){
                                        if(this.loc.x - locationBiasC < ((SpriteLayer)chunkMatrix[i][j].layers.get(lyr)).sprites.get(spt).loc.x){
                                            loc.x += speed;
                                        } else if(this.loc.x - locationBiasC > ((SpriteLayer)chunkMatrix[i][j].layers.get(lyr)).sprites.get(spt).loc.x){
                                            loc.x -= speed;
                                        }
                                    } else {
                                        if(this.loc.y - locationBiasR < ((SpriteLayer)chunkMatrix[i][j].layers.get(lyr)).sprites.get(spt).loc.y){
                                            loc.y += speed;
                                        } else if(this.loc.y - locationBiasR > ((SpriteLayer)chunkMatrix[i][j].layers.get(lyr)).sprites.get(spt).loc.y){
                                            loc.y -= speed;
                                        }
                                    }
                                    ballFound = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        //System.out.println(currentLoopTimeout);
        if(!ballFound){
            currentLoopTimeout++;
        }
        if(currentLoopTimeout > LOOPTIMEOUT){
            currentLoopTimeout = 0;
            createBall = true;
        }

    }

    @Override
    public void updateChunk(Chunk[][] c) {
        // TODO Auto-generated method stub

        chunkMatrix = c;

    }

    @Override
    public boolean hasChildren() {
        // TODO Auto-generated method stub
        return createBall && placeBall;
    }

    @Override
    public Sprite getChildren() {
        // TODO Auto-generated method stub
        if(createBall && placeBall){
            createBall = false;
            if(faceDirection == NORTH){
                return new PongBall("Ball", -1, 0, this.loc.x + 1.0, this.loc.y + 0.5, 90.0, true);
            }
            if(faceDirection == EAST){
                return new PongBall("Ball", -1, 0, this.loc.x + 0.5, this.loc.y + 1.0, 90.0, true);
            }
            if(faceDirection == SOUTH){
                return new PongBall("Ball", -1, 0, this.loc.x + 1.0, this.loc.y - 0.5, 90.0, true);
            }
            if(faceDirection == WEST){
                return new PongBall("Ball", -1, 0, this.loc.x - 0.5, this.loc.y + 1.0, 90.0, true);
            }

        }
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
