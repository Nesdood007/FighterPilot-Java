package item;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gameEvent.AI;
import physics.Collidable;
import level.Chunk;
import level.Sprite;

/**This allows for an item to be picked up by a GameObject. This also allows for an item to be placed in a level.
 * 
 * @author Brady O'Leary
 *
 */
public class ItemContainer extends Sprite implements AI, Collidable{

    Item item;
    int lifetime;
    int currentLifetime = 0;
    boolean usesLifetime;
    
    public ItemContainer(Item item){
	super(item.getName(), item.getID(), 0, 0.0, 0.0);//dv,x,y
	this.item = item;
    }
    
    public ItemContainer(Item item, double xPosition, double yPosition, int lifetime){
	super(item.getName(), item.getID(), 0, xPosition, yPosition);
	if(lifetime <= 0){
	    usesLifetime = false;
	}else{
	    this.lifetime = lifetime;
	}
    }

    
    public boolean isDead() {
	return usesLifetime && lifetime < currentLifetime;
    }

    
    public BufferedImage getImage() {
	return item.getImage();
    }

    @Override
    public Rectangle getHitbox() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public BufferedImage getShadowMask() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void doCollision(Collidable c) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void doAI() {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void updateChunk(Chunk[][] c) {
	// TODO Auto-generated method stub
	
    }
    
    /**ItemContainers will NEVER have children.
     * 
     */
    public boolean hasChildren() {
	return false;
    }
    /**ItemContainers will NEVER have children.
     * 
     */
    public Sprite getChildren() {
	return null;
    }
    
    
}
