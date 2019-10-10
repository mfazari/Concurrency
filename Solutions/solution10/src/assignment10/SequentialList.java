package assignment10;

import java.util.LinkedList;

public class SequentialList<T extends Comparable<T>> implements SortedListInterface<T> {

	/** Represents the start of the list. */
	private Node head;

	/** Represents the end of the list. */
	private Node tail;

	public SequentialList() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		tail = head.next;
	}

	@Override
	public boolean add(T item) {
		int key = item.hashCode();
		Node pred = this.head;
		Node curr = pred.next;
		while (curr.key < key) {
			pred = curr;
			curr = curr.next;
		}
		if (curr.key == key) { // present
			return false;
		} else { // not present
			Node entry = new Node(item);
			entry.next = curr;
			pred.next = entry;
			return true;
		}
	}

	@Override
	public boolean remove(T item) {
		int key = item.hashCode();
		Node pred = this.head;
		Node curr = pred.next;
		while (curr.key < key) {
			pred = curr;
			curr = curr.next;
		}
		if (curr.key == key) { // present
			pred.next = curr.next;
			return true;
		} else { // not present
			return false;
		}
	}

	@Override
	public boolean contains(T item) {
		int key = item.hashCode();
		Node pred = this.head;
		Node curr = pred.next;
		while (curr.key < key) {
			pred = curr;
			curr = curr.next;
		}
		return (curr.key == key);
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