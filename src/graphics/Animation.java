package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**LevelFormat implementation for Animations
 * 
 * @author Brady
 *
 */
public class Animation {

    private BufferedImage[] frames;
    
    private int framesPerSecond;
    private int currentFrame;
    
    private boolean loopAnimation;
    private boolean isPlaying = true;
    
    private long oldTime;
    private long currTime;
    
    private int width;
    private int height;
    
    
    /**Creates an Animation from a single image containing all frames. Reads left-to-right then up-to-down
     * 
     * @param fileName Name of File
     * @param spriteWidth Width of Sprite
     * @param spriteHeight Height of Sprite
     * @param numberOfFrames Number of Frames to read
     * @param framesPerSecond FPS of Animation
     */
    public Animation(String fileName, int spriteWidth, int spriteHeight, int numberOfFrames, int framesPerSecond, boolean loop){
	
	frames = new BufferedImage[numberOfFrames];
	this.framesPerSecond = framesPerSecond;
	loopAnimation = loop;
	
	BufferedImage parentImage = null;
	
	try{
	    parentImage = ImageIO.read(new File(fileName));
	}catch(IOException e){
	    System.err.println("Cannot find File: " + e);
	}
	
	int i = 0;
	for(int h = 0; h < parentImage.getWidth(); h = h + spriteHeight){
	    for(int w = 0; w < parentImage.getWidth(); w = w + spriteWidth){
		if(i < numberOfFrames){
		    frames[i] = parentImage.getSubimage(w, h, spriteWidth, spriteHeight);
		    i++;
		}else{
		    break;
		}
	    }
	}
	
	currentFrame = 0;
	isPlaying = true;
	
	width = spriteWidth;
	height = spriteHeight;
    }
    /**Creates a new Animation from individual filenames of the individual frames
     * 
     * @param fileNames File Names of the frames
     */
    public Animation(String[] fileNames){
	
	frames = new BufferedImage[fileNames.length];
	
	try{
	    for(int i = 0; i < fileNames.length; i++){
		frames[i] = ImageIO.read(new File(fileNames[i]));
	    }
	}catch(IOException e){
	    System.err.println("Animation|Cannot Find Image File: " + e);
	}
	
	currentFrame = 0;
	isPlaying = true;
    }
    /**Changes the FPS of the Animation
     * 
     * @param fps new FPS Value
     */
    public void changeFPS(int fps){
	framesPerSecond = fps;
    }
    /**Updates the current frame of the animation
     * 
     */
    private void updateFrame(){
	currTime = System.currentTimeMillis();
	if(isPlaying && (oldTime) + (1000/framesPerSecond) <= (currTime)){
	    oldTime = currTime;
	    currentFrame++;
	    if(currentFrame >= frames.length ){
		currentFrame = 0;
		if(!loopAnimation){
		    isPlaying = false;
		}
	    }
	    
	}
    }
    /**Gets the current Image
     * 
     * @return BufferedImage Current Frame
     */
    public BufferedImage getBufferedImage(){
	updateFrame();
	if(currentFrame < frames.length) return frames[currentFrame];
	System.out.println("Frame Error, returning last.");
	return frames[frames.length - 1];
    }
    /**Plays the animation
     * 
     */
    public void play(){
	isPlaying = true;
    }
    /**Pauses the animation on the current frame
     * 
     */
    public void pause(){
	isPlaying = false;
    }
    /**Stops the animation on the first frame
     * 
     */
    public void stop(){
	isPlaying = false;
	currentFrame = 0;
    }
    /**Tells whether the animation is still playing
     * 
     * @return boolean returns true is Animation is still playing
     */
    public boolean isPlaying(){
	return isPlaying || loopAnimation;
    }
    /**Gets the Width of the Animation
     * 
     * @return int width of the animation
     */
    public int getWidth(){
	return width;
    }
    /**Gets the Height of the Animation
     * 
     * @return int height of the animation
     */
    public int getHeight(){
	return height;
    }
}
