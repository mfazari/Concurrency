package assignment10;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import assignment10.CoarseGrainedLockList.Node;

public class FineGrainedLockList<T extends Comparable<T>> implements
SortedListInterface<T> {

	/** Represents the start of the list. */
	private final Node head;

	/** Represents the end of the list. */
	private final Node tail;

	public FineGrainedLockList() {
		head = new Node(Integer.MIN_VALUE);
		tail = new Node(Integer.MAX_VALUE);
		head.next = tail;
	}

	@Override
	public boolean add(T item) {
		int key = item.hashCode();
		head.lock();
		Node pred = head;
		Node curr = pred.next;
		curr.lock();
		try {
			while (curr.key < key) {
				pred.unlock();
				pred = curr;
				curr = curr.next;
				curr.lock();
			}
			if (curr.key == key) {
				return false;
			}
			Node newNode = new Node(item);
			newNode.next = curr;
			pred.next = newNode;
			return true;
		} finally {
			curr.unlock(); pred.unlock();
		}
	}

	@Override
	public boolean remove(T item) {
		Node pred = null, curr = null;
		int key = item.hashCode();
		head.lock();
		try {
			pred = head;
			curr = pred.next;
			curr.lock();
			try {
				while (curr.key < key) {
					pred.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock();
				}
				if (curr.key == key) {
					pred.next = curr.next;
					return true;
				}
				return false;
			} finally {
				curr.unlock();
			}
		} finally {
			pred.unlock();
		}
	}

	@Override
	public boolean contains(T item) {
		Node pred = null, curr = null;
		int key = item.hashCode();
		head.lock();
		try {
			pred = head;
			curr = pred.next;
			curr.lock();
			try {
				while (curr.key < key) {
					pred.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock();
				}
				return (curr.key == key);
			} finally {
				curr.unlock();
			}
		} finally {
			pred.unlock();
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
	 * list Node
	 */
	private class Node {
		/**
		 * actual item
		 */
		T item;
		/**
		 * item's hash code
		 */
		int key;
		/**
		 * next Node in list
		 */
		Node next;
		/**
		 * synchronizes individual Node
		 */
		Lock lock;

		/**
		 * Constructor for usual Node
		 * 
		 * @param item
		 *            element in list
		 */
		Node(T item) {
			this.item = item;
			this.key = item.hashCode();
			this.lock = new ReentrantLock();
		}

		/**
		 * Constructor for sentinel Node
		 * 
		 * @param key
		 *            should be min or max int value
		 */
		Node(int key) {
			this.item = null;
			this.key = key;
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
