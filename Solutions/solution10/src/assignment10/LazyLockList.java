package assignment10;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import assignment10.CoarseGrainedLockList.Node;

public class LazyLockList<T extends Comparable<T>> implements
SortedListInterface<T> {

	/** Represents the start of the list. */
	private final Node head;

	/** Represents the end of the list. */
	private final Node tail;

	public LazyLockList() {
		head = new Node(Integer.MIN_VALUE);
		tail = new Node(Integer.MAX_VALUE);
		head.next = tail;
	}

	@Override
	public boolean add(T item) {
		int key = item.hashCode();
		while (true) {
			Node pred = this.head;
			Node curr = head.next;
			while (curr.key < key) {
				pred = curr;
				curr = curr.next;
			}
			pred.lock(); curr.lock();
			try {
				if (validate(pred, curr)) {
					if (curr.key == key) { // present
						return false;
					} else { // not present
						Node Node = new Node(item);
						Node.next = curr;
						pred.next = Node;
						return true;
					}
				}
			} finally { // always unlock
				pred.unlock(); curr.unlock();
			}
		}
	}

	@Override
	public boolean remove(T item) {
	    int key = item.hashCode();
	    while (true) {
	      Node pred = this.head;
	      Node curr = head.next;
	      while (curr.key < key) {
	        pred = curr; curr = curr.next;
	      }
	      pred.lock(); curr.lock();
	      try {
	        if (validate(pred, curr)) {
	          if (curr.key != key) {    // present
	             return false;
	          } else {                  // absent
	            curr.marked = true;     // logically remove
	            pred.next = curr.next;  // physically remove
	            return true;
	          }
	        }
	      } finally { // always unlock
	        pred.unlock(); curr.unlock();
	      }
	    }
	}

	@Override
	public boolean contains(T item) {
		int key = item.hashCode();
		Node curr = this.head;
		while (curr.key < key)
			curr = curr.next;
		return curr.key == key && !curr.marked;
	}

	/**
	 * Check that prev and curr are still in list and adjacent
	 */
	private boolean validate(Node pred, Node curr) {
		return !pred.marked && !curr.marked && pred.next == curr;
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


	/**
	 * list Node
	 */
	private class Node {
		/**
		 * actual item
		 */
		final T item;
		/**
		 * item's hash code
		 */
		final int key;
		/**
		 * next Node in list
		 */
		volatile Node next;
		/**
		 * If true, Node is logically deleted.
		 */
		volatile boolean marked;
		/**
		 * Synchronizes Node.
		 */
		final Lock lock;

		/**
		 * Constructor for usual Node
		 * 
		 * @param item
		 *            element in list
		 */
		Node(T item) { // usual constructor
			this.item = item;
			this.key = item.hashCode();
			this.next = null;
			this.marked = false;
			this.lock = new ReentrantLock();
		}

		/**
		 * Constructor for sentinel Node
		 * 
		 * @param key
		 *            should be min or max int value
		 */
		Node(int key) { // sentinel constructor
			this.item = null;
			this.key = key;
			this.next = null;
			this.marked = false;
			this.lock = new ReentrantLock();
		}

		/**
		 * Lock Node
		 */
		void lock() {
			lock.lock();
		}

		/**
		 * Unlock Node
		 */
		void unlock() {
			lock.unlock();
		}
	}

}
