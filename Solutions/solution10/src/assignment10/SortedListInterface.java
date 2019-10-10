package assignment10;

import java.util.LinkedList;

/**
 * A simple interface for sorted lists
 *
 * @param <T extends Comparable<T>> Must implement comparable for the list
 * to be able to sort the items.
 */
public interface SortedListInterface<T extends Comparable<T>> {

	/**
	 * Add an item of type T to the list in the natural ordering.
	 * If an item of the same (ordering) exists return false.
	 * 
	 * @param item to be added.
	 * @return true when inserted.
	 */
	public boolean add (T item);

	/**
	 * Removes first instances of an item of type T from the list and maintains the order.
	 * 
	 * @param item to be removed.
	 * @return true if removed or item not contained in list.
	 */
	public boolean remove (T item);
	
	/**
	 * Searches for the item of type T.
	 * 
	 * @param item to be searched.
	 * @return whether item was found in the list.
	 */
	public boolean contains (T item);
	
	/**
	 * Creates and returns an array containing all elements of the list. 
	 * 
	 * @return all elements as an array.
	 */
	public LinkedList<T> toLinkedList();
}
