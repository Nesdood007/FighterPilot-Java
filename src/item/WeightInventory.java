package item;

import java.util.ArrayList;
//import java.util.Set;
/**Storage system for items. This one is limited by weight, however, it is up to the parent class to determine that
 * 
 * @author Brady
 *
 */
public class WeightInventory extends Inventory {
    
    private ArrayList<ItemNode> inventory;
    
    /**The node that each item is stored in along with its amount
     * 
     * @author Brady
     *
     */
    private class ItemNode{
	
	public Item item;
	public int amount;
	/**Creates a new ItemNode
	 * 
	 * @param item Item
	 * @param amount Amount of item in inventory
	 */
	public ItemNode(Item item, int amount){
	    this.item = item;
	    this.amount = amount;
	}
	
	
    }
    /**Creates a new Inventory.
     * 
     * @param spaces Number of Spaces in Inventory. Use -1 if infinite.
     * 
     */
    public WeightInventory(){
	inventory = new ArrayList<ItemNode>();
    }
    /**Adds one item to the inventory
     * 
     * @param item Item to add
     */
    public void addItem(Item item){
	ItemNode node = null;
	
	for(int i = 0; i < inventory.size(); i++){
	    if(item.equals(inventory.get(i).item)){
		node = inventory.get(i);
	    }
	}
	
	if(node == null){
	    node = new ItemNode(item, 1);
	} else {
	    node.amount++;
	}
    }
    /**Adds multiple items to the inventory
     * 
     * @param item Item to add
     * @param amount Amount of item to add
     */
    public void addItem(Item item, int amount){
	ItemNode node = null;
	
	for(int i = 0; i < inventory.size(); i++){
	    if(item.equals(inventory.get(i).item)){
		node = inventory.get(i);
	    }
	}
	
	if(node == null){
	    node = new ItemNode(item, amount);
	} else {
	    node.amount += amount;
	}
    }
    /**Checks to see if Item is present in the inventory
     * 
     * @param item Item to check for
     * @return True if item is found, false otherwise
     */
    public boolean hasItem(Item item){
	for(int i = 0; i < inventory.size(); i++){
	    if(item.equals(inventory.get(i).item)){
		return true;
	    }
	}
	return false;
    }
    /**Removes one Item from the inventory.
     * @TODO - Should this return a boolean value based on if it removed the proper number of an item?
     * @param item Item to remove from the inventory
     */
    public void removeItem(Item item){
	ItemNode node = null;
	
	for(int i = 0; i < inventory.size(); i++){
	    if(item.equals(inventory.get(i).item)){
		node = inventory.get(i);
	    }
	}
	
	if(node == null){
	    //Do nothing since there is nothing to remove.
	} else {
	    node.amount--;
	}
	
	//Remove the node if it is empty
	for(int i = 0; i < inventory.size(); i++){
	    if(inventory.get(i).amount <= 0){
		inventory.remove(i);
		i--;
	    }
	}
    }
    /**Removes an amount of a given item from the inventory
     * 
     * @param item Type of item to be removed
     * @param amount Amount of the item to be removed
     */
    public void removeItem(Item item, int amount){
	ItemNode node = null;
	
	for(int i = 0; i < inventory.size(); i++){
	    if(item.equals(inventory.get(i).item)){
		node = inventory.get(i);
	    }
	}
	
	if(node == null){
	    //Do nothing since there is nothing to remove.
	} else {
	    node.amount -= amount;
	}
	
	//Remove the node if it is empty
	for(int i = 0; i < inventory.size(); i++){
	    if(inventory.get(i).amount <= 0){
		inventory.remove(i);
		i--;
	    }
	}
    }
    /**Returns all items in inventory in an array
     * 
     * @return Array of all items
     */
    public Item[] toArray(){
	Item[] toReturn = new Item[inventory.size()];
	
	for(int i = 0; i < inventory.size(); i++){
	    toReturn[i] = inventory.get(i).item;
	}
	
	return toReturn;
    }
    /**Gets the amount of a given item
     * 
     * @param item Item to lookup the amount of
     * @return Amount of the item
     */
    public int getAmount(Item item){
	for(int i = 0; i < inventory.size(); i++){
	    if(inventory.get(i).item.equals(item)){
		return inventory.get(i).amount;
	    }
	}
	return 0;
    }
    /**Returns the total weight of the inventory.
     * 
     * @return total weight of the Inventory.
     */
    public double getTotalWeight(){
	double toReturn = 0.0;
	
	for(int i = 0; i < inventory.size(); i++){
	    toReturn += inventory.get(i).item.getWeight() * inventory.get(i).amount;
	}
	
	return toReturn;
    }
}
