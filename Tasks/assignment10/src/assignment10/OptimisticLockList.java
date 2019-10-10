package assignment10;

import java.util.LinkedList;

public class OptimisticLockList<T extends Comparable<T>> implements SortedListInterface<T> {

	/** Represents the start of the list. */
	private Node head;

	/** Represents the end of the list. */
	private Node tail;

	public OptimisticLockList() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		tail = head.next;
	}

	@Override
	public synchronized boolean add(T item) {
		return false; // TODO Implement
	}

	@Override
	public synchronized boolean remove(T item) {
		return false; // TODO Implement
	}

	@Override
	public synchronized boolean contains(T item) {
		return false; // TODO Implement
	}

	@Override
	public LinkedList<T> toLinkedList() {
		LinkedList<T> list = new LinkedList<T>();
		Node curr = this.head.next;
		while (curr != tail) {
			list.add(curr.item);
			curr = curr.next;
		}
		return list;
	}

	/** list node */
	class Node {
		/** actual item */
		T item;

		/** item's hash code */
		int key;

		/** next entry in list */
		Node next;

		/**
		 * Constructor for usual entry
		 * 
		 * @param item
		 *            element in list
		 */
		Node(T item) {
			this.item = item;
			this.key = item.hashCode();
		}

		/**
		 * Constructor for sentinel entry
		 * 
		 * @param key
		 *            should be min or max int value
		 */
		Node(int key) {
			this.key = key;
		}
	}

}