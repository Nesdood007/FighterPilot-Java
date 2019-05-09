package figherpiloteditor;
/**This is the container for the Selectable item and will return a clone of the Selected Object
 * 
 * @author Brady O'Leary
 *
 */
public class SelectableObjectContainer<E> {
    
    private E object = null;
    
    /**Create new Instance
     * 
     * @param obj
     */
    public SelectableObjectContainer(E obj) {
        object = obj;
    }
    
    /**Return a clone (Deep Copy) of the Object in the container
     * 
     * @return Deep Copy of Object
     */
    public E getObject() {
        return object;
    }

    
}
