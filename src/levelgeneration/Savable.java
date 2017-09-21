package levelgeneration;
/**Interface for reading and writing GameObjects to a file. 
 * <p>
 * Feeds in a formatted String that fills in the variables for descendants of Sprite and Block
 * 
 * @author Brady
 *
 */
public interface Savable {
    void buildFromVarList(String args);
    String getVarList();
}
