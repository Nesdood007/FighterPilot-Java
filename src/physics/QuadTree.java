package physics;

import java.util.ArrayList;

/**This class is meant to be a part of the Collision interface. This Quadtree will hold all elements (In this case, Sprites) that will have colission determined.
 * 
 * @author Brady
 *
 */
public class QuadTree<E> {

    private QuadNode<E> root;
    private int maxChildren;

    private class QuadNode<E> {
	//Children
	public QuadNode<E> firstChild = null;
	public QuadNode<E> secondChild = null;
	public QuadNode<E> thirdChild = null;
	public QuadNode<E> fourthChild = null;

	private ArrayList<E> data = new ArrayList<E>();
	private boolean hasChildren = false;

	private int bucketCapacity;
	private double size;

	public QuadNode(int capacity, double size) {
	    this.bucketCapacity = capacity;
	    this.size = size;
	}

	public void addData(E elem) {
	    data.add(elem);

	    if(data.size() > bucketCapacity){
		hasChildren = true;
		//Code that determines location of elements in regard to QuadTree Node. Use size.
	    }
	}
    }


}
