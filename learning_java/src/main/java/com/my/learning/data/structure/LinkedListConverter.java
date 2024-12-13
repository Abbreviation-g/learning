package com.my.learning.data.structure;

public class LinkedListConverter {
	static class Node {
		private Integer value;
		private Node next;

		public Node(Integer value) {
			super();
			this.value = value;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		public Node getNext() {
			return next;
		}

		public Integer getValue() {
			return value;
		}
		
		public static Node reverser(Node head) {
			if (head == null || head.next == null)
		        return head;
		    Node newHead = reverser(head.next);
		    
		    Node temp = head.next;
		    temp.next = head;
		    head.next = null;
		    return newHead;
		}
		
		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append(value);
			if (getNext() != null) {
				result.append(", ");
				result.append(getNext());
			}
			return result.toString();
		}
	}

	public static void main(String[] args) {
		Node linkedList = new Node(0);

		Node previous = linkedList;
		for (int i = 1; i < 3; i++) {
			Node temp = new Node(i);
			previous.setNext(temp);
			previous = temp;
		}
		System.out.println(linkedList);
		System.out.println(Node.reverser(linkedList));
	}
}
