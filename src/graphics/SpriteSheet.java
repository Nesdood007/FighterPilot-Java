package graphics;//old location -> level

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

    private BufferedImage[] sprites; //last position is for an invalid id

    private int[] size = new int[2];//{Height,Width}

    //Defaults

    public static final String[] TEST = {"0.png","1.png","2.png"};
    public static final String[] TRANSPARENT = {"FighterPilotImages/0.png","FighterPilotImages/1.png"};

    /**Creates a new SpriteSheet from image filenames
     * 
     * @param names 
     */
    public SpriteSheet(String[] names){

	sprites = new BufferedImage[names.length+1];

	//loads sprites from filenames

	for(int i=0;i<names.length;i++){

	    try{
		sprites[i] = ImageIO.read(new File(names[i]));

		//Calculates Height and Width of Blocks
		if(sprites[i].getHeight() > size[0]) size[0] = sprites[i].getHeight();
		if(sprites[i].getWidth() > size[1]) size[1] = sprites[i].getWidth();

	    } catch(IOException e){
		System.out.println("@SpriteSheet-Warning:Cannot find image\n" + e);

		//Reads invalid Image to take place of image
		try{
		    sprites[i] = ImageIO.read(new File("x.png"));
		} catch(IOException f){
		    System.out.println("@SpriteSheet-Warning:Cannot find image\n" + f);
		    sprites[i] = new BufferedImage(size[1], size[0], BufferedImage.OPAQUE);//Fake Image so Program will not crash during Render
		}

	    }

	}

	//loads "invalid id" sprite

	try{
	    sprites[sprites.length-1] = ImageIO.read(new File("x.png"));
	} catch(IOException e){
	    System.out.println("@SpriteSheet-Warning:Cannot find image\n" + e);
	    sprites[sprites.length-1] = new BufferedImage(size[1], size[0], BufferedImage.OPAQUE);
	}

	if(size[0] != size[1]) System.out.println("@SpriteSheet - Warning: Block size is not square!");
    }

    public BufferedImage getBufferedImage(int id){

	if(id >= sprites.length){
	    System.out.println("@SpriteSheet - Invalid ID");
	    return sprites[sprites.length-1];
	}

	return sprites[id];
    }

    public int getSpriteSize(){

	if(size[0]>size[1]) return size[0];
	return size[1];
    }
}
