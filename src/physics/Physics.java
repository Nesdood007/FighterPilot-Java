package physics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import level.Chunk;
/**NOTICE: Use of this class is depreciated. Use Collision instead to do collisions between collidable objects and AI for physics involving things like gravity
 *
 */
public interface Physics {

    void doPhysics();

    Rectangle getHitbox();

    BufferedImage getShadowMask();

    void updateChunk(Chunk[][] c);

}
