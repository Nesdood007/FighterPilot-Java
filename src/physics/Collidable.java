package physics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
/**Use to tell if an object will need collision methods, stuff to be called and used.
 * 
 * @author Brady
 *
 */
public interface Collidable {

    /**This returns a NON-ADJUSTED Rectangle containing data for dimensions
     * 
     * @return Rectangle Not adjusted for Position within chunk. Collision Detection implementation will do that.
     */
    Rectangle getHitbox();

    BufferedImage getShadowMask();

    void doCollision(Collidable c);//Method is called when the game determines a collision has occurred.

}
