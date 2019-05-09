package levelgeneration;

import level.Sprite;
import fighterpilot.Enemy;
/**Contains all Recognized Sprites in the Game, including the player. Enum names should match the class names, to keep everything standard.
 * 
 * @author Brady O'Leary
 *
 */
public enum LevelGeneratorSprites {
    //Sprites Go Here
    Enemy("Enemy", new Enemy("RandGen", 0, 0, 4.0, 4.0));

    private final String spriteName;
    private final Sprite sprite;

    private LevelGeneratorSprites(String spriteName, Sprite sprite){
        this.spriteName = spriteName;
        this.sprite = sprite;
    }

    public String spriteName(){
        return spriteName;
    }

    public Sprite sprite(){
        return sprite;
    }

}
