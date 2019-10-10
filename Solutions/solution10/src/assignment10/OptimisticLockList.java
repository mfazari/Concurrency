package assignment10;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OptimisticLockList<T extends Comparable<T>> implements
	SortedListInterface<T> {

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
	public boolean add(T item) {
		int key = item.hashCode();
		while (true) {
			Node pred = this.head;
			Node curr = pred.next;
			while (curr.key < key) {
				pred = curr;
				curr = curr.next;
			}
			pred.lock();
			curr.lock();
			try {
				if (validate(pred, curr)) {
					if (curr.key == key) { // present
						return false;
					} else { // not present
						Node entry = new Node(item);
						entry.next = curr;
						pred.next = entry;
						return true;
					}
				}
			} finally { // always unlock
				pred.unlock();
				curr.unlock();
			}
		}
	}

	@Override
	public boolean remove(T item) {
		int key = item.hashCode();
		while (true) {
			Node pred = this.head;
			Node curr = pred.next;
			while (curr.key < key) {
				pred = curr;
				curr = curr.next;
			}
			pred.lock();
			curr.lock();
			try {
				if (validate(pred, curr)) {
					if (curr.key == key) {
						pred.next = curr.next;
						return true;
					} else {
						return false;
					}
				}
			} finally {
				pred.unlock();
				curr.unlock();
			}
		}
	}

	@Override
	public boolean contains(T item) {
		int key = item.hashCode();
		while (true) {
			Node pred = this.head;
			Node curr = pred.next;
			while (curr.key < key) {
				pred = curr;
				curr = curr.next;
			}
			try {
				pred.lock();
				curr.lock();
				if (validate(pred, curr)) {
					return (curr.key == key);
				}
			} finally {
				pred.unlock();
				curr.unlock();
			}
		}
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
	 * Check that prev and curr are still in list and adjacent
	 * 
	 * @param pred
	 *            predecessor node
	 * @param curr
	 *            current node
	 * @return whther predecessor and current have changed
	 */
	private boolean validate(Node pred, Node curr) {
		Node entry = head;
		while (entry.key <= pred.key) {
			if (entry == pred)
				return pred.next == curr;
			entry = entry.next;
		}
		return false;
	}

	/**
	 * list node
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
		 * next entry in list
		 */
		volatile Node next;
		/**
		 * Synchronizes entry.
		 */
		final Lock lock;

		/**
		 * Constructor for usual entry
		 * 
		 * @param item
		 *            element in list
		 */
		Node(T item) {
			this.item = item;
			this.key = item.hashCode();
			lock = new ReentrantLock();
		}

		/**
		 * Constructor for sentinel entry
		 * 
		 * @param key
		 *            should be min or max int value
		 */
		Node(int key) {
			this.item = null;
			this.key = key;
			lock = new ReentrantLock();
		}

		/**
		 * Lock entry
		 */
		void lock() {
			lock.lock();
		}

		/**
		 * Unlock entry
		 */
		void unlock() {
			lock.unlock();
		}
	}

}