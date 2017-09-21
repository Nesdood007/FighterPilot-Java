package item;
/**Designed to supplement Items. This is an implementation for the use of Effects with a GameObject
 * 
 * @author Brady
 *
 */
public interface Effects {
    
    Effect[] getEffects();
    void updateEffects();
    void addEffect(Effect effect);
    void clearEffects();
    

}
