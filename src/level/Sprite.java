package level;

import java.awt.image.BufferedImage;

/**Sprite Class is meant to be Extended....
 * See Example.
 * 
 * For Sprites needing AI, implement the AI interface. For Sprites needing Events (which should be most sprites), implement the GameEvent interface.
 */
public abstract class Sprite /*implements AI, Physics*/{

    public String name;
    public int id;
    public Location loc;
    public int data_value;

    public Sprite(){
	name = "nothing";
	id = 0;
	loc = new Location(0.0,0.0);
	data_value = 0;
    }
    /**Creates a new Sprite
     * 
     * @param n Name
     * @param i ID
     * @param d DataValue
     * @param x X Location
     * @param y Y Location
     */
    public Sprite(String n,int i,int d,double x, double y){
	name = n;
	id = i;
	loc = new Location(x,y);
	data_value = d;
    }

    /**Tells whether the sprite is dead.
     * 
     * @return true if dead.
     */
    public abstract boolean isDead();
    
    public abstract BufferedImage getImage();

    //Debug
    public String toString(){
	return "Sprite::name: " + name + "\tID: " + id + "\t" + loc + "\tDataValue:" + data_value + "\tIsDead:" + this.isDead();

    }
}
