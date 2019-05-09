package graphics;

import java.awt.Graphics2D;
/**This class draws a scene. Extend it to actually make a scene.
 * 
 * @author Brady
 *
 */
public abstract class Scene {

    private boolean isPlaying;
    /**Creates a scene
     * 
     */
    public Scene(){
	isPlaying = true;
    }
    /**Takes a Graphics2D object and draws the contents of the background to it. Also updates scene.
     * 
     * @param g2d Graphics2D object to draw scene to.
     */
    public abstract void draw(Graphics2D g2d);
    /**Starts the scene.
     * 
     */
    public void start(){
	isPlaying = true;
    }
    /**Pauses the scene.
     * 
     */
    public void pause(){
	isPlaying = false;
    }
    /**Stops and resets the scene.
     * 
     */
    public void stop(){
	isPlaying = false;
    }
    /**Get the current playing state of the scene.
     * 
     * @return boolean true if playing.
     */
    public boolean isPlaying(){
	return isPlaying;
    }
}
