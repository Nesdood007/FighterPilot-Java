package item;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**This is the base item class. 
 * 
 * @author Brady
 *
 */
public abstract class Item {

    private String name;
    private int id;
    private double weight;
    private String description;
    
    private BufferedImage image;
    
    public Item(String name, int id, double weight, String description){
	this.name = name;
	this.id = id;
	this.weight = weight;
	this.description = description;
	try {
	    image = ImageIO.read(new File("FighterPilotTestImages/TestItem.png"));
	} catch (IOException e) {
	    System.err.println("Warn: Cannot find TestItem Image.");
	    e.printStackTrace();
	}
    }
    
    public String getName(){
	return name;
    }
    
    public int getID(){
	return id;
    }
    
    public double getWeight(){
	return weight;
    }
    
    public String getDescription(){
	return description;
    }
    
    /**Should return the Effects of the Item as a String.
     * 
     * @return Item Effects formatted as a String
     */
    public abstract String getEffects();
    
    public String toString(){
	return "Item|" + name + ": ID:" + id + " weight:" + weight;
    }
    
    public BufferedImage getImage(){
	return image;
    }
    
    
}
