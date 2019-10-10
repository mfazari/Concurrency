package assignment10;

import java.util.LinkedList;

public class CoarseGrainedLockList<T extends Comparable<T>> implements
	SortedListInterface<T> {
	
	/** Represents the start of the list. */
	private final Node head;

	/** Represents the end of the list. */
	private final Node tail;

	public CoarseGrainedLockList() {
		head = new Node(Integer.MIN_VALUE);
		tail = new Node(Integer.MAX_VALUE);
		head.next = tail;
	}

	@Override
	public synchronized boolean add(T item) {
		Node pred, curr;
		Node node = new Node(item);
		int key = item.hashCode();
		pred = head;
		curr = pred.next;
		while (curr.key < key) {
			pred = curr;
			curr = curr.next;
		}
		if (key == curr.key) {
			return false;
		} else {
			node.next = curr;
			pred.next = node;
			return true;
		}
	}

	@Override
	public synchronized boolean remove(T item) {
		Node pred, curr;
		int key = item.hashCode();
		pred = this.head;
		curr = pred.next;
		while (curr.key < key) {
			pred = curr;
			curr = curr.next;
		}
		if (key == curr.key) {
			pred.next = curr.next;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public synchronized boolean contains(T item) {
		Node pred, curr;
		int key = item.hashCode();
		pred = head;
		curr = pred.next;
		while (curr.key < key) {
			pred = curr;
			curr = curr.next;
		}
		return (key == curr.key);
	}
	

	// not thread safe, only for testing purposes
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
	
	/**
	 * list node
	 */
	class Node {
		/**
		 * actual item
		 */
		T item;
		/**
		 * item's hash code
		 */
		int key;
		/**
		 * next entry in list
		 */
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
