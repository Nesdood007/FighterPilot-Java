package item;
/**This is the parent class of Inventory. All Inventories must implement this stuff.
 * 
 * @author Brady
 *
 */
public abstract class Inventory {

    public abstract void addItem(Item item);
    public abstract void addItem(Item item, int amount);
    
    public abstract boolean hasItem(Item item);
    
    public abstract int getAmount(Item item);
    
    public abstract void removeItem(Item item);
    public abstract void removeItem(Item item, int amount);
    
    public abstract Item[] toArray();
    
}
